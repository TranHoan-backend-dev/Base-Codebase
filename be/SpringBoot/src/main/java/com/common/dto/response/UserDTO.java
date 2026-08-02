package com.common.dto.response;

import com.common.model.enumerate.EntityStatus;
import com.common.model.enumerate.UserRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO đại diện cho dữ liệu User.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {

    Long id;

    String username;

    String email;

    EntityStatus status;

    Long tenantId;

    String tenantCode;

    Set<UserRole> roles;

    ProfileDTO profile;

    LocalDateTime createdAt;

    LocalDateTime modifiedAt;
}
