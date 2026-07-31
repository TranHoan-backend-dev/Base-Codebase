package com.common.dto.request.grid;

import lombok.Data;
import java.util.List;

/**
 * Request payload gửi từ Frontend lên Backend để yêu cầu dữ liệu bảng.
 * Chứa thông tin phân trang, các bộ lọc hiện tại và quy tắc sắp xếp.<br/>
 * Created at 21/06/2026
 * 
 * @author txhoan
 */
@Data
public class GridDataRequest {
    private int page;
    private int size;
    private List<SortConfig> sorts;
    private List<FilterConfig> filters;

    @Data
    public static class SortConfig {
        private String field;
        private String direction; // ASC, DESC
    }

    @Data
    public static class FilterConfig {
        private String field;
        private String operator; // EQUALS, CONTAINS, GREATER_THAN, etc.
        private Object value;
    }
}
