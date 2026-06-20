package com.common.config.redis;

import org.junit.jupiter.api.Test;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Lớp kiểm thử đơn vị (Unit Test) cho cấu hình RedisConfiguration.<br/>
 * Đảm bảo các Bean RedisTemplate và CacheManager được cấu hình đúng loại Serializer mong muốn.<br/>
 *
 * @author txhoan
 */
class RedisConfigurationTest {

    @Configuration
    static class TestConfig {
        @Bean
        public RedisConnectionFactory redisConnectionFactory() {
            return mock(RedisConnectionFactory.class);
        }
    }

    @Test
    void testRedisTemplateAndCacheManagerBeans() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.register(TestConfig.class, RedisConfiguration.class);
            context.refresh();

            // 1. Kiểm tra sự tồn tại của bean redisTemplate
            assertThat(context.containsBean("redisTemplate")).isTrue();
            RedisTemplate<?, ?> redisTemplate = context.getBean("redisTemplate", RedisTemplate.class);
            assertThat(redisTemplate).isNotNull();

            // 2. Kiểm tra cấu hình các bộ Serializer của RedisTemplate
            assertThat(redisTemplate.getKeySerializer()).isInstanceOf(StringRedisSerializer.class);
            assertThat(redisTemplate.getValueSerializer()).isInstanceOf(GenericJackson2JsonRedisSerializer.class);
            assertThat(redisTemplate.getHashKeySerializer()).isInstanceOf(StringRedisSerializer.class);
            assertThat(redisTemplate.getHashValueSerializer()).isInstanceOf(GenericJackson2JsonRedisSerializer.class);

            // 3. Kiểm tra sự tồn tại của bean cacheManager
            assertThat(context.containsBean("cacheManager")).isTrue();
            CacheManager cacheManager = context.getBean("cacheManager", CacheManager.class);
            assertThat(cacheManager).isInstanceOf(RedisCacheManager.class);
        }
    }
}
