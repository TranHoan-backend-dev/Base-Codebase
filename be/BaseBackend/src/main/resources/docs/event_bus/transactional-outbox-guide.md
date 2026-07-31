# Hướng Dẫn Thiết Kế và Sử Dụng Transactional Outbox Pattern

Tài liệu này cung cấp hướng dẫn chi tiết về cách cấu hình, thiết lập và sử dụng **Transactional Outbox Pattern** trong dự án **Base-Codebase** nhằm đảm bảo tính nhất quán dữ liệu và độ tin cậy khi giao tiếp bất đồng bộ qua Message Broker (Event Bus).

---

## 1. Bài Toán Khởi Nguồn: Dual Write Problem

Trong kiến trúc Microservices và Event-Driven Architecture (Kiến trúc hướng sự kiện), một nghiệp vụ thường bao gồm hai hành động:

1. **Cập nhật cơ sở dữ liệu (DB):** Ví dụ: lưu thông tin đơn hàng mới vào bảng `orders`.
2. **Bắn sự kiện (Publish Event) sang Message Broker:** Ví dụ: gửi thông điệp `OrderCreated` sang Kafka/RabbitMQ để dịch vụ vận chuyển và dịch vụ email cùng xử lý.

Hành động này được gọi là **Dual Write (Ghi kép)**. Việc thực hiện đồng thời hai thao tác này ẩn chứa nguy cơ mất nhất quán dữ liệu nghiêm trọng:

* **Trường hợp 1:** DB commit thành công nhưng Broker bị sập hoặc gặp sự cố mạng khiến việc gửi sự kiện thất bại. Kết quả: Đơn hàng đã tạo nhưng khách hàng không nhận được email và kho không chuẩn bị hàng.
* **Trường hợp 2:** Bắn sự kiện sang Broker thành công nhưng sau đó DB commit bị rollback do lỗi ràng buộc dữ liệu hoặc lỗi kết nối. Kết quả: Dịch vụ kho nhận được sự kiện và chuẩn bị hàng cho một đơn hàng không hề tồn tại trong cơ sở dữ liệu.

Do DB và Message Broker là hai hệ thống phân tán khác nhau, chúng ta không thể sử dụng một Transaction cục bộ thông thường để bao bọc cả hai. Giải pháp Transaction phân tán (như 2PC - Two-Phase Commit) thì quá nặng nề, làm giảm đáng kể hiệu năng và khả năng mở rộng của hệ thống.

**Transactional Outbox Pattern** ra đời để giải quyết triệt để bài toán này bằng cách đưa việc gửi sự kiện về cùng một Transaction cục bộ của Cơ sở dữ liệu.

---

## 2. Tư Duy Sử Dụng & Phân Tích Nghiệp Vụ Áp Dụng

Mặc dù Transactional Outbox Pattern giải quyết được độ tin cậy gửi tin nhắn, nó cũng mang lại chi phí vận hành (overhead) như tăng tải ghi DB, yêu cầu một Scheduler quét ngầm và độ trễ nhất định (Eventual Consistency). Do đó, chúng ta cần tư duy phân tích kỹ nghiệp vụ để áp dụng đúng chỗ.

### 2.1. Nghiệp vụ BẮT BUỘC sử dụng Outbox

Nên áp dụng Outbox cho tất cả các nghiệp vụ có tính chất **quyết định luồng tiền**, **luồng hàng hóa** hoặc **trạng thái pháp lý/hợp đồng** của người dùng, nơi mà việc thất lạc sự kiện sẽ dẫn đến hậu quả nghiêm trọng về mặt kinh doanh:

1. **Luồng Thanh Toán & Đơn Hàng (Payment & Orders):** Sau khi trừ tiền khách hàng thành công trong database, bắt buộc phải bắn sự kiện kích hoạt đơn hàng, phát hành hóa đơn và chuẩn bị hàng. Nếu mất sự kiện này, khách hàng bị trừ tiền mà không nhận được hàng.
2. **Đăng Ký & Xác Thực Tài Khoản (User Registration):** Lưu tài khoản mới thành công đồng thời lưu outbox sự kiện gửi link kích hoạt qua email/SMS. Nếu mất sự kiện này, tài khoản bị treo vĩnh viễn vì khách hàng không thể xác thực.
3. **Thay Đổi Trạng Thái Hệ Thống Quan Trọng (System State Changes):** Thay đổi cấu hình bảo mật, kích hoạt/khóa tài khoản người dùng, thay đổi hạn mức tín dụng.

### 2.2. Nghiệp vụ KHÔNG CẦN THIẾT hoặc KHÔNG NÊN sử dụng Outbox

Tránh lạm dụng Outbox cho các dữ liệu có tần suất thay đổi cực cao nhưng giá trị nghiệp vụ của từng sự kiện đơn lẻ thấp hoặc được cập nhật liên tục:

