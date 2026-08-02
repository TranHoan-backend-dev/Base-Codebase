package com.common.config.redis;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Cấu hình hệ thống Redis dùng chung cho việc lưu trữ cache, lưu trữ phân tán, và lock phân tán.<br/>
 * Thiết lập các Serializer dạng JSON (Jackson) để tránh lỗi định dạng binary mặc định của Java.<br/>
 * Created at 20/06/2026
 *
 * @see <a href="../../../../../resources/docs/redis/redis-guide.md">Redis Specification Guide</a>
 * @author txhoan
 */
@Configuration
@EnableCaching
public class RedisConfiguration {

    /**
     * Tạo Bean RedisTemplate hỗ trợ lưu trữ Key-Value dạng JSON.<br/>
     * Dùng StringRedisSerializer cho Key và GenericJackson2JsonRedisSerializer cho Value.<br/>
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();

        // Cấu hình Key & Value Serializer
        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(jsonSerializer);

        // Cấu hình Hash Key & Value Serializer
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(jsonSerializer);

        template.afterPropertiesSet();
        return template;
    }

    /**
     * Cấu hình CacheManager dùng Redis cho các phương thức annotation @Cacheable.<br/>
     * Thời gian sống (TTL) mặc định của cache là 10 phút, không lưu các giá trị null.<br/>
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10)) // TTL mặc định 10 phút
                .disableCachingNullValues()       // Không lưu giá trị null
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(cacheConfig)
                .build();
    }
}
