package com.common.service.impl;

import com.common.model.nosql.BaseModel;
import com.common.model.nosql.BaseSoftDeleteModel;
import com.common.repository.mongo.BaseRepository;
import com.common.service.contract.IBaseService;
import com.common.dto.request.PagingRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.common.utilities.JsonUtils;

import java.util.*;

/**
 * Lớp implementation base service cho NoSQL MongoDB.<br/>
 * Hỗ trợ các tính năng nâng cao như Xóa mềm, Tìm kiếm động và Chiếu dữ liệu tùy chọn (Dynamic Projection).<br/>
 * Created at 10/06/2026, Updated at 19/06/2026
 *
 * @param <TEntity>     Kiểu dữ liệu Document kế thừa từ BaseModel (NoSQL)
 * @param <TId>         Kiểu dữ liệu khóa chính Document (thường là String)
 * @param <TRepository> Kiểu dữ liệu Repository kế thừa từ BaseRepository (NoSQL)
 * @see <a href="../../../../../resources/docs/spring_structure/service-guide.md">BaseMongoService Specification Guide</a>
 * @author txhoan
 */
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class BaseMongoServiceImpl<
        TEntity extends BaseModel,
        TId,
        TRepository extends BaseRepository<TEntity, TId>>
        implements IBaseService<TEntity, TId> {

    TRepository repository;

    @NonFinal
    MongoTemplate mongoTemplate;

    /**
     * Phân tích kiểu lớp (Class) của TEntity bằng Reflection thông qua Generic Superclass.<br/>
     * Phục vụ tìm kiếm động, lọc và chiếu trường trên MongoDB.<br/>
     * Created at 19/06/2026
     *
     * @return Đối tượng Class của TEntity
     */
    @SuppressWarnings("unchecked")
    protected Class<TEntity> getEntityClass() {
        Class<?> clazz = getClass();
        // Lặp dọc theo cấu trúc kế thừa để xác định lớp cha trực tiếp là BaseMongoServiceImpl
        while (clazz != null && clazz.getSuperclass() != Object.class) {
            var genericSuper = clazz.getGenericSuperclass();
            if (genericSuper instanceof java.lang.reflect.ParameterizedType type) {
                var rawType = type.getRawType();
                if (rawType instanceof Class<?> rawClass && BaseMongoServiceImpl.class.isAssignableFrom(rawClass)) {
                    // Đối số generic index 0 là Class của Document
                    return (Class<TEntity>) type.getActualTypeArguments()[0];
                }
            }
            clazz = clazz.getSuperclass();
        }
        throw new IllegalStateException("Không thể phân tích kiểu Document class cho " + getClass().getName());
    }

    /**
     * Lấy tên người đăng nhập hiện tại từ Security Context.<br/>
     * Created at 19/06/2026
     *
     * @return Tên người dùng hoặc "SYSTEM"
     */
    private String getCurrentAuditorName() {
        var authentication = org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return "SYSTEM";
    }

    /**
     * Tạo đối tượng Query cho MongoDB hỗ trợ lọc động và tìm kiếm từ khóa.<br/>
     * Created at 19/06/2026
     *
     * @param request DTO chứa các điều kiện lọc và phân trang
     * @return Đối tượng Query MongoDB
     */
    private Query buildMongoQuery(PagingRequest request) {
        var query = new Query();
        var entityClass = getEntityClass();

        // 1. Kiểm tra Xóa mềm: Nếu document kế thừa từ BaseSoftDeleteModel, lọc bản ghi deleted = false
        if (BaseSoftDeleteModel.class.isAssignableFrom(entityClass)) {
            query.addCriteria(Criteria.where("deleted").is(false));
        }

        // 2. Lọc chính xác: Phân tích JSON filter được gửi lên từ client
        if (request.filter() != null && !request.filter().isBlank()) {
            try {
                var filterMap = JsonUtils.fromJson(request.filter(), Map.class);
                for (var entry : ((Map<?, ?>) filterMap).entrySet()) {
                    var key = String.valueOf(entry.getKey());
                    var val = entry.getValue();
                    if (val != null) {
                        // Thêm điều kiện so sánh bằng (is) trên MongoDB Criteria
                        query.addCriteria(Criteria.where(key).is(val));
                    }
                }
            } catch (Exception e) {
                log.warn("Lỗi phân tích cú pháp filter JSON trong BaseMongoServiceImpl: {}", request.filter(), e);
            }
        }

        // 3. Tìm kiếm mờ theo từ khóa (Keyword Search): regex case-insensitive (chữ thường/hoa)
        if (request.keyword() != null && !request.keyword().isBlank()) {
            var keywordCriteriaList = new ArrayList<Criteria>();
            var searchPattern = request.keyword();
            
            // Tìm kiếm trong các thuộc tính String của lớp con hiện tại
            for (var field : entityClass.getDeclaredFields()) {
                if (field.getType() == String.class) {
                    keywordCriteriaList.add(Criteria.where(field.getName()).regex(searchPattern, "i"));
                }
            }

            // Quét tìm cả các thuộc tính String trên các lớp cha (BaseModel)
            Class<?> superClazz = entityClass.getSuperclass();
            while (superClazz != null && superClazz != Object.class) {
                for (var field : superClazz.getDeclaredFields()) {
                    if (field.getType() == String.class) {
                        keywordCriteriaList.add(Criteria.where(field.getName()).regex(searchPattern, "i"));
                    }
                }
                superClazz = superClazz.getSuperclass();
            }

            if (!keywordCriteriaList.isEmpty()) {
                // Áp dụng toán tử OR trên MongoDB
                query.addCriteria(new Criteria().orOperator(keywordCriteriaList.toArray(new Criteria[0])));
            }
        }

        return query;
    }

    /**
     * Tạo mới một bản ghi Document vào MongoDB.<br/>
     * Created at 10/06/2026
     *
     * @param entity Đối tượng Document cần lưu
     * @return Đối tượng Document sau khi lưu thành công
     */
    @Override
    @Transactional
    public TEntity create(TEntity entity) {
        log.info("Đang tạo tài liệu Mongo: {}", entity);
        return repository.save(entity);
    }

    /**
     * Cập nhật thông tin bản ghi Document đã tồn tại.<br/>
     * Created at 10/06/2026
     *
     * @param entity Đối tượng Document cần cập nhật
     */
    @Override
    @Transactional
    public void update(TEntity entity) {
        log.info("Đang cập nhật tài liệu Mongo: {}", entity);
        repository.save(entity);
    }

    /**
     * Xóa bản ghi Document khỏi MongoDB.<br/>
     * Tự động phát hiện kiểu lớp con để thực hiện xóa mềm (Soft Delete)
     * hoặc xóa cứng (Hard Delete) khỏi cơ sở dữ liệu.<br/>
     * Created at 10/06/2026, Updated soft-delete at 19/06/2026
     *
     * @param entity Đối tượng Document cần xóa
     * @see <a href="../../../../../resources/docs/spring_structure/service-guide.md">BaseMongoService Specification Guide (Soft Delete Section)</a>
     */
    @Override
    @Transactional
    public void delete(TEntity entity) {
        log.info("Đang xóa tài liệu Mongo: {}", entity);
        // Kiểm tra xem Document có kế thừa BaseSoftDeleteModel hay không
        if (entity instanceof com.common.model.nosql.BaseSoftDeleteModel softDeleteEntity) {
            // Thực hiện thiết lập trạng thái xóa mềm
            softDeleteEntity.setDeleted(true);
            softDeleteEntity.setDeletedAt(java.time.LocalDateTime.now());
            softDeleteEntity.setDeletedBy(getCurrentAuditorName());
            // Lưu lại thông tin cập nhật trạng thái xóa mềm
            repository.save(entity);
        } else {
            // Ngược lại thực hiện xóa vật lý khỏi MongoDB
            repository.delete(entity);
        }
    }

    /**
     * Tìm kiếm Document theo ID.<br/>
     * Bỏ qua không trả về các thực thể đã bị xóa mềm.<br/>
     * Created at 10/06/2026, Updated soft-delete filter at 19/06/2026
     *
     * @param id Khóa chính cần tìm
     * @return Optional chứa Document nếu tìm thấy và chưa bị xóa mềm
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TEntity> findById(TId id) {
        log.info("Tìm kiếm tài liệu Mongo theo id: {}", id);
        var entityOpt = repository.findById(id);
        // Kiểm tra và bỏ qua nếu thực thể đã bị xóa mềm
        if (entityOpt.isPresent() && entityOpt.get() instanceof com.common.model.nosql.BaseSoftDeleteModel softDeleteEntity) {
            if (softDeleteEntity.isDeleted()) {
                return Optional.empty();
            }
        }
        return entityOpt;
    }

    /**
     * Tìm kiếm Document theo ID, ném ra NotFoundException nếu không tìm thấy hoặc đã xóa mềm.<br/>
     * Created at 10/06/2026, Updated soft-delete filter at 19/06/2026
     *
     * @param id Khóa chính cần tìm
     * @return Document tương ứng
     * @throws com.common.exception.NotFoundException nếu không tìm thấy hoặc đã bị xóa mềm
     */
    @Override
    @Transactional(readOnly = true)
    public TEntity findByIdOrThrow(TId id) {
        log.info("Tìm kiếm tài liệu Mongo theo id hoặc throw: {}", id);
        var entity = repository.findByIdOrThrow(id);
        if (entity instanceof com.common.model.nosql.BaseSoftDeleteModel softDeleteEntity && softDeleteEntity.isDeleted()) {
            throw new com.common.exception.NotFoundException(com.common.service.MessageService.getMessage("resource.not_found", id));
        }
        return entity;
    }

    /**
     * Lấy danh sách Document phân trang, sắp xếp và lọc động.<br/>
     * Created at 10/06/2026, Updated dynamic filtering at 19/06/2026
     *
     * @param request Payload chứa cấu hình phân trang, bộ lọc và keyword
     * @return Trang dữ liệu chứa các Document
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TEntity> getPaginated(PagingRequest request) {
        log.info("Lấy danh sách phân trang tài liệu Mongo: {}", request);
        if (mongoTemplate == null) {
            return repository.findAll(request.pageable());
        }
        var query = buildMongoQuery(request).with(request.pageable());
        var entityClass = getEntityClass();
        // Đếm tổng số bản ghi khớp bộ lọc
        long total = mongoTemplate.count(query, entityClass);
        // Truy vấn phân trang lấy danh sách đối tượng
        var content = mongoTemplate.find(query, entityClass);
        return new PageImpl<>(content, request.pageable(), total);
    }

    /**
     * Lấy danh sách Document phân trang, lọc động và chỉ lấy ra các thuộc tính được chỉ định (Dynamic Projection).<br/>
     * Sử dụng cấu hình field projection trên Query giúp tối ưu băng thông mạng và hiệu năng MongoDB.<br/>
     * Created at 19/06/2026
     *
     * @param request Payload chứa phân trang, lọc và mảng fields cần lấy
     * @return Trang kết quả chứa danh sách các Map thuộc tính
     * @see <a href="../../../../../resources/docs/spring_structure/service-guide.md">BaseMongoService Specification Guide (Dynamic Projection Section)</a>
     */
    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Page<Map<String, Object>> getPaginatedProjected(PagingRequest request) {
        log.info("Lấy phân trang và chiếu thuộc tính tài liệu Mongo: {}", request);
        if (mongoTemplate == null) {
            throw new IllegalStateException("MongoTemplate chưa được khởi tạo trong BaseMongoServiceImpl");
        }

        var entityClass = getEntityClass();
        var query = buildMongoQuery(request).with(request.pageable());

        // Áp dụng projection (chỉ lấy các trường được chỉ định ở DB level)
        if (request.fields() != null && !request.fields().isEmpty()) {
            for (var field : request.fields()) {
                query.fields().include(field);
            }
        }

        // Đếm số lượng bản ghi thỏa mãn điều kiện lọc
        long total = mongoTemplate.count(query, entityClass);
        if (total == 0) {
            return Page.empty(request.pageable());
        }

        // Lấy danh sách dữ liệu từ MongoDB
        var results = mongoTemplate.find(query, entityClass);

        // Ánh xạ sang Map<String, Object> để loại bỏ các trường thừa không được chọn
        var content = results.stream().map(doc -> {
            var docJson = JsonUtils.toJson(doc);
            var fullMap = JsonUtils.fromJson(docJson, Map.class);
            
            var fields = request.fields();
            if (fields == null || fields.isEmpty()) {
                return (Map<String, Object>) fullMap;
            }

            var map = new LinkedHashMap<String, Object>();
            for (var field : fields) {
                if (fullMap.containsKey(field)) {
                    map.put(field, fullMap.get(field));
                }
            }
            return map;
        }).toList();

        return new PageImpl<>(content, request.pageable(), total);
    }
}
