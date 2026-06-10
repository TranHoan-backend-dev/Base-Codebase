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
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Lớp base controller chứa các API cơ bản (lấy theo ID, lấy phân trang).<br/>
 * Created at 10/06/2026
 *
 * @param <TEntity> Kiểu dữ liệu Entity/Document
 * @param <TId>     Kiểu dữ liệu khóa chính (Long, String, etc.)
 * @param <TService> Kiểu dữ liệu Service kế thừa từ IBaseService
 * @author txhoan
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class BaseController<
        TEntity,
        TId,
        TService extends IBaseService<TEntity, TId>> {

    TService service;

    /**
     * API lấy thông tin chi tiết của một bản ghi theo ID.<br/>
     *
     * @param id Khóa chính cần lấy
     * @return ResponseEntity chứa thông tin bản ghi
     */
    @GetMapping("/{id}")
    public ResponseEntity<WrapperApiResponse> getById(@PathVariable TId id) {
        var entity = service.findByIdOrThrow(id);
        return Utils.returnOkResponse(MessageService.getMessage("controller.get_success"), entity);
    }

    /**
     * API lấy danh sách bản ghi phân trang.<br/>
     *
     * @param request Payload chứa cấu hình phân trang, tìm kiếm và lọc
     * @return ResponseEntity chứa trang dữ liệu
     */
    @PostMapping("/paging")
    public ResponseEntity<WrapperApiResponse> getPaged(@RequestBody @Valid PagingRequest request) {
        var page = service.getPaginated(request);
        return Utils.returnOkResponse(MessageService.getMessage("controller.paging_success"), page);
    }
}
