package com.common.dto.response;

import com.common.model.enumerate.UserRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

/**
 * DTO phản hồi kết quả xác thực Auth (Access Token, Refresh Token, User context).
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse {

    String accessToken;

    String refreshToken;

    @lombok.Builder.Default
    String tokenType = "Bearer";

    long expiresIn;

    Long userId;

    String username;

    String email;

    Long tenantId;

    String tenantCode;

    Set<UserRole> roles;
}
