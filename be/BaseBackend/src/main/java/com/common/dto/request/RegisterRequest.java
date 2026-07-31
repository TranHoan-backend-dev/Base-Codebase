package com.common.dto.request;

import com.common.utilities.SharedConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * DTO yêu cầu đăng ký tài khoản & công ty mới.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {

    @NotBlank(message = "{auth.tenant_name_required}")
    String tenantName;

    @NotBlank(message = "{auth.tenant_code_required}")
    String tenantCode;

    @NotBlank(message = "{auth.username_required}")
    @Size(min = 3, max = 50)
    String username;

    @NotBlank(message = "{auth.email_required}")
    @Email(message = "{auth.email_invalid}")
    String email;

    @NotBlank(message = "{auth.password_required}")
    @Pattern(regexp = SharedConstants.PASSWORD_PATTERN, message = "{auth.password_invalid}")
    String password;

    String firstName;

    String lastName;

    @Pattern(regexp = SharedConstants.PHONE_PATTERN, message = "{auth.phone_invalid}")
    String phoneNumber;
}
