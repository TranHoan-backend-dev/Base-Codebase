# Hướng Dẫn Sử Dụng Hệ Thống Event Bus Động (Kafka & RabbitMQ)

Tài liệu này hướng dẫn chi tiết cách cấu hình và sử dụng hệ thống Event Bus động (`IEventBus`) hỗ trợ đa Broker (Kafka, RabbitMQ, None) trong thư viện `Common`.

---

## 1. Cơ Chế Hoạt Động

Để cho phép thay đổi Message Broker một cách linh hoạt mà không sửa đổi mã nguồn (Business Code), hệ thống Event Bus cung cấp:

1. **Interface dùng chung:** [IEventBus](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/eventbus/IEventBus.java) định nghĩa phương thức `publish`.
2. **Cấu hình nạp Bean động:** [EventBusConfiguration](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/eventbus/EventBusConfiguration.java) sử dụng `@ConditionalOnProperty` để quét và nạp Bean thực thi tương ứng dựa vào key `common.event-bus.type`.

---

## 2. Cách Cấu Hình Lựa Chọn Broker

Nhà phát triển cấu hình loại Broker mong muốn tại file `application.yaml` hoặc `application.properties` của ứng dụng Client:

```yaml
common:
  event-bus:
    type: kafka # Hỗ trợ: kafka | rabbitmq | none (mặc định là none)
```

### A. Nếu chọn `kafka`

Cần bổ sung cấu hình kết nối Kafka tiêu chuẩn của Spring Boot:

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: my-group
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
```

### B. Nếu chọn `rabbitmq`

1. **Cấu hình kết nối RabbitMQ tiêu chuẩn:**

```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```

1. **Cấu hình khởi tạo cấu trúc hàng đợi động (RabbitMQ Topology):**

Thư viện hỗ trợ tự động định nghĩa Topic Exchange, Queue chính và các hàng đợi theo Entity nghiệp vụ thông qua cấu hình `rabbit-mq-config`:

```yaml
rabbit-mq-config:
  exchange_name: event_exchange # Tên Topic Exchange chính (Mặc định: event_exchange nếu không cấu hình)
  queue: main-service-queue     # Tên Queue chính của Microservice nhận tất cả tin nhắn (Mặc định: không tạo nếu để trống)
  entities:                     # Động sinh ra các Queue dạng "<entity>-queue" và tự động binding với routing key dạng "<entity>.#"
    - user
    - order
    - payment
```

* **Topic Exchange**: Tự động khai báo Exchange kiểu Topic bền vững (durable).
* **Queue chính của Service**: Tự động tạo và liên kết với Exchange thông qua routing key `#` (nhận mọi tin nhắn).
* **Entity Queues**: Tự động tạo Queue riêng biệt cho từng Entity và liên kết với Exchange qua routing key `<entity>.#` để phục vụ lọc tin nhắn theo đối tượng nghiệp vụ cụ thể.

---

## 3. Cách Sử Dụng Trong Lớp Nghiệp Vụ

### Bước 1: Kích hoạt tại Client Application

```java
package com.client;

import com.common.eventbus.EnableCommonEventBus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCommonEventBus // <-- Kích hoạt tự động cấu hình Event Bus
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
```

### Bước 2: Inject và gửi tin nhắn (Publish Event)

Mã nguồn nghiệp vụ hoàn toàn độc lập với công nghệ hàng đợi:

```java
package com.client.service;

import com.common.eventbus.IEventBus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final IEventBus eventBus; // Spring sẽ tự động inject Bean phù hợp (KafkaEventBus hoặc RabbitEventBus)

    public void createOrder(Order order) {
        // Xử lý lưu đơn hàng...
        
        // Phát sự kiện
        eventBus.publish("order-created-topic", order); 
    }
}
```
