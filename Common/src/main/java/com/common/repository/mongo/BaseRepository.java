package com.common.repository.mongo;

import com.common.model.nosql.BaseModel;
import com.common.exception.NotFoundException;
import com.common.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Lớp base repository chứa các method thao tác dữ liệu cơ bản cho MongoDB (NoSQL).<br/>
 * Created at 10/06/2026
 *
 * @param <T>  Kiểu dữ liệu Document kế thừa từ BaseModel
 * @param <ID> Kiểu dữ liệu của khóa chính Document (thường là String)
 * @see <a href="../../../../../resources/docs/spring_structure/repository-guide.md">BaseRepository Specification Guide</a>
 * @author txhoan
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseModel, ID> extends MongoRepository<T, ID> {

    /**
     * Tìm kiếm Document theo ID, nếu không tìm thấy sẽ ném ra exception NotFoundException.<br/>
     *
     * @param id Khóa chính cần tìm
     * @return Document tương ứng
     * @throws NotFoundException Nếu không tìm thấy
     */
    default T findByIdOrThrow(ID id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException(MessageService.getMessage("resource.not_found", id)));
    }

    /**
     * Tìm kiếm Document theo ID, nếu không tìm thấy sẽ ném ra exception NotFoundException với message tùy chọn.<br/>
     *
     * @param id      Khóa chính cần tìm
     * @param message Thông điệp lỗi tùy chỉnh
     * @return Document tương ứng
     * @throws NotFoundException Nếu không tìm thấy
     */
    default T findByIdOrThrow(ID id, String message) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException(message));
    }

    /**
     * Kiểm tra sự tồn tại của ID, nếu không tồn tại sẽ ném ra exception NotFoundException.<br/>
     *
     * @param id Khóa chính cần kiểm tra
     * @throws NotFoundException Nếu không tồn tại
     */
    default void existsOrThrow(ID id) {
        if (!existsById(id)) {
            throw new NotFoundException(MessageService.getMessage("resource.not_found", id));
        }
    }

    /**
     * Truy vấn phân trang dữ liệu và trả về dưới dạng class/interface Projection (chỉ lấy các trường cần thiết).<br/>
     * Giúp tối ưu hóa hiệu năng bằng cách không select các trường không cần thiết hoặc dung lượng lớn.
     *
     * @param pageable       Thông tin phân trang và sắp xếp
     * @param projectionType Class/Interface của Projection mong muốn
     * @param <P>            Kiểu Projection
     * @return Trang dữ liệu dạng Projection
     */
    <P> Page<P> findAllProjectedBy(Pageable pageable, Class<P> projectionType);
}
