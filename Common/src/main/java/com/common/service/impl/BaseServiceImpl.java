package com.common.service.impl;

import com.common.exception.NotFoundException;
import com.common.model.sql.BaseModel;
import com.common.model.sql.BaseSoftDeleteModel;
import com.common.repository.sql.BaseRepository;
import com.common.service.MessageService;
import com.common.service.contract.IBaseService;
import com.common.dto.request.PagingRequest;
import com.common.utilities.JsonUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Selection;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Lớp implementation base service cho SQL JPA.<br/>
 * Hỗ trợ các tính năng như Xóa mềm, Lọc Specifications động và Dynamic Column selection.<br/>
 * Created at 10/06/2026, Updated at 19/06/2026
 *
 * @param <TEntity>     Kiểu dữ liệu Entity kế thừa từ BaseModel
 * @param <TId>         Kiểu dữ liệu khóa chính Entity
 * @param <TRepository> Kiểu dữ liệu Repository kế thừa từ BaseRepository
 * @see <a href="../../../../../resources/docs/spring_structure/service-guide.md">BaseService Specification Guide</a>
 * @author txhoan
 */
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class BaseServiceImpl<
        TEntity extends BaseModel,
        TId,
        TRepository extends BaseRepository<TEntity, TId>>
        implements IBaseService<TEntity, TId> {

    TRepository repository;

    @NonFinal
    EntityManager entityManager;

    /**
     * Phân tích kiểu lớp (Class) của TEntity bằng Reflection thông qua Generic Superclass.<br/>
     * Dùng để phục vụ tìm kiếm động, lọc và phản xạ các thuộc tính String.<br/>
     * Created at 19/06/2026
     *
     * @return Đối tượng Class của TEntity
     */
    @SuppressWarnings("unchecked")
    protected Class<TEntity> getEntityClass() {
        Class<?> clazz = getClass();
        // Duyệt dọc theo chuỗi kế thừa đến khi gặp lớp cha trực tiếp của BaseServiceImpl
        while (clazz != null && clazz.getSuperclass() != Object.class) {
            var genericSuper = clazz.getGenericSuperclass();
            if (genericSuper instanceof java.lang.reflect.ParameterizedType type) {
                var rawType = type.getRawType();
                // Kiểm tra xem rawType có phải là lớp BaseServiceImpl hay không
                if (rawType instanceof Class<?> rawClass && BaseServiceImpl.class.isAssignableFrom(rawClass)) {
                    // Trả về đối số Generic đầu tiên (index 0 chính là TEntity)
                    return (Class<TEntity>) type.getActualTypeArguments()[0];
                }
            }
            clazz = clazz.getSuperclass();
        }
        throw new IllegalStateException("Không thể phân tích kiểu Entity class cho " + getClass().getName());
    }

    /**
     * Lấy tên người dùng hiện tại đang đăng nhập từ Security Context.<br/>
     * Created at 19/06/2026
     *
     * @return Tên người dùng hoặc "SYSTEM" nếu không tìm thấy thông tin xác thực
     */
    private String getCurrentAuditorName() {
        var authentication = org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication();
        // Kiểm tra xem đối tượng authentication đã xác thực thành công hay chưa
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return "SYSTEM"; // Trả về SYSTEM mặc định nếu chạy ngầm hoặc chưa đăng nhập
    }

    /**
     * Xây dựng đối tượng Specification động phục vụ tìm kiếm và lọc dữ liệu nâng cao.<br/>
     * Hỗ trợ tự động lọc bản ghi chưa xóa, lọc theo thuộc tính JSON và tìm kiếm từ khóa.<br/>
     * Created at 19/06/2026
     *
     * @param request DTO chứa các tham số lọc, tìm kiếm
     * @return Đối tượng Specification chứa các Predicate tương ứng
     */
    private Specification<TEntity> buildSpecification(PagingRequest request) {
        return (root, query, cb) -> {
            var predicates = new ArrayList<Predicate>();
            var entityClass = getEntityClass();

            // 1. Kiểm tra Xóa mềm: Nếu entity kế thừa từ BaseSoftDeleteModel, lọc bản ghi deleted = false
            if (BaseSoftDeleteModel.class.isAssignableFrom(entityClass)) {
                predicates.add(cb.equal(root.get("deleted"), false));
            }

            // 2. Lọc chính xác theo thuộc tính: Parse chuỗi JSON filter được gửi lên từ client
            if (request.filter() != null && !request.filter().isBlank()) {
                try {
                    // Dùng JsonUtils parse filter JSON sang Map
                    var filterMap = JsonUtils.fromJson(request.filter(), Map.class);
                    for (var entry : ((Map<?, ?>) filterMap).entrySet()) {
                        var key = String.valueOf(entry.getKey());
                        var val = entry.getValue();
                        if (val != null) {
                            // Tạo điều kiện equal cho từng thuộc tính
                            predicates.add(cb.equal(root.get(key), val));
                        }
                    }
                } catch (Exception e) {
                    log.warn("Lỗi phân tích cú pháp filter JSON trong BaseServiceImpl: {}", request.filter(), e);
                }
            }

            // 3. Tìm kiếm mờ (Full-text keyword search): Quét qua các thuộc tính String
            if (request.keyword() != null && !request.keyword().isBlank()) {
                String searchPattern = "%" + request.keyword().toLowerCase() + "%";
                var keywordPredicates = new ArrayList<Predicate>();
                
                // Quét các trường String khai báo trực tiếp trong Entity con
                for (var field : entityClass.getDeclaredFields()) {
                    if (field.getType() == String.class) {
                        try {
                            keywordPredicates.add(cb.like(cb.lower(root.get(field.getName())), searchPattern));
                        } catch (Exception ignored) {}
                    }
                }
                
                // Đồng thời quét lên các lớp cha (bao gồm BaseModel và BaseSoftDeleteModel)
                Class<?> superClazz = entityClass.getSuperclass();
                while (superClazz != null && superClazz != Object.class) {
                    for (var field : superClazz.getDeclaredFields()) {
                        if (field.getType() == String.class) {
                            try {
                                keywordPredicates.add(cb.like(cb.lower(root.get(field.getName())), searchPattern));
                            } catch (Exception ignored) {}
                        }
                    }
                    superClazz = superClazz.getSuperclass();
                }

                if (!keywordPredicates.isEmpty()) {
                    // Ghép các điều kiện OR cho phần keyword tìm kiếm mờ
                    predicates.add(cb.or(keywordPredicates.toArray(new Predicate[0])));
                }
            }

            // Ghép toàn bộ các điều kiện AND với nhau
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Tạo mới một bản ghi thực thể vào cơ sở dữ liệu.<br/>
     * Created at 10/06/2026
     *
     * @param entity Đối tượng thực thể cần lưu
     * @return Đối tượng thực thể sau khi được lưu
     */
    @Override
    @Transactional
    public TEntity create(TEntity entity) {
        log.info("Đang tạo thực thể: {}", entity);
        // Lưu và trả về thực thể
        return repository.save(entity);
    }

    /**
     * Cập nhật thông tin bản ghi thực thể đã tồn tại.<br/>
     * Created at 10/06/2026
     *
     * @param entity Đối tượng thực thể cần cập nhật
     */
    @Override
    @Transactional
    public void update(TEntity entity) {
        log.info("Đang cập nhật thực thể: {}", entity);
        // Lưu đè dữ liệu lên bản ghi cũ
        repository.save(entity);
    }

    /**
     * Xóa một bản ghi thực thể.<br/>
     * Tự động phát hiện kiểu đối tượng để áp dụng xóa mềm (Soft Delete)
     * hoặc xóa cứng (Hard Delete) vật lý trong DB.<br/>
     * Created at 10/06/2026, Updated soft-delete at 19/06/2026
     *
     * @param entity Đối tượng thực thể cần xóa
     * @see <a href="../../../../../resources/docs/spring_structure/service-guide.md">BaseService Specification Guide (Soft Delete Section)</a>
     */
    @Override
    @Transactional
    public void delete(TEntity entity) {
        log.info("Đang xóa thực thể: {}", entity);
        // Kiểm tra xem đối tượng có kế thừa BaseSoftDeleteModel hay không
        if (entity instanceof BaseSoftDeleteModel softDeleteEntity) {
            // Nếu có, thực hiện đánh dấu trạng thái và thời gian người xóa
            softDeleteEntity.setDeleted(true);
            softDeleteEntity.setDeletedAt(LocalDateTime.now());
            softDeleteEntity.setDeletedBy(getCurrentAuditorName());
            // Lưu lại thông tin cập nhật xóa mềm
            repository.save(entity);
        } else {
            // Ngược lại, thực hiện xóa cứng vật lý khỏi Database
            repository.delete(entity);
        }
    }

    /**
     * Tìm kiếm bản ghi theo ID.<br/>
     * Bỏ qua không trả về các thực thể đã bị xóa mềm.<br/>
     * Created at 10/06/2026, Updated soft-delete filter at 19/06/2026
     *
     * @param id Khóa chính cần tìm
     * @return Optional chứa thực thể tương ứng nếu tìm thấy và chưa bị xóa mềm
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TEntity> findById(TId id) {
        log.info("Đang tìm kiếm thực thể theo id: {}", id);
        var entityOpt = repository.findById(id);
        // Lọc không trả về các thực thể đã xóa mềm
        if (entityOpt.isPresent() && entityOpt.get() instanceof BaseSoftDeleteModel softDeleteEntity) {
            if (softDeleteEntity.isDeleted()) {
                return Optional.empty();
            }
        }
        return entityOpt;
    }

    /**
     * Tìm kiếm bản ghi theo ID, ném ra NotFoundException nếu không tìm thấy hoặc đã xóa mềm.<br/>
     * Created at 10/06/2026, Updated soft-delete filter at 19/06/2026
     *
     * @param id Khóa chính cần tìm
     * @return Thực thể tương ứng
     * @throws com.common.exception.NotFoundException nếu không tìm thấy hoặc đã bị xóa mềm
     */
    @Override
    @Transactional(readOnly = true)
    public TEntity findByIdOrThrow(TId id) {
        log.info("Đang tìm kiếm thực thể theo id hoặc throw: {}", id);
        var entity = repository.findByIdOrThrow(id);
        // Kiểm tra nếu thực thể hỗ trợ xóa mềm và đã bị đánh dấu là đã xóa
        if (entity instanceof BaseSoftDeleteModel softDeleteEntity && softDeleteEntity.isDeleted()) {
            throw new NotFoundException(MessageService.getMessage("resource.not_found", id));
        }
        return entity;
    }

    /**
     * Lấy danh sách thực thể phân trang, sắp xếp và lọc động bằng Specification.<br/>
     * Created at 10/06/2026, Updated dynamic specification filtering at 19/06/2026
     *
     * @param request Payload cấu hình phân trang, tìm kiếm và lọc
     * @return Trang kết quả chứa danh sách Entity
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TEntity> getPaginated(PagingRequest request) {
        log.info("Lấy phân trang danh sách thực thể: {}", request);
        // Thực hiện tìm kiếm phân trang có Specification
        return repository.findAll(buildSpecification(request), request.pageable());
    }

    /**
     * Lấy danh sách thực thể phân trang, lọc động và chỉ lấy ra các trường thuộc tính được chỉ định (Dynamic Projection).<br/>
     * Sử dụng CriteriaTuple Query tối ưu chỉ SELECT đúng các cột yêu cầu từ DB.<br/>
     * Created at 19/06/2026
     *
     * @param request Payload chứa phân trang, lọc và mảng fields
     * @return Trang kết quả chứa danh sách các Map thuộc tính
     * @see <a href="../../../../../resources/docs/spring_structure/service-guide.md">BaseService Specification Guide (Dynamic Projection Section)</a>
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Map<String, Object>> getPaginatedProjected(PagingRequest request) {
        log.info("Lấy phân trang và chiếu thuộc tính thực thể: {}", request);
        if (entityManager == null) {
            throw new IllegalStateException("EntityManager chưa được khởi tạo trong BaseServiceImpl");
        }

        var entityClass = getEntityClass();
        var cb = entityManager.getCriteriaBuilder();

        // 1. Đếm tổng số bản ghi khớp điều kiện lọc để phân trang
        var countQuery = cb.createQuery(Long.class);
        var countRoot = countQuery.from(entityClass);
        var spec = buildSpecification(request);
        
        countQuery.select(cb.count(countRoot));
        // Apply Specification predicates vào count query
        countQuery.where(spec.toPredicate(countRoot, countQuery, cb));
        var total = entityManager.createQuery(countQuery).getSingleResult();

        if (total == 0) {
            return Page.empty(request.pageable());
        }

        // 2. Lấy dữ liệu với các thuộc tính được chiếu (Tuple Query)
        var query = cb.createTupleQuery();
        var root = query.from(entityClass);

        var selections = new ArrayList<Selection<?>>();
        var fields = request.fields();
        
        // Nếu không truyền fields cần lấy, mặc định quét và lấy tất cả các thuộc tính của Entity
        if (fields == null || fields.isEmpty()) {
            fields = new ArrayList<>();
            Class<?> currentClass = entityClass;
            while (currentClass != null && currentClass != Object.class) {
                for (var field : currentClass.getDeclaredFields()) {
                    fields.add(field.getName());
                }
                currentClass = currentClass.getSuperclass();
            }
        }

        // Thêm các thuộc tính vào danh sách SELECT
        for (var field : fields) {
            try {
                selections.add(root.get(field).alias(field));
            } catch (Exception ignored) {
                // Bỏ qua các trường không hợp lệ hoặc không ánh xạ trực tiếp trong DB
                log.error(ignored.getMessage(), ignored);
            }
        }

        query.multiselect(selections);
        // Áp dụng bộ lọc Specification vào câu lệnh SELECT
        query.where(spec.toPredicate(root, query, cb));

        // 3. Áp dụng sắp xếp (Sort) từ Pageable
        if (request.pageable().getSort().isSorted()) {
            var orders = new ArrayList<Order>();
            for (var order : request.pageable().getSort()) {
                try {
                    if (order.isAscending()) {
                        orders.add(cb.asc(root.get(order.getProperty())));
                    } else {
                        orders.add(cb.desc(root.get(order.getProperty())));
                    }
                } catch (Exception ignored) {}
            }
            query.orderBy(orders);
        }

        // 4. Phân trang dữ liệu và thực hiện truy vấn
        var results = entityManager.createQuery(query)
                .setFirstResult((int) request.pageable().getOffset())
                .setMaxResults(request.pageable().getPageSize())
                .getResultList();

        // 5. Ánh xạ các cột trả về sang dạng Map<String, Object> để trả về REST API
        var content = results.stream().map(tuple -> {
            var map = new LinkedHashMap<String, Object>();
            for (var element : tuple.getElements()) {
                map.put(element.getAlias(), tuple.get(element));
            }
            return (Map<String, Object>) map;
        }).toList();

        return new PageImpl<>(content, request.pageable(), total);
    }
}
