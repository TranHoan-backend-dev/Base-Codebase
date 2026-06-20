package com.common.service.impl;

import com.common.model.sql.OutboxEvent;
import com.common.repository.sql.OutboxEventRepository;
import com.common.service.contract.IOutboxService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Lớp kiểm thử tích hợp (Integration Test) giả lập cho cơ chế Transactional Outbox.<br/>
 * Kiểm chứng tính nguyên tử (Atomicity): nếu nghiệp vụ fail thì cả hai (Nghiệp vụ + Outbox) đều rollback.<br/>
 * Created at 20/06/2026
 *
 * @author txhoan
 */
class TransactionalOutboxTest {

    @Mock
    private OutboxEventRepository outboxEventRepository;

    @Mock
    private PlatformTransactionManager transactionManager;

    @Mock
    private TransactionStatus transactionStatus;

    private IOutboxService outboxService;
    private TransactionTemplate transactionTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        outboxService = new OutboxServiceImpl(outboxEventRepository, new ObjectMapper());
        
        // Mock hành vi của TransactionManager
        when(transactionManager.getTransaction(any())).thenReturn(transactionStatus);
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Test
    void testOutboxEventSavedWithinSuccessfulTransaction() {
        // Chạy nghiệp vụ và outbox trong một transaction thành công
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                // Giả lập lưu thực thể nghiệp vụ thành công
                // ... (ví dụ: orderService.createOrder())
                
                // Lưu outbox event
                outboxService.saveEvent("Order", "123", "OrderCreated", "{\"amount\": 100}");
            }
        });

        // Xác nhận OutboxEventRepository được gọi lưu sự kiện
        verify(outboxEventRepository, times(1)).save(any(OutboxEvent.class));
        
        // Xác nhận transaction được commit chứ không phải rollback
        verify(transactionManager, times(1)).commit(transactionStatus);
        verify(transactionManager, never()).rollback(transactionStatus);
    }

    @Test
    void testOutboxEventRolledBackWhenTransactionFails() {
        // Chạy nghiệp vụ và outbox trong một transaction bị lỗi
        assertThatThrownBy(() -> {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    // 1. Lưu outbox event thành công
                    outboxService.saveEvent("Order", "123", "OrderCreated", "{\"amount\": 100}");

                    // 2. Giả lập nghiệp vụ lỗi ném ngoại lệ
                    throw new RuntimeException("Business transaction failed!");
                }
            });
        }).isInstanceOf(RuntimeException.class)
          .hasMessageContaining("Business transaction failed!");

        // Xác nhận outboxEventRepository.save vẫn được gọi trong luồng
        verify(outboxEventRepository, times(1)).save(any(OutboxEvent.class));

        // QUAN TRỌNG: Xác nhận transactionManager.rollback được gọi thay vì commit
        verify(transactionManager, times(1)).rollback(transactionStatus);
        verify(transactionManager, never()).commit(transactionStatus);
    }
}
