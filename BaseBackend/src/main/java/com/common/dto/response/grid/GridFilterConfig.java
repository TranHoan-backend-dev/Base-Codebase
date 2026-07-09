package com.common.dto.response.grid;

import lombok.Data;
import lombok.Builder;
import java.util.List;

/**
 * DTO đại diện cho cấu hình một trường bộ lọc (Filter) của Grid.
 * Hỗ trợ các kiểu như TEXT_INPUT, SELECT, hoặc gọi API động (ASYNC_SELECT).<br/>
 * Created at 21/06/2026
 * 
 * @author txhoan
 */
@Data
@Builder
public class GridFilterConfig {
    private String field;
    private String label;
    private String type; // TEXT_INPUT, SELECT, ASYNC_SELECT
    private String placeholder;
    private String defaultValue;
    private List<OptionEntry> options;
    private String apiEndpoint; // For ASYNC_SELECT

    /**
     * DTO thông tin một option nằm trong bộ lọc kiểu SELECT.
     */
    @Data
    @Builder
    public static class OptionEntry {
        private String label;
        private String value;
    }
}
