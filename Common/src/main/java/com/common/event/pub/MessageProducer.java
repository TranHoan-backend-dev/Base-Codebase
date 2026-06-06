package com.common.event.pub;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class MessageProducer {
    @Value("${rabbit-mq-config.exchange_name}")
    private String EXCHANGE_NAME;

    private final RabbitTemplate template;

    /**
     * Cấu hình module bắn sự kiện phu vụ project Nest.js
     * Created at 06/06/2026
     *
     * @param routingKey Key
     * @param message    Thông điệp
     * @author txhoan
     */
    public void sendMessage(String routingKey, Object message) {
        // cấu hình để khớp định dạng nest.js
        Map<String, Object> payload = new HashMap<>();
        payload.put("pattern", routingKey);
        payload.put("data", message);

        // tai su dung context, tranh viec dong-mo context lien tuc moi khi co 1 request
        // duoc gui toi
        template.invoke(t -> {
            template.convertAndSend(EXCHANGE_NAME, routingKey, payload);
            return null;
        });
        log.info("Message sent successfully: {}", message);
    }

    /**
     * Gửi dữ liệu kèm thời gian chờ 10p
     * Created at 06/06/2026
     *
     * @param routingKey Key
     * @param data       Dữ liệu
     * @author txhoan
     */
    public void sendWithDelay(String routingKey, Object data) {
        log.info("Sending delay data to exchange: {}, routingKey: {}", EXCHANGE_NAME, routingKey);
        Map<String, Object> payload = new HashMap<>();
        payload.put("pattern", routingKey);
        payload.put("data", data);

        template.invoke(t -> {
            template.convertAndSend("delayed-exchange", routingKey, payload,
                    message -> {
                        message.getMessageProperties().setHeader("x-delay", 600000);
                        return message;
                    });
            return null;
        });
        log.info("Message sent successfully to RabbitMQ: {}", data);
    }
}
