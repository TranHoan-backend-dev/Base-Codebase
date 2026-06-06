package com.common.config.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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

    @Bean
    public Declarables topologyDeclarables(
            @Value("${rabbit-mq-config.exchange}") String exchange,
            @Value("${rabbit-mq-config.queue:#{null}}") String queue,
            RabbitTopologyProperties props) {
        // shared topology logic...
        return new Declarables();
    }
}
