# Hướng Dẫn Sử Dụng Thư Viện Tiện Ích (Utilities)

Tài liệu này mô tả các tiện ích dùng chung (JSON, Lưu trữ file, Xử lý lỗi tập trung) có sẵn trong thư viện `Common`.

---

## 1. Tiện Ích Làm Việc Với JSON `JsonUtils`

Lớp [JsonUtils](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/utilities/JsonUtils.java) bọc quanh `ObjectMapper` của Jackson giúp serialization và deserialization một cách đồng nhất, an toàn.

### Các Tính Năng Định Cấu Hình Sẵn

- **JavaTimeModule**: Tự động hỗ trợ parse/format các trường ngày tháng thuộc chuẩn Java 8 (`LocalDateTime`, `LocalDate`, `OffsetDateTime`, vv.).
- **Fail on Unknown Properties = false**: Không ném ngoại lệ khi giải nén JSON chứa các thuộc tính không có trong Class Java đích (tránh lỗi khi API Client gửi thêm dữ liệu dư thừa).

### Cách Sử Dụng

```java
// 1. Chuyển đối tượng Java sang JSON String
String json = JsonUtils.toJson(userObject);

// 2. Chuyển JSON String ngược lại thành đối tượng Java
UserDto userDto = JsonUtils.fromJson(json, UserDto.class);
```

---

## 2. Tiện Ích Lưu Trữ File `IStorageService`

Để độc lập dịch vụ lưu trữ file (không bị bó cứng vào đĩa cứng cục bộ Local Storage khi scale ứng dụng lên Cloud), thư viện cung cấp Interface [StorageService](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/utilities/StorageService.java) chứa hai thao tác:

```java
public interface StorageService {
    String uploadFile(MultipartFile file);
    void deleteFile(String fileIdentifier);
}
```

### Triển khai mặc định: `LocalStorageServiceImpl`

Thư viện tích hợp sẵn lớp [LocalStorageServiceImpl](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/utilities/LocalStorageServiceImpl.java) để lưu trữ cục bộ.

- **Tên File Duy Nhất**: Tên tệp tải lên được ghép tự động với timestamp hiện tại (ví dụ: `1718809282_avatar.png`) giúp loại bỏ hoàn toàn nguy cơ trùng đè file.
- **Thư Mục Cấu Hình**: Đường dẫn thư mục lưu trữ được khai báo linh hoạt trong tệp `application.yaml` qua key `storage.local.upload-dir` (mặc định là `uploads/images` dưới thư mục chạy dự án).

---

## 3. Bắt Lỗi Xung Đột Concurrency `GlobalExceptionHandler`

Khi các thực thể kế thừa `BaseModel` hoặc `BaseSoftDeleteModel` xảy ra xung đột cập nhật dữ liệu đồng thời (do thuộc tính `@Version` kiểm soát), Hibernate sẽ ném ra ngoại lệ `ObjectOptimisticLockingFailureException`.

### Xử Lý Tập Trung

[GlobalExceptionHandler](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/exception/GlobalExceptionHandler.java) đã được đăng ký bắt lỗi này và tự động trả về phản hồi chuẩn:

- **HTTP Status Code**: `409 Conflict`
- **Thông Điệp**: `Dữ liệu đã bị sửa đổi bởi người dùng khác. Vui lòng tải lại trang và thực hiện lại.`

Nhờ đó, client-side (Frontend) nhận diện được lỗi xung đột dữ liệu nhanh chóng để đưa ra cảnh báo reload trang phù hợp cho người dùng cuối.
