package com.common.dto.response.grid;

import lombok.Data;
import lombok.Builder;

/**
 * DTO đại diện cho cấu hình hiển thị của một nút hành động (Button) trên Grid.
 * Có thể đặt trên Toolbar, trên từng dòng (ROW) hoặc khi chọn nhiều dòng (BULK).<br/>
 * Created at 21/06/2026
 * 
 * @author txhoan
 */
@Data
@Builder
public class GridActionConfig {
    private String code;
    private String label;
    private String icon;
    private String position; // TOOLBAR, ROW, BULK
    private String buttonType; // primary, danger, default
    private ActionConfig actionConfig;
}
