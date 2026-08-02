package com.common.dto.response.grid;

import lombok.Data;
import lombok.Builder;

/**
 * Cấu hình chi tiết cho một hành động (Action).
 * Xác định hành động này sẽ chuyển trang (ROUTE/EXTERNAL_LINK) hay gọi API trực tiếp.<br/>
 * Created at 21/06/2026
 * 
 * @author txhoan
 */
@Data
@Builder
public class ActionConfig {
    private String type; // ROUTE, EXTERNAL_LINK, API
    private String target; // For ROUTE/EXTERNAL_LINK
    private String method; // GET, POST, PUT, DELETE for API
    private String endpoint; // For API
    private String confirmMessage; // For API
}
