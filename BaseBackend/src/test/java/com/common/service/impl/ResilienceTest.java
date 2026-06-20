package com.common.service.impl;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Lớp kiểm thử đơn vị cho Circuit Breaker của Resilience4j.<br/>
 * Kiểm chứng các trạng thái CLOSED -> OPEN -> HALF-OPEN chuyển đổi chính xác.<br/>
 * Created at 20/06/2026
 *
 * @author txhoan
 */
class ResilienceTest {

    private CircuitBreaker circuitBreaker;

    @BeforeEach
    void setUp() {
        // Cấu hình Circuit Breaker với cửa sổ trượt nhỏ để dễ kiểm thử
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .slidingWindowSize(5)
                .minimumNumberOfCalls(5)
                .failureRateThreshold(50.0f) // 50% lỗi sẽ ngắt mạch
                .waitDurationInOpenState(Duration.ofMillis(200)) // Thời gian chờ ở trạng thái OPEN ngắn
                .permittedNumberOfCallsInHalfOpenState(2)
                .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);
        circuitBreaker = registry.circuitBreaker("testCircuitBreaker");
    }

    @Test
    void testCircuitBreakerStateTransitions() throws InterruptedException {
        // 1. Kiểm tra trạng thái ban đầu là CLOSED
        assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.CLOSED);

        // Chuẩn bị một hàm Supplier mô phỏng thành công
        Supplier<String> successSupplier = () -> "Success";
        // Chuẩn bị một hàm Supplier mô phỏng lỗi
        Supplier<String> failureSupplier = () -> {
            throw new RuntimeException("Downstream error");
        };

        // Thực hiện 2 cuộc gọi thành công
        for (int i = 0; i < 2; i++) {
            String result = circuitBreaker.decorateSupplier(successSupplier).get();
            assertThat(result).isEqualTo("Success");
        }
        assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.CLOSED);

        // Thực hiện 3 cuộc gọi thất bại tiếp theo để kích hoạt ngắt mạch
        // Tổng cộng: 2 thành công + 3 thất bại = 5 cuộc gọi (đủ slidingWindowSize = 5)
        // Tỉ lệ thất bại: 3/5 = 60% > failureRateThreshold (50%)
        for (int i = 0; i < 3; i++) {
            try {
                circuitBreaker.decorateSupplier(failureSupplier).get();
            } catch (RuntimeException e) {
                // Bỏ qua exception để ghi nhận lỗi vào cửa sổ trượt
            }
        }

        // 2. Kiểm tra xem mạch đã chuyển sang trạng thái OPEN hay chưa
        assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.OPEN);

        // Khi mạch đang OPEN, cuộc gọi tiếp theo phải ném ra CallNotPermittedException ngay lập tức
        assertThatThrownBy(() -> circuitBreaker.decorateSupplier(successSupplier).get())
                .isInstanceOf(CallNotPermittedException.class);

        // 3. Đợi cho đến khi hết thời gian waitDurationInOpenState (200ms) để mạch chuyển sang HALF-OPEN
        Thread.sleep(250);

        // Thực hiện một cuộc gọi sau thời gian chờ để kích hoạt chuyển đổi sang HALF-OPEN
        try {
            circuitBreaker.decorateSupplier(successSupplier).get();
        } catch (Exception e) {
            // Nhận kết quả
        }

        // Kiểm tra mạch đã chuyển sang HALF-OPEN
        assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.HALF_OPEN);

        // Trong trạng thái HALF-OPEN, thực hiện cuộc gọi thành công tiếp theo để khép mạch
        circuitBreaker.decorateSupplier(successSupplier).get();

        // 4. Mạch phải quay trở lại trạng thái CLOSED
        assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.CLOSED);
    }
}
