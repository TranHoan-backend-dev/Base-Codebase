package com.common.config.jpa;

import com.common.config.audit.AuditorAwareImpl;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation kích hoạt cấu hình JPA của Common library.<br/>
 * Khi sử dụng annotation này tại {@code @SpringBootApplication} của service, sẽ tự động kích hoạt:<br/>
 * <ul>
 *   <li>{@link BusinessJpaConfig} — datasource nghiệp vụ (@Primary), scan {@code com.*.model.business}</li>
 *   <li>{@link SystemJpaConfig} — datasource kỹ thuật, scan {@code com.common.model.sql}</li>
 *   <li>{@link AuditorAwareImpl} — tự động điền {@code createdBy} / {@code modifiedBy}</li>
 * </ul>
 * Created at 10/06/2026
 *
 * @author txhoan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({JpaConfiguration.class, BusinessJpaConfig.class, SystemJpaConfig.class, AuditorAwareImpl.class})
public @interface EnableCommonJpa {
}
