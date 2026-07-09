package com.common.service.contract.grid;

import com.common.dto.request.grid.GridDataRequest;
import java.util.List;

/**
 * Service xử lý truy vấn dữ liệu và thao tác (C/U/D) trên Dynamic Grid.<br/>
 * Created at 21/06/2026
 * 
 * @author txhoan
 */
public interface DynamicGridDataService {
    
    /**
     * Truy vấn dữ liệu dựa vào cấu hình phân trang và bộ lọc.
     * Tự động áp dụng các quy tắc bảo mật GBAC nếu có.
     * @param gridCode Mã grid.
     * @param request Thông tin truy vấn.
     * @return Danh sách dữ liệu phân trang.
     */
    Object getData(String gridCode, GridDataRequest request);
    
    /**
     * Thực thi nghiệp vụ xóa nhiều bản ghi.
     * @param gridCode Mã grid.
     * @param ids Danh sách id cần xóa.
     */
    void bulkDelete(String gridCode, List<String> ids);
}
