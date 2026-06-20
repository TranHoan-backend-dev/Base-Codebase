package com.common.config.jpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Cấu hình JPA Auditing dùng chung cho toàn bộ Spring context.<br/>
 * {@code @EnableJpaAuditing} được đặt ở đây (không phải trong từng JpaConfig riêng)
 * vì Auditing là cấu hình chung, áp dụng cho cả 2 datasource (business và system).<br/>
 * {@link org.springframework.data.jpa.domain.support.AuditingEntityListener} hoạt động
 * ở tầng entity nên không bị giới hạn bởi từng {@code EntityManagerFactory}.<br/>
 * Created at 06/04/2026
 *
 * @author txhoan
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
class JpaConfiguration {
}