1. **Chỉ Số Giám Sát & Log Nghiệp Vụ (Telemetry & Tracking):** Dữ liệu clickstream của người dùng, log hành vi xem sản phẩm, các chỉ số đo đạc hiệu năng. Nếu mất một vài sự kiện này, hệ thống phân tích không bị ảnh hưởng đáng kể.
2. **Thông Tin Cập Nhật Liên Tục (High-Frequency Updates):** Tọa độ GPS của shipper cập nhật mỗi 5 giây, nhiệt độ cảm biến IoT gửi liên tục. Các sự kiện này mang tính thời điểm, sự kiện sau sẽ ghi đè giá trị sự kiện trước. Nếu sử dụng Outbox, bảng outbox sẽ nhanh chóng phình to làm sập cơ sở dữ liệu.
3. **Tác Vụ Đồng Bộ Hoàn Toàn (Synchronous Flows):** Các API truy vấn thông tin thuần túy (Read-only APIs) không phát sinh thay đổi trạng thái database.

---

## 3. Cơ Chế Hoạt Động Của Outbox Pattern

Mô hình hoạt động của Transactional Outbox trong dự án **Base-Codebase** gồm 3 bước chính diễn ra bất đồng bộ:

```
[Client] ---> (1. Call API) ---> [Service] (Nghiệp vụ + Outbox)
                                    |
                                    +---> [Transaction Nghiệp Vụ] (Spring @Transactional)
                                                |
                                                +---> Ghi dữ liệu nghiệp vụ (bảng orders)
                                                +---> Ghi OutboxEvent PENDING (bảng outbox_events)
                                                |
                                          (Commit DB)
                                                |
                                                v
[OutboxScheduler] <--- (2. Định kỳ quét PENDING mỗi 5s) --- [Database]
       |
       +---> (3. Publish) ---> [IEventBus] ---> [Message Broker] (Kafka/RabbitMQ)
       |                                                |
 (Gửi thành công)                               (Gửi thất bại)
       |                                                |
       v                                                v
Cập nhật PROCESSED                             Tăng retry_count, lưu error
```

1. **Bước 1 (Ghi đồng thời):** Khi có một yêu cầu nghiệp vụ, Service thực hiện cập nhật bảng nghiệp vụ và chèn một bản ghi sự kiện vào bảng `outbox_events` ở trạng thái `PENDING`. Cả hai hành động này nằm trong cùng một Spring `@Transactional`.
2. **Bước 2 (Scheduler quét ngầm):** Bộ lập lịch `OutboxScheduler` chạy ngầm định kỳ quét bảng `outbox_events` để lấy các sự kiện có trạng thái `PENDING`.
3. **Bước 3 (Gửi tin và Cập nhật):**
    * Scheduler gọi `IEventBus.publish(...)` để đẩy sự kiện sang Message Broker.
    * Nếu gửi thành công, trạng thái sự kiện được chuyển sang `PROCESSED`.
    * Nếu gặp lỗi (mạng lỗi, Broker sập), hệ thống tăng số lần thử lại (`retryCount`) và ghi nhận nguyên nhân lỗi vào `errorMessage`. Khi vượt quá số lần cấu hình (mặc định 5 lần), trạng thái sẽ chuyển sang `FAILED` để kỹ sư hệ thống kiểm tra thủ công.

---

## 4. Thiết Lập Cơ Sở Dữ Liệu và Thực Thể

Bảng `outbox_events` được định nghĩa thông qua thực thể JPA `OutboxEvent.java` kế thừa từ `BaseModel` để tự động thừa hưởng các trường kiểm toán (`createdAt`, `modifiedAt`, `createdBy`, `modifiedBy`).

### 4.1. Thực thể `OutboxEvent.java`

```java
package com.common.model.sql;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "outbox_events")
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OutboxEvent extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "aggregate_type", nullable = false)
    String aggregateType;

    @Column(name = "aggregate_id", nullable = false)
    String aggregateId;

    @Column(name = "event_type", nullable = false)
    String eventType;

    @Column(name = "payload", nullable = false, columnDefinition = "TEXT")
    String payload;

    @Column(name = "status", nullable = false)
    String status; // PENDING, PROCESSED, FAILED

    @Column(name = "retry_count", nullable = false)
    Integer retryCount;

    @Column(name = "error_message", length = 1000)
    String errorMessage;
}
```

---

## 5. Hướng Dẫn Lập Trình Cho Nhà Phát Triển

Để ghi nhận sự kiện, các dịch vụ nghiệp vụ cần tiêm `IOutboxService` và gọi hàm `saveEvent` trong khối mã được đánh dấu bằng `@Transactional`.

### 5.1. Triển khai trong Service Nghiệp Vụ

