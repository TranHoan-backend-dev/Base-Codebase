package com.common.eventbus;

/**
 * Interface trừu tượng hóa Event Bus dùng chung cho các ứng dụng microservices.<br/>
 * Giúp mã nghiệp vụ không phụ thuộc vào một Message Broker cụ thể (Kafka, RabbitMQ, vv.).<br/>
 * Created at 20/06/2026
 *
 * @see <a href="../../../../../resources/docs/event_bus/event-bus-guide.md">Event Bus Specification Guide</a>
 * @author txhoan
 */
public interface IEventBus {

    /**
     * Gửi (publish) một sự kiện đến một chủ đề (topic/queue) nhất định.<br/>
     * Created at 20/06/2026
     *
     * @param topic tên chủ đề hoặc routing key nhận sự kiện
     * @param event dữ liệu sự kiện (sẽ được tự động serialize sang JSON)
     */
    void publish(String topic, Object event);
}
