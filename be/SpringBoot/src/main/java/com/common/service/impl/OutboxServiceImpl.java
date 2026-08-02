package com.common.service.impl;

import com.common.model.sql.OutboxEvent;
import com.common.repository.sql.OutboxEventRepository;
import com.common.service.contract.IOutboxService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Lớp triển khai dịch vụ lưu trữ sự kiện Transactional Outbox.<br/>
 * Chạy đồng bộ trong cùng transaction nghiệp vụ của phương thức gọi.<br/>
 * Created at 20/06/2026
 *
 * @author txhoan
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxServiceImpl implements IOutboxService {

    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void saveEvent(String aggregateType, String aggregateId, String eventType, Object payload) {
        log.info("Ghi nhận sự kiện Outbox: type={}, id={}, event={}", aggregateType, aggregateId, eventType);
        try {
            String jsonPayload = objectMapper.writeValueAsString(payload);
            OutboxEvent event = OutboxEvent.builder()
                    .aggregateType(aggregateType)
                    .aggregateId(aggregateId)
                    .eventType(eventType)
                    .payload(jsonPayload)
                    .status("PENDING")
                    .retryCount(0)
                    .build();

            outboxEventRepository.save(event);
            log.debug("Lưu sự kiện Outbox thành công. Event ID: {}", event.getId());
        } catch (JsonProcessingException e) {
            log.error("Lỗi serialize payload sự kiện outbox sang JSON", e);
            throw new RuntimeException("Lỗi serialize payload sự kiện outbox sang JSON", e);
        }
    }
}
