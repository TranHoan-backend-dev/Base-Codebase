package com.common.service.impl;

import com.common.service.contract.IDistributedLockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;

/**
 * Lớp triển khai khóa phân tán lập trình sử dụng RedisTemplate và Lua Script giải phóng khóa an toàn.<br/>
 * Tránh trường hợp giải phóng nhầm khóa của thread khác bằng cách xác thực giá trị định danh duy nhất (lockValue).<br/>
 * Created at 20/06/2026
 *
 * @see <a href="../../../../../resources/docs/redis/distributed-lock.md">Distributed Lock Specification</a>
 * @author txhoan
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RedisDistributedLockServiceImpl implements IDistributedLockService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String RELEASE_LOCK_SCRIPT = """
            if redis.call('get', KEYS[1]) == ARGV[1] then
                return redis.call('del', KEYS[1])
            else
                return 0
            end
            """;

    @Override
    public boolean tryLock(String lockKey, String lockValue, long expireTimeInMs) {
        try {
            log.debug("Cố gắng acquire lock: {}, với value: {}, TTL: {} ms", lockKey, lockValue, expireTimeInMs);
            Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, Duration.ofMillis(expireTimeInMs));
            return Boolean.TRUE.equals(result);
        } catch (Exception e) {
            log.error("Lỗi khi cố gắng acquire lock: {}", lockKey, e);
            return false;
        }
    }

    @Override
    public boolean tryLockWithTimeout(String lockKey, String lockValue, long expireTimeInMs, long timeoutInMs) {
        long endTime = System.currentTimeMillis() + timeoutInMs;
        log.info("Cố gắng acquire lock kèm timeout: {}, value: {}, TTL: {}, Timeout: {}", lockKey, lockValue, expireTimeInMs, timeoutInMs);

        while (System.currentTimeMillis() < endTime) {
            if (tryLock(lockKey, lockValue, expireTimeInMs)) {
                return true;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                log.warn("Thread bị ngắt quãng khi đang đợi lock: {}", lockKey, e);
                Thread.currentThread().interrupt();
                return false;
            }
        }
        log.warn("Không thể acquire lock {} trong vòng {} ms (timeout)", lockKey, timeoutInMs);
        return false;
    }

    @Override
    public boolean releaseLock(String lockKey, String lockValue) {
        try {
            log.debug("Cố gắng release lock: {} với value: {}", lockKey, lockValue);
            RedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_SCRIPT, Long.class);
            Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), lockValue);
            return Long.valueOf(1).equals(result);
        } catch (Exception e) {
            log.error("Lỗi khi cố gắng release lock: {}", lockKey, e);
            return false;
        }
    }
}
