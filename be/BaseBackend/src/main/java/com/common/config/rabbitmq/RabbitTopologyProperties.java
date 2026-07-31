package com.common.config.rabbitmq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Lớp ánh xạ các thuộc tính cấu hình RabbitMQ Topology chạy động từ file properties/yaml.<br/>
 * Cung cấp thông tin danh sách thực thể (entities) để tự động sinh hàng đợi (queues) tương ứng.<br/>
 *
 * @see <a href="../../../../../resources/docs/event_bus/event-bus-guide.md">Event Bus Specification Guide</a>
 * @author txhoan
 */
@Data
@ConfigurationProperties(prefix = "rabbit-mq-config")
public class RabbitTopologyProperties {
    private List<String> entities;
    private List<String> actions;
}
