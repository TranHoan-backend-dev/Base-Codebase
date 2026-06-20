package com.common.scheduler;

import com.common.eventbus.IEventBus;
import com.common.model.sql.OutboxEvent;
import com.common.repository.sql.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Bộ lập lịch quét định kỳ các sự kiện outbox để gửi đi thông qua Event Bus.<br/>
 * Đảm bảo tính nhất quán cuối cùng (Eventual Consistency).<br/>
 * Created at 20/06/2026
 *
 * @author txhoan
 */
@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class OutboxScheduler {

    private final OutboxEventRepository outboxEventRepository;
    private final IEventBus eventBus;

    private static final int MAX_RETRY_COUNT = 5;

    /**
     * Quét các sự kiện outbox ở trạng thái PENDING định kỳ mỗi 5 giây.<br/>
     */
    @Scheduled(fixedDelay = 5000)
    public void processOutboxEvents() {
        log.debug("Đang quét các sự kiện Outbox PENDING...");
        List<OutboxEvent> pendingEvents = outboxEventRepository.findByStatusAndRetryCountLessThanOrderByIdAsc("PENDING", MAX_RETRY_COUNT);

        if (pendingEvents.isEmpty()) {
            return;
        }

        log.info("Tìm thấy {} sự kiện Outbox PENDING cần xử lý.", pendingEvents.size());
        for (OutboxEvent event : pendingEvents) {
            publishEvent(event);
        }
    }

    private void publishEvent(OutboxEvent event) {
        try {
            log.info("Đang gửi sự kiện Outbox ID: {}, Type: {}", event.getId(), event.getEventType());
            
            // Bắn sự kiện lên Message Broker qua Event Bus
            eventBus.publish(event.getEventType(), event.getPayload());

            // Cập nhật trạng thái thành công
            event.setStatus("PROCESSED");
            event.setErrorMessage(null);
            outboxEventRepository.save(event);
            log.info("Gửi sự kiện Outbox ID: {} thành công.", event.getId());
        } catch (Exception e) {
            log.error("Gửi sự kiện Outbox ID: {} thất bại", event.getId(), e);
            
            int retries = event.getRetryCount() + 1;
            event.setRetryCount(retries);
            event.setErrorMessage(e.getMessage());
            
            if (retries >= MAX_RETRY_COUNT) {
                event.setStatus("FAILED");
                log.error("Sự kiện Outbox ID: {} đã đạt số lần thử tối đa ({}) -> Chuyển trạng thái FAILED", event.getId(), MAX_RETRY_COUNT);
            }
            
            outboxEventRepository.save(event);
        }
    }
}
