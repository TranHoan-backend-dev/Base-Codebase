package com.common.controller;

import com.common.dto.response.TenantDTO;
import com.common.model.enumerate.EntityStatus;
import com.common.model.sql.Tenant;
import com.common.service.contract.ITenantService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller quản lý Tenant Entity.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@RestController
@RequestMapping("${app.api.tenants-prefix:${server.servlet.context-path:/api/v1}/tenants}")
public class TenantController extends BaseController<Tenant, Long, TenantDTO, TenantDTO, ITenantService> {

    public TenantController(ITenantService service) {
        super(service);
    }

    @Override
    protected TenantDTO toResponse(Tenant entity) {
        if (entity == null) return null;
        return TenantDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .code(entity.getCode())
                .domain(entity.getDomain())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .build();
    }

    @Override
    protected Tenant toEntity(TenantDTO request) {
        if (request == null) return null;
        return Tenant.builder()
                .name(request.getName())
                .code(request.getCode())
                .domain(request.getDomain())
                .status(request.getStatus() != null ? request.getStatus() : EntityStatus.ACTIVE)
                .build();
    }

    @Override
    protected void updateEntity(TenantDTO request, Tenant entity) {
        if (request.getName() != null) entity.setName(request.getName());
        if (request.getCode() != null) entity.setCode(request.getCode());
        if (request.getDomain() != null) entity.setDomain(request.getDomain());
        if (request.getStatus() != null) entity.setStatus(request.getStatus());
    }
}
