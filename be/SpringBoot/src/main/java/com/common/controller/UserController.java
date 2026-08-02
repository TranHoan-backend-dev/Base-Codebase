package com.common.controller;

import com.common.dto.response.UserDTO;
import com.common.model.enumerate.EntityStatus;
import com.common.model.sql.User;
import com.common.service.contract.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller quản lý User Entity.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@RestController
@RequestMapping("${app.api.users-prefix:${server.servlet.context-path:/api/v1}/users}")
public class UserController extends BaseController<User, Long, UserDTO, UserDTO, IUserService> {

    public UserController(IUserService service) {
        super(service);
    }

    @Override
    protected UserDTO toResponse(User entity) {
        if (entity == null) return null;
        return UserDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .status(entity.getStatus())
                .tenantId(entity.getTenant() != null ? entity.getTenant().getId() : null)
                .tenantCode(entity.getTenant() != null ? entity.getTenant().getCode() : null)
                .roles(entity.getRoles())
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .build();
    }

    @Override
    protected User toEntity(UserDTO request) {
        if (request == null) return null;
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .status(request.getStatus() != null ? request.getStatus() : EntityStatus.ACTIVE)
                .roles(request.getRoles())
                .build();
    }

    @Override
    protected void updateEntity(UserDTO request, User entity) {
        if (request.getEmail() != null) entity.setEmail(request.getEmail());
        if (request.getStatus() != null) entity.setStatus(request.getStatus());
        if (request.getRoles() != null) entity.setRoles(request.getRoles());
    }
}
