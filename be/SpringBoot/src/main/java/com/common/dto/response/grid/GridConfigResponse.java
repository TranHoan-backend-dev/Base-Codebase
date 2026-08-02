package com.common.dto.response.grid;

import lombok.Data;
import lombok.Builder;
import java.util.List;
import java.util.Map;

/**
 * DTO đại diện cho toàn bộ cấu hình của một bảng dữ liệu động (Server-Driven UI).
 * Chứa thông tin về layout, các cột, bộ lọc và các nút hành động.<br/>
 * Created at 21/06/2026
 * 
 * @author txhoan
 */
@Data
@Builder
public class GridConfigResponse {
    private String gridCode;
    private String title;
    private LayoutOptions layoutOptions;
    private List<GridColumnConfig> columns;
    private List<GridFilterConfig> filters;
    private List<GridActionConfig> actions;

    /**
     * Cấu hình hiển thị chung của lưới dữ liệu (phân trang, chọn dòng).
     */
    @Data
    @Builder
    public static class LayoutOptions {
        private boolean rowSelection;
        private boolean pagination;
        private int defaultPageSize;
        private List<Integer> pageSizeOptions;
        private boolean stickyHeader;
    }
}
