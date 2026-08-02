package com.common.dto.response.grid;

import lombok.Data;
import lombok.Builder;
import java.util.Map;

/**
 * DTO đại diện cho cấu hình của một cột trong Dynamic Grid.
 * Quy định cách thức render dữ liệu (TEXT, BADGE, LINK) và các tuỳ biến giao diện.<br/>
 * Created at 21/06/2026
 * 
 * @author txhoan
 */
@Data
@Builder
public class GridColumnConfig {
    private String field;
    private String header;
    private String type; // TEXT, NUMBER, BADGE, LINK
    private boolean sortable;
    private boolean hidden;
    private String width;
    private String align;
    
    private ActionConfig actionConfig;
    private Map<String, String> customStyles;
    private Map<String, ValueMapEntry> valueMap;

    /**
     * Thông tin ánh xạ (Map) từ giá trị ENUM ở Database sang Text hiển thị và màu sắc giao diện.
     */
    @Data
    @Builder
    public static class ValueMapEntry {
        private String label;
        private String color;
    }
}
