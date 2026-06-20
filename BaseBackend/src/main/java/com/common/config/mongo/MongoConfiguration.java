package com.common.config.mongo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * Cấu hình auditing cho MongoDB (NoSQL).<br/>
 * Created at 10/06/2026
 *
 * @author txhoan
 */
@Configuration
@EnableMongoAuditing(auditorAwareRef = "auditorProvider")
class MongoConfiguration {
}
