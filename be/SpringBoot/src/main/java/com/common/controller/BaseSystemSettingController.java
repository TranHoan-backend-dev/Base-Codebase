package com.common.controller;

import com.common.dto.request.SystemSettingRequest;
import com.common.dto.response.SystemSettingResponse;
import com.common.model.sql.SystemSetting;
import com.common.service.contract.ISystemSettingService;

/**
 * REST Controller trừu tượng tích hợp sẵn các API thao tác cấu hình hệ thống (CRUD, phân trang).<br/>
 * Các microservices con kế thừa controller này để phục vụ quản lý tham số hệ thống chạy động.<br/>
 * Created at 20/06/2026
 *
 * @see <a href="../../../../resources/docs/spring_structure/system-settings-guide.md">System Settings Specification Guide</a>
 * @see <a href="../../../../resources/.specify/openapi-spec.md">OpenAPI Specification Spec</a>
 * @author txhoan
 */
public abstract class BaseSystemSettingController extends BaseController<
        SystemSetting,
        Long,
        SystemSettingRequest,
        SystemSettingResponse,
        ISystemSettingService> {

    protected BaseSystemSettingController(ISystemSettingService service) {
        super(service);
    }

    /**
     * Chuyển đổi từ Entity SystemSetting sang DTO Response.<br/>
     * Created at 20/06/2026
     */
    @Override
    protected SystemSettingResponse toResponse(SystemSetting entity) {
        if (entity == null) {
            return null;
        }
        return SystemSettingResponse.builder()
                .id(entity.getId())
                .settingKey(entity.getSettingKey())
                .settingValue(entity.getSettingValue())
                .description(entity.getDescription())
                .build();
    }

    /**
     * Chuyển đổi từ DTO Request sang Entity SystemSetting mới.<br/>
     * Created at 20/06/2026
     */
    @Override
    protected SystemSetting toEntity(SystemSettingRequest request) {
        if (request == null) {
            return null;
        }
        return SystemSetting.builder()
                .settingKey(request.getSettingKey())
                .settingValue(request.getSettingValue())
                .description(request.getDescription())
                .build();
    }

    /**
     * Cập nhật các trường thông tin của thực thể cấu hình cũ từ DTO Request.<br/>
     * Created at 20/06/2026
     */
    @Override
    protected void updateEntity(SystemSettingRequest request, SystemSetting entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setSettingKey(request.getSettingKey());
        entity.setSettingValue(request.getSettingValue());
        entity.setDescription(request.getDescription());
    }
}
