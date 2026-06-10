package com.common.config.mongo;

import com.common.config.audit.AuditorAwareImpl;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation kích hoạt cấu hình MongoDB (NoSQL) của Common library.<br/>
 * Created at 10/06/2026
 *
 * @author txhoan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MongoConfiguration.class, AuditorAwareImpl.class})
public @interface EnableCommonMongo {
}
