package com.common.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * Lớp kiểm thử đơn vị (Unit Test) cho dịch vụ khóa phân tán RedisDistributedLockServiceImpl.<br/>
 * Sử dụng cơ chế giả lập hành vi của Redis bằng Mockito để kiểm tra tính năng khóa/mở khóa đơn luồng và đa luồng.<br/>
 * Đảm bảo cơ chế loại trừ tương hỗ (mutual exclusion) hoạt động chính xác trong môi trường cạnh tranh cao.<br/>
 * Created at 20/06/2026
 *
 * @author txhoan
 */
class RedisDistributedLockServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    private RedisDistributedLockServiceImpl lockService;

    // Set luồng an toàn mô phỏng không gian khóa Redis
    private final Set<String> activeLocks = ConcurrentHashMap.newKeySet();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        lockService = new RedisDistributedLockServiceImpl(redisTemplate);

        activeLocks.clear();

        // Stub setIfAbsent giả lập hành vi khóa Redis
        when(valueOperations.setIfAbsent(anyString(), any(), any(Duration.class))).thenAnswer(invocation -> {
            String key = invocation.getArgument(0);
            return activeLocks.add(key);
        });

        // Stub execute giả lập hành vi chạy Lua Script giải phóng khóa
        when(redisTemplate.execute(any(RedisScript.class), anyList(), any())).thenAnswer(invocation -> {
            List<String> keys = invocation.getArgument(1);
            String key = keys.get(0);
            if (activeLocks.remove(key)) {
                return 1L; // Giải phóng thành công
            }
            return 0L; // Không tồn tại khóa
        });
    }

    @Test
    void testTryLockSuccessAndRelease() {
        String key = "test-lock";
        String val = "token-123";

        // Lần đầu acquire lock phải thành công
        boolean locked = lockService.tryLock(key, val, 1000);
        assertThat(locked).isTrue();

        // Thử acquire lock lần 2 trên cùng key phải thất bại
        boolean lockedAgain = lockService.tryLock(key, val, 1000);
        assertThat(lockedAgain).isFalse();

        // Giải phóng lock thành công
        boolean released = lockService.releaseLock(key, val);
        assertThat(released).isTrue();

        // Sau khi giải phóng, acquire lại phải thành công
        boolean lockedThird = lockService.tryLock(key, val, 1000);
        assertThat(lockedThird).isTrue();
    }

    @Test
    void testConcurrentMutualExclusion() throws InterruptedException {
        String key = "shared-resource";
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            final int id = i;
            executor.submit(() -> {
                try {
                    latch.await(); // Đợi tất cả các luồng sẵn sàng
                    boolean acquired = lockService.tryLock(key, "thread-" + id, 5000);
                    if (acquired) {
                        successCount.incrementAndGet();
                    } else {
                        failCount.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        latch.countDown(); // Kích hoạt chạy đồng thời
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // Đảm bảo chỉ có duy nhất một luồng lấy được khóa
        assertThat(successCount.get()).isEqualTo(1);
        assertThat(failCount.get()).isEqualTo(threadCount - 1);
    }
}
