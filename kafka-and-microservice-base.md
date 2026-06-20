# Kế hoạch triển khai cấu hình Kafka, RabbitMQ và Base APIs cho Microservices

## Mục tiêu
1. Triển khai **Event Bus Abstraction** giúp linh hoạt chuyển đổi giữa Kafka, RabbitMQ hoặc các Event Bus khác trong tương lai qua cấu hình properties.
2. Thiết lập các **Base Business APIs và Utilities** thiết yếu dùng chung cho mọi dự án (AOP Audit Logging, File Storage REST Controller, System Settings REST Controller).
3. Đảm bảo toàn bộ mã nguồn được bình luận đầy đủ (Javadocs, comment chi tiết), đi kèm tài liệu hướng dẫn và tham chiếu `@see` trong Javadoc.

---

## 1. Thiết kế cơ chế Event Bus động (Kafka / RabbitMQ / None)
Chúng ta sẽ trừu tượng hóa Event Bus qua interface `IEventBus` để tách biệt mã nghiệp vụ khỏi thư viện Broker cụ thể.

- **Interface:** `IEventBus.java` định nghĩa các hàm `publish(String topic, Object event)`.
- **Cấu hình:** `common.event-bus.type` có các giá trị `kafka`, `rabbitmq`, `none`.
- **Kafka Implementation:** `KafkaEventBus.java` kích hoạt khi `common.event-bus.type=kafka`.
- **RabbitMQ Implementation:** `RabbitEventBus.java` kích hoạt khi `common.event-bus.type=rabbitmq`.
- **No-Op Implementation:** `NoOpEventBus.java` kích hoạt khi `common.event-bus.type=none` hoặc khi không cấu hình (fallback).

---

## 2. Gợi ý & Triển khai các Base Business APIs và Utilities dùng chung
Chúng tôi đề xuất 3 nhóm Base APIs/Utilities cốt lõi mà 99% dự án microservices cần dùng:

### A. Hệ thống kiểm toán hành động người dùng (AOP Audit Logging)
- **Annotation:** `@AuditLog(action = "ACTION_NAME")` dùng tại các phương thức Controller.
- **Aspect:** `AuditLogAspect.java` tự động bắt cuộc gọi, lấy thông tin người dùng từ `UserContextHolder`, lấy địa chỉ IP, tên method, tham số truyền vào, thời gian thực thi và trạng thái thành công/thất bại, sau đó xuất ra log hoặc đẩy qua `IEventBus` để lưu trữ tập trung.
- **Tài liệu:** `resources/docs/microservice/audit-log-guide.md`.

### B. REST API Lưu trữ File tích hợp sẵn (File Storage API)
- Expose một Controller trừu tượng `BaseStorageController.java` cho phép tải lên (`upload`) và xóa file bằng cách gọi qua `IStorageService`.
- Các Client Application chỉ cần kế thừa controller này để mở ra các cổng API upload/download ảnh đại diện, hóa đơn, tài liệu mà không cần viết lại code.
- **Tài liệu:** `resources/docs/utilities/utilities-guide.md` (phần Storage).

### C. API Cấu hình tham số hệ thống (System Settings API)
- Tạo các Model, Repository, Service và Controller cơ bản cho thực thể `SystemSetting` (lưu trữ key-value các cấu hình chạy động như hạn mức giao dịch, cờ bật/tắt tính năng, thời gian cache, vv.).
- **Tài liệu:** `resources/docs/spring_structure/system-settings-guide.md`.

---

## Các công việc cần làm (Tasks)

- [ ] **Công việc 1: Cấu hình Dependencies**
  - Thêm thư viện `spring-kafka` vào `build.gradle.kts` (RabbitMQ đã có sẵn qua `spring-boot-starter-amqp`).
  - Xác minh: Biên dịch thành công.

- [ ] **Công việc 2: Xây dựng Event Bus Abstraction**
  - Tạo interface `IEventBus.java`.
  - Tạo annotation `@EnableCommonEventBus` để kích hoạt tính năng.
  - Triển khai `KafkaEventBus.java` (sử dụng `KafkaTemplate`).
  - Triển khai `RabbitEventBus.java` (sử dụng `RabbitTemplate`).
  - Triển khai `NoOpEventBus.java`.
  - Tạo cấu hình tự động kích hoạt bean dựa trên `@ConditionalOnProperty`.
  - Xác minh: Logic chuyển đổi hoạt động chuẩn xác theo cấu hình.

- [ ] **Công việc 3: Xây dựng AOP Audit Logging**
  - Tạo annotation `@AuditLog.java`.
  - Tạo class `AuditLogAspect.java` xử lý AOP.
  - Xác minh: Aspect bắt đúng dữ liệu người dùng và log ra thành công.

- [ ] **Công việc 4: Tạo Base REST Controllers (Storage & Settings)**
  - Tạo `BaseStorageController.java` kế thừa các tiện ích file.
  - Tạo DTO, Service và Controller cho `SystemSetting`.
  - Xác minh: API endpoint khởi tạo thành công.

- [ ] **Công việc 5: Viết tài liệu đặc tả & Hướng dẫn sử dụng**
  - Viết tài liệu tại `resources/docs/event_bus/event-bus-guide.md`.
  - Viết tài liệu tại `resources/docs/microservice/audit-log-guide.md`.
  - Cập nhật hướng dẫn `system-settings-guide.md` và `utilities-guide.md`.

- [ ] **Công việc 6: Thêm Javadoc @see References**
  - Bổ sung comment Javadoc cho mọi class/method mới tạo và tham chiếu chéo tới file Markdown tương ứng.

- [ ] **Công việc 7: Chạy kiểm thử tự động**
  - Chạy `.\gradlew.bat test` đảm bảo toàn bộ hệ thống biên dịch sạch sẽ.

## Hoàn thành khi
- Các bean Event Bus và AOP Aspect biên dịch thành công.
- Các Base REST API hoạt động đúng nghiệp vụ.
- Tài liệu Markdown đầy đủ và được tham chiếu chuẩn xác từ Javadoc.
