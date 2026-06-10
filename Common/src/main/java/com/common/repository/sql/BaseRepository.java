package com.common.repository.sql;

import com.common.model.sql.BaseModel;
import com.common.exception.NotFoundException;
import com.common.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Lớp base repository chứa các method thao tác dữ liệu cơ bản cho JPA (SQL).<br/>
 * Created at 10/06/2026
 *
 * @param <T>  Kiểu dữ liệu Entity kế thừa từ BaseModel
 * @param <ID> Kiểu dữ liệu của khóa chính Entity
 * @author txhoan
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseModel, ID> extends JpaRepository<T, ID> {

    /**
     * Tìm kiếm Entity theo ID, nếu không tìm thấy sẽ ném ra exception NotFoundException.<br/>
     *
     * @param id Khóa chính cần tìm
     * @return Entity tương ứng
     * @throws NotFoundException Nếu không tìm thấy
     */
    default T findByIdOrThrow(ID id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException(MessageService.getMessage("resource.not_found", id)));
    }

    /**
     * Tìm kiếm Entity theo ID, nếu không tìm thấy sẽ ném ra exception NotFoundException với message tùy chọn.<br/>
     *
     * @param id      Khóa chính cần tìm
     * @param message Thông điệp lỗi tùy chỉnh
     * @return Entity tương ứng
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
     * Giúp tối ưu hóa hiệu năng bằng cách không select các trường không cần thiết hoặc dung lượng lớn (CLOB, BLOB, relations).
     *
     * @param pageable       Thông tin phân trang và sắp xếp
     * @param projectionType Class/Interface của Projection mong muốn
     * @param <P>            Kiểu Projection
     * @return Trang dữ liệu dạng Projection
     */
    <P> Page<P> findAllProjectedBy(Pageable pageable, Class<P> projectionType);
}
