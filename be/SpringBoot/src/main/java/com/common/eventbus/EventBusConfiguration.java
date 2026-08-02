package com.common.eventbus;

import com.common.eventbus.impl.KafkaEventBus;
import com.common.eventbus.impl.NoOpEventBus;
import com.common.eventbus.impl.RabbitEventBus;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Lớp cấu hình nạp động Bean Event Bus tùy biến dựa trên thuộc tính cấu hình.<br/>
 * Hỗ trợ chuyển cấu hình linh động bằng cách chỉ định key `common.event-bus.type=kafka|rabbitmq|none` trong file YAML.<br/>
 * Created at 20/06/2026
 *
 * @see <a href="../../../../../resources/docs/event_bus/event-bus-guide.md">Event Bus Specification Guide</a>
 * @author txhoan
 */
@Configuration
public class EventBusConfiguration {

    /**
     * Khởi tạo Kafka Event Bus khi cấu hình type là kafka.<br/>
     * Created at 20/06/2026
     *
     * @param kafkaTemplate đối tượng KafkaTemplate được Spring tự động nạp
     * @return bean KafkaEventBus
     */
    @Bean
    @ConditionalOnProperty(name = "common.event-bus.type", havingValue = "kafka")
    public IEventBus kafkaEventBus(KafkaTemplate<String, Object> kafkaTemplate) {
        return new KafkaEventBus(kafkaTemplate);
    }

    /**
     * Khởi tạo RabbitMQ Event Bus khi cấu hình type là rabbitmq.<br/>
     * Created at 20/06/2026
     *
     * @param amqpTemplate đối tượng AmqpTemplate được Spring tự động nạp
     * @return bean RabbitEventBus
     */
    @Bean
    @ConditionalOnProperty(name = "common.event-bus.type", havingValue = "rabbitmq")
    public IEventBus rabbitEventBus(AmqpTemplate amqpTemplate) {
        return new RabbitEventBus(amqpTemplate);
    }

    /**
     * Khởi tạo No-Op Event Bus làm fallback mặc định khi type là none hoặc không cấu hình.<br/>
     * Created at 20/06/2026
     *
     * @return bean NoOpEventBus
     */
    @Bean
    @ConditionalOnProperty(name = "common.event-bus.type", havingValue = "none", matchIfMissing = true)
    public IEventBus noOpEventBus() {
        return new NoOpEventBus();
    }
}
