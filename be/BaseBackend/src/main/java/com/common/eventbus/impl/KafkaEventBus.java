package com.common.eventbus.impl;

import com.common.eventbus.IEventBus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Triển khai Event Bus sử dụng Apache Kafka làm Message Broker.<br/>
 * Bean này được tự động cấu hình khi `common.event-bus.type=kafka`.<br/>
 * Created at 20/06/2026
 *
 * @see <a href="../../../../../../resources/docs/event_bus/event-bus-guide.md">Event Bus Specification Guide</a>
 * @author txhoan
 */
@Slf4j
@RequiredArgsConstructor
public class KafkaEventBus implements IEventBus {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Gửi sự kiện đến Kafka topic bằng KafkaTemplate.<br/>
     * Created at 20/06/2026
     */
    @Override
    public void publish(String topic, Object event) {
        log.info("Publishing event to Kafka topic [{}]: {}", topic, event);
        try {
            kafkaTemplate.send(topic, event);
        } catch (Exception e) {
            log.error("Failed to publish event to Kafka topic [{}]: {}", topic, e.getMessage(), e);
            throw new RuntimeException("Kafka publish failed", e);
        }
    }
}
