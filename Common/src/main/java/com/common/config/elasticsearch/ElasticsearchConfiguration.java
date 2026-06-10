package com.common.config.elasticsearch;

import com.common.config.YamlPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;

/**
 * Cấu hình Spring Data Elasticsearch (NoSQL).<br/>
 * Created at 10/06/2026
 *
 * @author txhoan
 */
@Configuration
@PropertySource(value = "classpath:application.yaml", factory = YamlPropertySourceFactory.class)
@EnableElasticsearchAuditing(auditorAwareRef = "auditorProvider")
class ElasticsearchConfiguration {
}
