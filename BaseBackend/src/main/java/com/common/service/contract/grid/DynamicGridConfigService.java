package com.common.service.contract.grid;

import com.common.dto.response.grid.GridConfigResponse;

/**
 * Service xử lý logic lấy cấu hình của Dynamic Grid.<br/>
 * Created at 21/06/2026
 * 
 * @author txhoan
 */
public interface DynamicGridConfigService {
    
    /**
     * Lấy toàn bộ cấu hình cột, thao tác và bộ lọc của bảng theo mã grid.
     * @param gridCode Mã grid (VD: "USER_MANAGEMENT").
     * @return GridConfigResponse Cấu hình chi tiết bảng.
     */
    GridConfigResponse getConfig(String gridCode);
}
