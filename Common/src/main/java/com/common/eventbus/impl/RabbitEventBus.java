package com.common.eventbus.impl;

import com.common.eventbus.IEventBus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;

/**
 * Triển khai Event Bus sử dụng RabbitMQ làm Message Broker.<br/>
 * Bean này được tự động cấu hình khi `common.event-bus.type=rabbitmq`.<br/>
 * Created at 20/06/2026
 *
 * @see <a href="../../../../../../resources/docs/event_bus/event-bus-guide.md">Event Bus Specification Guide</a>
 * @author txhoan
 */
@Slf4j
@RequiredArgsConstructor
public class RabbitEventBus implements IEventBus {

    private final AmqpTemplate amqpTemplate;

    /**
     * Gửi sự kiện đến RabbitMQ exchange/routing key bằng AmqpTemplate.<br/>
     * Created at 20/06/2026
     */
    @Override
    public void publish(String topic, Object event) {
        log.info("Publishing event to RabbitMQ routing key/queue [{}]: {}", topic, event);
        try {
            amqpTemplate.convertAndSend(topic, event);
        } catch (Exception e) {
            log.error("Failed to publish event to RabbitMQ [{}]: {}", topic, e.getMessage(), e);
            throw new RuntimeException("RabbitMQ publish failed", e);
        }
    }
}
