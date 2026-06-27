package com.common.controller.grid;

import com.common.dto.request.grid.BulkDeleteRequest;
import com.common.dto.request.grid.GridDataRequest;
import com.common.dto.response.grid.GridConfigResponse;
import com.common.service.contract.grid.DynamicGridConfigService;
import com.common.service.contract.grid.DynamicGridDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

/**
 * REST Controller xử lý các endpoint liên quan đến Dynamic Grid.
 * Cung cấp API để lấy cấu hình Grid, lấy dữ liệu bảng và thực thi các thao tác hàng loạt.<br/>
 * Created at 21/06/2026
 * 
 * @author txhoan
 */
@RestController
@RequestMapping("/api/v1/dynamic-grid")
@RequiredArgsConstructor
public class DynamicGridController {

    private final DynamicGridConfigService configService;
    private final DynamicGridDataService dataService;

    /**
     * API lấy cấu hình của một lưới dữ liệu (Dynamic Grid Config).
     * @param gridCode Mã định danh của lưới (VD: "USER_MANAGEMENT").
     * @return GridConfigResponse chứa thông tin cột, bộ lọc, thao tác.
     */
    @GetMapping("/{gridCode}/config")
    public ResponseEntity<GridConfigResponse> getGridConfig(@PathVariable String gridCode) {
        return ResponseEntity.ok(configService.getConfig(gridCode));
    }

    /**
     * API lấy dữ liệu để hiển thị lên lưới dữ liệu dựa vào mã gridCode và tham số phân trang/lọc.
     * @param gridCode Mã định danh của lưới.
     * @param request Payload chứa thông tin phân trang, sort và filter.
     * @return Đối tượng dữ liệu (thường là Page/List) được bọc trong Response.
     */
    @PostMapping("/{gridCode}/data")
    public ResponseEntity<Object> getGridData(@PathVariable String gridCode, @RequestBody GridDataRequest request) {
        return ResponseEntity.ok(dataService.getData(gridCode, request));
    }

    /**
     * API thực thi thao tác xóa hàng loạt cho nhiều bản ghi.
     * @param gridCode Mã định danh của lưới.
     * @param request Chứa danh sách IDs cần xóa.
     * @return 200 OK nếu thành công.
     */
    @PostMapping("/{gridCode}/bulk-delete")
    public ResponseEntity<Void> bulkDelete(@PathVariable String gridCode, @RequestBody BulkDeleteRequest request) {
        dataService.bulkDelete(gridCode, request.getIds());
        return ResponseEntity.ok().build();
    }
}
