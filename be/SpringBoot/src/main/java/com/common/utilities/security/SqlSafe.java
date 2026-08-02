package com.common.utilities.security;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation ràng buộc kiểm tra hợp lệ dữ liệu đầu vào chống tấn công SQL Injection.<br/>
 * Thường được áp dụng lên các trường thông tin trong DTO (ví dụ: username, search keyword).<br/>
 * Created at 19/06/2026
 *
 * @see <a href="../../../../../resources/docs/security_filters/security-filter-guide.md">Security and Web Filter Guide</a>
 * @see "Common/src/main/resources/docs/security_filters/security-filter-guide.md"
 * @author txhoan
 */
@Documented
@Constraint(validatedBy = SqlSafeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlSafe {

    /**
     * Thông báo lỗi mặc định khi phát hiện ký tự nguy hại.
     *
     * @return chuỗi thông điệp cảnh báo
     */
    String message() default "{validation.sql_safe}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
