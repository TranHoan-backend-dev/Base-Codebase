package com.common.utilities;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation dùng để validate các chuỗi đầu vào (Request body, query parameter, ...)
 * đảm bảo chúng không chứa mã độc hại như SQL Injection hay XSS.
 *
 * @author txhoan
 */
@Documented
@Constraint(validatedBy = SafeStringValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SafeString {
    String message() default "Dữ liệu đầu vào chứa các ký tự hoặc từ khóa không an toàn.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
