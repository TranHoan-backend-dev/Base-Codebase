package com.common.config.rabbitmq;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Cấu hình RabbitMQ tự động chỉ khi `common.event-bus.type=rabbitmq`.<br/>
 * Thiết lập các bean MessageConverter, RabbitTemplate hỗ trợ transaction, RabbitAdmin và tự động khởi tạo Topology.<br/>
 *
 * @see <a href="../../../../../resources/docs/event_bus/event-bus-guide.md">Event Bus Specification Guide</a>
 * @author txhoan
 */
@Configuration
@ConditionalOnProperty(name = "common.event-bus.type", havingValue = "rabbitmq")
@EnableConfigurationProperties(RabbitTopologyProperties.class)
class RabbitMQConfiguration {

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf) {
        var tpl = new RabbitTemplate(cf);
        tpl.setChannelTransacted(true);
        tpl.setMessageConverter(converter());
        return tpl;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory cf) {
        return new RabbitAdmin(cf);
    }

    /**
     * Tự động khởi tạo cấu trúc Topology trên RabbitMQ (Exchange, Queues, Bindings) dựa trên cấu hình.<br/>
     * Modified at 20/06/2026
     */
    @Bean
    public Declarables topologyDeclarables(
            @Value("${rabbit-mq-config.exchange_name:event_exchange}") String exchangeName,
            @Value("${rabbit-mq-config.queue:#{null}}") String mainQueueName,
            RabbitTopologyProperties props
    ) {
        
        List<Declarable> declarables = new ArrayList<>();

        // 1. Tạo Topic Exchange chính
        TopicExchange exchange = new TopicExchange(exchangeName, true, false);
        declarables.add(exchange);

        // 2. Tạo Queue chính cho service nếu được cấu hình và bind nhận mọi message (#)
        if (mainQueueName != null && !mainQueueName.trim().isEmpty()) {
            Queue mainQueue = new Queue(mainQueueName.trim(), true);
            declarables.add(mainQueue);
            declarables.add(BindingBuilder.bind(mainQueue).to(exchange).with("#"));
        }

        // 3. Tự động tạo Queue và Binding cho từng Entity được khai báo chạy động
        if (props.getEntities() != null) {
            for (var entity : props.getEntities()) {
                if (entity != null && !entity.trim().isEmpty()) {
                    var queueName = entity.trim() + "-queue";
                    var entityQueue = new Queue(queueName, true);
                    declarables.add(entityQueue);

                    // Liên kết Queue của Entity nhận tất cả các routing key dạng "entity.#"
                    declarables.add(BindingBuilder.bind(entityQueue)
                            .to(exchange)
                            .with(entity.trim() + ".#"));
                }
            }
        }

        return new Declarables(declarables);
    }
}
