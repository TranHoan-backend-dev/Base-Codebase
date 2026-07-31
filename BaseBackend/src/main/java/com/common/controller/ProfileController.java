package com.common.controller;

import com.common.dto.response.ProfileDTO;
import com.common.model.sql.Profile;
import com.common.service.contract.IProfileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller quản lý Profile Entity.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@RestController
@RequestMapping("${app.api.profiles-prefix:${server.servlet.context-path:/api/v1}/profiles}")
public class ProfileController extends BaseController<Profile, Long, ProfileDTO, ProfileDTO, IProfileService> {

    public ProfileController(IProfileService service) {
        super(service);
    }

    @Override
    protected ProfileDTO toResponse(Profile entity) {
        if (entity == null) return null;
        return ProfileDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .phoneNumber(entity.getPhoneNumber())
                .avatarUrl(entity.getAvatarUrl())
                .position(entity.getPosition())
                .department(entity.getDepartment())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getModifiedAt())
                .build();
    }

    @Override
    protected Profile toEntity(ProfileDTO request) {
        if (request == null) return null;
        return Profile.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .avatarUrl(request.getAvatarUrl())
                .position(request.getPosition())
                .department(request.getDepartment())
                .build();
    }

    @Override
    protected void updateEntity(ProfileDTO request, Profile entity) {
        if (request.getFirstName() != null) entity.setFirstName(request.getFirstName());
        if (request.getLastName() != null) entity.setLastName(request.getLastName());
        if (request.getPhoneNumber() != null) entity.setPhoneNumber(request.getPhoneNumber());
        if (request.getAvatarUrl() != null) entity.setAvatarUrl(request.getAvatarUrl());
        if (request.getPosition() != null) entity.setPosition(request.getPosition());
        if (request.getDepartment() != null) entity.setDepartment(request.getDepartment());
    }
}
