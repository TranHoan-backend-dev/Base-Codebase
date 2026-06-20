package com.common.controller;

import com.common.dto.request.PagingRequest;
import com.common.dto.response.WrapperApiResponse;
import com.common.service.MessageService;
import com.common.service.contract.IBaseService;
import com.common.utilities.Utils;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Lớp base controller chứa các API cơ bản (CRUD, lấy theo ID, lấy phân trang, phân trang chiếu thuộc tính).<br/>
 * Tránh lặp lại boilerplate code ở các Controller nghiệp vụ cụ thể.<br/>
 * Created at 10/06/2026, Updated at 19/06/2026
 *
 * @param <TEntity>   Kiểu dữ liệu Entity/Document
 * @param <TId>       Kiểu dữ liệu khóa chính (Long, String, etc.)
 * @param <TRequest>  Kiểu dữ liệu DTO Request dùng cho Create/Update
 * @param <TResponse> Kiểu dữ liệu DTO Response trả về cho Client
 * @param <TService>  Kiểu dữ liệu Service kế thừa từ IBaseService
 * @see <a href="../../../../resources/docs/spring_structure/controller-guide.md">BaseController Specification Guide</a>
 * @author txhoan
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class BaseController<
        TEntity,
        TId,
        TRequest,
        TResponse,
        TService extends IBaseService<TEntity, TId>> {

    TService service;

    /**
     * Map từ Entity sang DTO Response.<br/>
     * Các lớp con cần triển khai phương thức này (bằng cách dùng MapStruct mapper hoặc map tay).<br/>
     * Created at 19/06/2026
     *
     * @param entity Đối tượng thực thể database
     * @return DTO tương ứng
     */
    protected abstract TResponse toResponse(TEntity entity);

    /**
     * Map từ DTO Request sang Entity.<br/>
     * Các lớp con cần triển khai phương thức này.<br/>
     * Created at 19/06/2026
     *
     * @param request DTO đầu vào của Client
     * @return Đối tượng thực thể database tương ứng
     */
    protected abstract TEntity toEntity(TRequest request);

    /**
     * Cập nhật các trường thông tin của Entity từ DTO Request.<br/>
     * Thường dùng cho nghiệp vụ Update nhằm sao chép các thuộc tính sửa đổi sang Entity cũ.<br/>
     * Created at 19/06/2026
     *
     * @param request DTO sửa đổi đầu vào
     * @param entity  Entity cũ lấy từ cơ sở dữ liệu
     */
    protected abstract void updateEntity(TRequest request, TEntity entity);

    /**
     * API tạo mới bản ghi.<br/>
     * Nhận vào DTO Request, chuyển đổi thành Entity, thực hiện lưu trữ thông qua Service
     * và trả về mã trạng thái HTTP 201 Created cùng cấu trúc WrapperApiResponse đồng nhất.<br/>
     * Created at 19/06/2026
     *
     * @param request DTO tạo mới đầu vào
     * @return ResponseEntity chứa thông báo kết quả
     */
    @PostMapping
    public ResponseEntity<WrapperApiResponse> create(@RequestBody @Valid TRequest request) {
        // Chuyển đổi DTO sang Entity
        var entity = toEntity(request);
        // Lưu trữ Entity xuống Database
        var created = service.create(entity);
        // Trả về kết quả tạo thành công (201 Created)
        return Utils.returnCreatedResponse(MessageService.getMessage("controller.create_success"));
    }

    /**
     * API cập nhật bản ghi theo ID.<br/>
     * Tìm kiếm bản ghi cũ, áp dụng các thuộc tính thay đổi từ Request và lưu lại.<br/>
     * Created at 19/06/2026
     *
     * @param id      Khóa chính cần cập nhật
     * @param request DTO chứa các thông tin chỉnh sửa
     * @return ResponseEntity chứa DTO Response của đối tượng sau chỉnh sửa
     */
    @PutMapping("/{id}")
    public ResponseEntity<WrapperApiResponse> update(@PathVariable TId id, @RequestBody @Valid TRequest request) {
        // Tìm bản ghi hiện tại hoặc ném lỗi 404
        var entity = service.findByIdOrThrow(id);
        // Áp dụng các thay đổi từ DTO vào thực thể DB
        updateEntity(request, entity);
        // Lưu thực thể
        service.update(entity);
        // Trả về phản hồi thành công (200 OK) kèm DTO Response
        return Utils.returnOkResponse(MessageService.getMessage("controller.update_success"), toResponse(entity));
    }

    /**
     * API xóa bản ghi theo ID.<br/>
     * Hỗ trợ tự động xóa mềm (nếu Entity hỗ trợ) hoặc xóa cứng khỏi cơ sở dữ liệu.<br/>
     * Created at 19/06/2026
     *
     * @param id Khóa chính cần xóa
     * @return ResponseEntity thông báo xóa thành công
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<WrapperApiResponse> delete(@PathVariable TId id) {
        // Tìm bản ghi hiện tại hoặc ném lỗi 404
        var entity = service.findByIdOrThrow(id);
        // Xóa bản ghi (tự động xóa mềm/xóa cứng trong Service)
        service.delete(entity);
        // Trả về phản hồi thành công (200 OK)
        return Utils.returnOkResponse(MessageService.getMessage("controller.delete_success"), null);
    }

    /**
     * API lấy thông tin chi tiết của một bản ghi theo ID.<br/>
     * Trả về dữ liệu đã được map qua DTO Response để ẩn các thông tin nhạy cảm của Entity.<br/>
     * Created at 10/06/2026, Updated at 19/06/2026
     *
     * @param id Khóa chính cần lấy
     * @return ResponseEntity chứa thông tin bản ghi đã được map qua DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<WrapperApiResponse> getById(@PathVariable TId id) {
        // Tìm thực thể hoặc ném lỗi 404
        var entity = service.findByIdOrThrow(id);
        // Chuyển đổi thực thể sang DTO Response và trả về client
        return Utils.returnOkResponse(MessageService.getMessage("controller.get_success"), toResponse(entity));
    }

    /**
     * API lấy danh sách bản ghi phân trang, sắp xếp và tìm kiếm lọc động.<br/>
     * Trả về dữ liệu được bọc trong Page chứa danh sách DTO Response.<br/>
     * Created at 10/06/2026, Updated at 19/06/2026
     *
     * @param request Payload chứa cấu hình phân trang, tìm kiếm và lọc
     * @return ResponseEntity chứa trang dữ liệu DTO
     */
    @PostMapping("/paging")
    public ResponseEntity<WrapperApiResponse> getPaged(@RequestBody @Valid PagingRequest request) {
        // Lấy danh sách phân trang
        var page = service.getPaginated(request);
        // Ánh xạ toàn bộ trang Entity sang trang DTO và phản hồi
        return Utils.returnOkResponse(MessageService.getMessage("controller.paging_success"), page.map(this::toResponse));
    }

    /**
     * API lấy danh sách bản ghi phân trang và chỉ lọc lấy các thuộc tính được chọn (Dynamic Projection).<br/>
     * Trả về dữ liệu trang dạng Map chứa các cặp Key-Value tương ứng với trường được chọn.<br/>
     * Created at 19/06/2026
     *
     * @param request Payload chứa cấu hình phân trang, bộ lọc và danh sách các trường cần lấy (fields)
     * @return ResponseEntity chứa trang dữ liệu dạng Map các thuộc tính được chọn
     * @see <a href="../../../../resources/docs/spring_structure/controller-guide.md">BaseController Specification Guide (Dynamic Projection Section)</a>
     */
    @PostMapping("/paging/projected")
    public ResponseEntity<WrapperApiResponse> getPagedProjected(@RequestBody @Valid PagingRequest request) {
        // Thực hiện truy vấn chiếu thuộc tính phân trang
        var page = service.getPaginatedProjected(request);
        // Phản hồi kết quả
        return Utils.returnOkResponse(MessageService.getMessage("controller.paging_success"), page);
    }
}
