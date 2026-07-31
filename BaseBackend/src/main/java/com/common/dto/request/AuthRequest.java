package com.common.dto.request;

import com.common.utilities.SharedConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * DTO yêu cầu đăng nhập.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthRequest {

    @NotBlank(message = "{auth.username_required}")
    @Pattern(regexp = SharedConstants.USERNAME_OR_EMAIL_PATTERN, message = "{auth.username_or_email_invalid}")
    String username;

    @NotBlank(message = "{auth.password_required}")
    @Pattern(regexp = SharedConstants.PASSWORD_PATTERN, message = "{auth.password_invalid}")
    String password;
}