Dưới đây là ví dụ cập nhật giá trị cấu hình hệ thống đồng thời kích hoạt một sự kiện Outbox để thông báo cho các microservice khác cập nhật cache:

```java
package com.common.service.impl;

import com.common.model.sql.SystemSetting;
import com.common.repository.sql.SystemSettingRepository;
import com.common.service.contract.IOutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemSettingService {

    private final SystemSettingRepository repository;
    private final IOutboxService outboxService;

    @Transactional
    public void updateSettingAndNotify(String settingKey, String settingValue) {
        log.info("Cập nhật tham số hệ thống: {} = {}", settingKey, settingValue);
        
        // 1. Cập nhật dữ liệu nghiệp vụ chính
        SystemSetting setting = repository.findBySettingKey(settingKey)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy cấu hình"));
        setting.setSettingValue(settingValue);
        repository.save(setting);

        // Chuẩn bị DTO sự kiện để gửi đi
        SettingUpdatedEvent eventPayload = new SettingUpdatedEvent(settingKey, settingValue);

        // 2. Lưu sự kiện vào bảng outbox
        // Hàm saveEvent kế thừa cùng transaction nghiệp vụ, nếu lưu DB lỗi ở bước 1, 
        // cả thực thể nghiệp vụ và OutboxEvent đều được rollback đồng thời.
        outboxService.saveEvent(
                "SystemSetting", 
                setting.getId().toString(), 
                "SystemSettingUpdated", 
                eventPayload
        );
    }
}
```

---

## 6. Xử Lý Trùng Lặp ở Phía Nhận (Idempotent Consumer)

Do cơ chế quét lại các sự kiện lỗi của bộ lập lịch (`OutboxScheduler`), Transactional Outbox Pattern chỉ có thể đảm bảo phân phối tin nhắn theo cơ chế **At-least-once delivery (Phát ít nhất một lần)**. Có nghĩa là trong các trường hợp lỗi mạng chập chờn, Broker đã nhận được tin nhắn và gửi cho Consumer thành công nhưng Client chưa kịp cập nhật trạng thái `PROCESSED` trong database, tin nhắn đó sẽ được gửi lại ở chu kỳ tiếp theo.

Vì vậy, **Phía nhận sự kiện (Consumer) BẮT BUỘC phải xử lý trùng lặp (Idempotency)**.

### Các chiến lược đảm bảo tính Idempotency

1. **Sử dụng Bảng Inbox (Inbox Pattern):** Phía Consumer duy trì một bảng `processed_messages` lưu trữ `message_id` (hoặc `outbox_event_id` nhận được). Trước khi xử lý bất kỳ sự kiện nào, Consumer kiểm tra xem ID này đã tồn tại trong DB chưa. Nếu đã có thì bỏ qua sự kiện.
2. **Cập nhật idempotent bằng trạng thái (State Validation):** Nếu sự kiện là `OrderPaid`, Consumer chỉ thực hiện xử lý nếu trạng thái hiện tại của đơn hàng trong cơ sở dữ liệu của nó đang là `PENDING_PAYMENT`. Nếu đơn hàng đã là `PAID` hoặc `SHIPPED`, Consumer bỏ qua sự kiện và chỉ xác nhận đã xử lý xong.
3. **Sử dụng Ràng buộc duy nhất (Unique Constraint):** Tạo khóa duy nhất kết hợp giữa định danh nghiệp vụ và loại sự kiện trong database của phía Consumer.

---

## 7. Các điểm lưu ý quan trọng khi vận hành

1. **Dọn dẹp Bảng Outbox (Outbox Cleanup Job):** Bảng `outbox_events` sẽ phình to rất nhanh theo thời gian. Bạn cần xây dựng một tác vụ ngầm chạy định kỳ (ví dụ: hàng ngày lúc 2h sáng) để xóa các sự kiện đã xử lý thành công (`status = 'PROCESSED'`) cách đây hơn 3 ngày hoặc 7 ngày:

    ```sql
    DELETE FROM outbox_events WHERE status = 'PROCESSED' AND created_at < NOW() - INTERVAL '7 days';
    ```

2. **Giám Sát Sự Kiện Lỗi (`FAILED` Events):** Các sự kiện có trạng thái `FAILED` sau khi hết số lần retry cần được cấu hình gửi cảnh báo về hệ thống Slack/Telegram của đội ngũ phát triển. Điều này thường báo hiệu lỗi cấu trúc payload (Schema mismatch) hoặc Message Broker đang bị cấu hình sai hạn mức payload.
3. **Kiểm soát Transaction Isolation Level:** Khi chạy Scheduler quét đồng thời với các tác vụ ghi nghiệp vụ, hãy đảm bảo chỉ số Isolation Level không gây ra hiện tượng khóa chết (deadlock) hoặc đọc dữ liệu rác (dirty read).
