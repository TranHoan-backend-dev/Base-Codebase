package com.common.config.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Service lưu trữ và kiểm tra Revoked Tokens / Refresh Token Session trên Redis.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private static final String BLACKLIST_PREFIX = "jwt:blacklist:";
    private static final String REFRESH_PREFIX = "jwt:refresh:";

    private final StringRedisTemplate redisTemplate;

    /**
     * Thêm Access Token vào danh sách đen (Blacklist) khi Đăng xuất.
     */
    public void blacklistToken(String token, long remainingTtlMs) {
        if (remainingTtlMs <= 0) return;
        String key = BLACKLIST_PREFIX + token;
        try {
            redisTemplate.opsForValue().set(key, "revoked", remainingTtlMs, TimeUnit.MILLISECONDS);
            log.info("Blacklisted token: {} for {} ms", key, remainingTtlMs);
        } catch (Exception e) {
            log.error("Failed to blacklist token in Redis: {}", e.getMessage());
        }
    }

    /**
     * Kiểm tra token có thuộc danh sách đen hay không.
     */
    public boolean isBlacklisted(String token) {
        String key = BLACKLIST_PREFIX + token;
        try {
            Boolean exists = redisTemplate.hasKey(key);
            return Boolean.TRUE.equals(exists);
        } catch (Exception e) {
            log.error("Error checking token blacklist status in Redis: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Lưu Refresh Token hợp lệ của User.
     */
    public void storeRefreshToken(String username, String refreshToken, long ttlMs) {
        String key = REFRESH_PREFIX + username;
        try {
            redisTemplate.opsForValue().set(key, refreshToken, ttlMs, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("Failed to store refresh token in Redis: {}", e.getMessage());
        }
    }

    /**
     * Lấy Refresh Token hợp lệ của User từ Redis.
     */
    public String getRefreshToken(String username) {
        String key = REFRESH_PREFIX + username;
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("Failed to get refresh token from Redis: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Xóa Refresh Token của User khi Đăng xuất.
     */
    public void revokeRefreshToken(String username) {
        String key = REFRESH_PREFIX + username;
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("Failed to revoke refresh token in Redis: {}", e.getMessage());
        }
    }
}
