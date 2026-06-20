# Tài Liệu Đặc Tả Hệ Thống Xử Lý Ngoại Lệ Tập Trung (Global Exception Handling)

Tài liệu này đặc tả chi tiết kiến trúc, nguyên lý hoạt động, cấu trúc mã phản hồi lỗi và hướng dẫn sử dụng hệ thống xử lý ngoại lệ tập trung (Global Exception Handling) trong thư viện `Common`.

---

## 1. Giới Thiệu Chung (Overview)

Trong kiến trúc Microservices, việc đảm bảo tính đồng nhất của dữ liệu phản hồi (API Response) khi có lỗi xảy ra là cực kỳ quan trọng. Nếu không quản lý tập trung, khi ứng dụng gặp sự cố (ví dụ: NullPointerException, Database Connection Timeout, Lỗi validation đầu vào), Spring Boot mặc định sẽ trả về các cấu trúc JSON lỗi khác nhau hoặc thậm chí là trang HTML thô (White Label Error Page). Việc này gây ra các nhược điểm:

1. **Gây khó khăn cho Client (Frontend/Mobile/Other Services)**: Client phải viết rất nhiều logic xử lý lỗi khác nhau cho từng endpoint.
2. **Rò rỉ thông tin hạ tầng**: Chi tiết log lỗi (stack trace) có thể bị lộ trực tiếp ra ngoài JSON phản hồi, tạo cơ hội cho kẻ tấn công dò tìm lỗ hổng hệ thống.

### Giải pháp xử lý lỗi tập trung

Thư viện `Common` xây dựng bộ xử lý ngoại lệ tập trung sử dụng `@RestControllerAdvice` trong lớp [GlobalExceptionHandler](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/exception/GlobalExceptionHandler.java). Bộ xử lý này tự động đánh chặn (intercept) tất cả các ngoại lệ ném ra từ tầng Controller, Service hoặc Filter, sau đó bao bọc chúng vào một cấu trúc thống nhất là `WrapperApiResponse`.

---

## 2. Sơ Đồ Luồng Xử Lý Ngoại Lệ (Exception Handling Flow)

Dưới đây là sơ đồ chuỗi xử lý khi một lỗi xảy ra trong quá trình thực thi Request:

```text
+------------+             +-----------------+             +---------------------+
|   Client   |             |   Controller    |             |   Business Service  |
+-----+------+             +--------+--------+             +----------+----------+
      |                             |                                 |
      |---- (1) Gửi Request ------->|                                 |
      |                             |---- (2) Gọi logic nghiệp vụ --->|
      |                             |                                 | (Gặp lỗi dữ liệu)
      |                             |                                 | Xảy ra Exception!
      |                             |<--- (3) Ném Exception (throw) --|
      |<--- (6) Trả về JSON lỗi ----|                                 |
      |     (WrapperApiResponse)    |                                 |
      |                             v                                 |
      |                    +--------+--------+                        |
      |                    |  Spring MVC     |                        |
      |                    +--------+--------+                        |
      |                             |                                 |
      |                             v (Đánh chặn ngoại lệ)             |
      |                    +--------+--------+                        |
      |                    |  GlobalException|                        |
      |                    |  Handler        |                        |
      |                    +--------+--------+                        |
      |                             |                                 |
      |                             v (Đóng gói & Chuyển đổi mã lỗi)  |
      |                    +--------+--------+                        |
      |                    | ResponseEntity  |                        |
      |                    | (HTTP 4xx / 5xx)|                        |
      |                    +--------+--------+                        |
```

---

## 3. Khuôn Mẫu Phản Hồi Lỗi Chuẩn (Standardized Error Response Pattern)

Tất cả các API khi xảy ra lỗi đều phản hồi dữ liệu theo cấu trúc của Record [WrapperApiResponse](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/dto/response/WrapperApiResponse.java):

```json
{
  "status": 400,
  "message": "Thông điệp mô tả lỗi thân thiện với người dùng",
  "data": {
    "field_error_1": "Mô tả lỗi chi tiết cho trường 1",
    "field_error_2": "Mô tả lỗi chi tiết cho trường 2"
  },
  "timestamp": "2026-06-20T19:40:00+07:00"
}
```

### Giải thích các thuộc tính

* **`status`** (integer): Mã trạng thái HTTP thực tế (ví dụ: `400`, `401`, `403`, `404`, `409`, `500`). Trùng khớp với mã HTTP Status của Header phản hồi.
* **`message`** (string): Thông điệp mô tả lỗi khái quát bằng ngôn ngữ tự nhiên (đã được đa ngôn ngữ hóa dựa trên Header `Accept-Language` gửi lên).
* **`data`** (object/null): Chứa dữ liệu lỗi chi tiết (ví dụ: Map các lỗi validation đầu vào của từng thuộc tính). Nếu không có chi tiết, giá trị sẽ là `null`.
* **`timestamp`** (string/ISO-8601): Thời điểm chính xác xảy ra lỗi tại máy chủ (theo múi giờ cấu hình hệ thống).

---

## 4. Bảng Ánh Xạ Mã Lỗi HTTP (HTTP Status Code Mapping Table)

Lớp `GlobalExceptionHandler` ánh xạ các Exception cụ thể thành các mã phản hồi HTTP tiêu chuẩn như sau:

| Loại Exception | Mã HTTP | Ý Nghĩa Phản Hồi | Giải Pháp Xử Lý |
| :--- | :---: | :--- | :--- |
| `MethodArgumentNotValidException` | **400** | Yêu cầu không hợp lệ (Validation Failed) | Lấy chi tiết các trường lỗi từ `BindingResult`, dịch thông báo qua i18n và lưu vào `data`. |
| `HttpMessageNotReadableException` | **400** | Sai định dạng JSON đầu vào | Thường do người dùng gửi sai kiểu định dạng Date, Number thô. |
| `DateTimeParseException` | **400** | Sai định dạng ngày tháng | Chuẩn hóa định dạng yêu cầu `yyyy-MM-dd`. |
| `IllegalArgumentException` | **400** | Đối số truyền vào phương thức không hợp lệ | Phản hồi lỗi cú pháp logic client gửi lên. |
| `BadCredentialsException` | **401** | Sai thông tin xác thực | Trả về thông báo "Invalid email or password" chuẩn bảo mật. |
| `DisabledException` | **401** | Tài khoản bị vô hiệu hóa | Tài khoản người dùng bị khóa hoặc chưa kích hoạt. |
| `AccessDeniedException` | **403** | Không có quyền hạn truy cập | Người dùng đã xác thực nhưng không có Role/Authority tương ứng. |
| `ForbiddenException` | **403** | Truy cập bị cấm | Lỗi nghiệp vụ cấm truy cập tài nguyên. |
| `NotFoundException` | **404** | Không tìm thấy tài nguyên nghiệp vụ | Lỗi không tìm thấy đối tượng cụ thể (đã được tối ưu hóa). |
| `EntityNotFoundException` | **404** | Thực thể Database JPA không tồn tại | Bắt lỗi trực tiếp từ Hibernate/JPA khi truy vấn proxy rỗng. |
| `ObjectOptimisticLockingFailureException` | **409** | Xung đột phiên bản dữ liệu (Optimistic Lock) | Dữ liệu đã bị sửa đổi bởi người dùng khác trong lúc thao tác. |
| `FeignException` | **500** | Lỗi gọi liên dịch vụ (Feign Client) | Gặp sự cố kết nối hoặc lỗi từ microservice đích. |
| `Exception.class` (Catch-all) | **500** | Lỗi hệ thống không lường trước | Bắt toàn bộ các lỗi runtime chưa được khai báo để ẩn stack trace thô và ghi log tập trung. |

---

## 5. Cơ Chế Xử Lý Lỗi Validation & Đa Ngôn Ngữ (i18n Validation)

Khi một DTO gửi lên bị vi phạm ràng buộc dữ liệu (ví dụ: trường được đánh dấu `@NotBlank` hoặc `@Size` bị lỗi), Spring Boot ném ra `MethodArgumentNotValidException`.

Lớp xử lý tập trung sẽ bóc tách lỗi và đa ngôn ngữ hóa theo cơ chế sau:

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<WrapperApiResponse> handleValidationExceptions(@NonNull MethodArgumentNotValidException ex) {
    var errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
        var fieldName = ((FieldError) error).getField();
        var errorMessage = error.getDefaultMessage(); // defaultMessage sẽ lấy từ file properties tương ứng
        errors.put(fieldName, errorMessage);
    });

    return Utils.returnBadRequestResponse("Validation failed", errors);
}
```

### Cách thức hoạt động của i18n

1. Người dùng gửi yêu cầu kèm Header: `Accept-Language: vi` (hoặc `en`).
2. Spring Boot tự động nạp tệp cấu hình tương ứng dưới `resources/`:
    * Tiếng Việt: `ValidationMessages_vi.properties`
    * Tiếng Anh: `ValidationMessages_en.properties`
3. Thông báo lỗi định nghĩa dạng khóa `{system_setting.key.blank}` trong DTO sẽ tự động được thay thế bằng chuỗi đã được dịch trước khi đưa vào `errorMessage`.

---

## 6. Hướng Dẫn Sử Dụng Đối Với Nhà Phát Triển (Developer Guide)

### Bước 1: Ném lỗi nghiệp vụ trong Service

Khi viết logic nghiệp vụ, nếu phát hiện dữ liệu không hợp lệ hoặc vi phạm quy tắc hệ thống, nhà phát triển chỉ cần ném ra [BaseException](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/exception/BaseException.java):

```java
@Service
@RequiredArgsConstructor
public class SystemSettingServiceImpl implements ISystemSettingService {
    
    private final SystemSettingRepository repository;

    @Override
    public SystemSetting getSetting(String key) {
        return repository.findBySettingKey(key)
                .orElseThrow(() -> new BaseException("Cấu hình hệ thống không tồn tại: " + key, HttpStatus.NOT_FOUND));
    }
    
    @Override
    public void createSetting(SystemSettingRequest request) {
        if (repository.existsBySettingKey(request.getSettingKey())) {
            // Ném ngoại lệ mặc định (HTTP Status 400 Bad Request)
            throw new BaseException("Từ khóa cấu hình đã tồn tại trên hệ thống!");
        }
        // ... thực hiện lưu trữ
    }
}
```

### Bước 2: Không cần Try-Catch thủ công tại Controller

Nhờ có `GlobalExceptionHandler`, tại lớp Controller bạn không cần viết các khối lệnh `try-catch` lặp đi lặp lại. Toàn bộ các Exception ném ra từ Service sẽ tự động bong lên và được bắt trọn vẹn:

```java
@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SystemSettingController {

    private final ISystemSettingService service;

    @GetMapping("/{key}")
    public ResponseEntity<WrapperApiResponse> getByKey(@PathVariable String key) {
        // Nếu không tìm thấy, Service ném BaseException (404), GlobalExceptionHandler tự động bắt và trả về JSON chuẩn
        var data = service.getSetting(key);
        return Utils.returnOkResponse("Lấy thông tin thành công", data);
    }
}
```

---

## 7. Các Lưu Ý Bảo Mật Quan Trọng (Developer Security Cautions)

> [!CAUTION]
>
> * **Không in Stack Trace ra API response**: Trong hàm `handleGenericException` (bắt lỗi 500), chúng tôi chỉ in log thô phía máy chủ thông qua `log.error("Unhandled exception occurred: {}", ex.getMessage(), ex)` và trả về thông điệp thân thiện `"Đã xảy ra lỗi hệ thống. Vui lòng thử lại sau."` cho khách hàng. Không bao giờ được phép truyền trực tiếp Stack Trace hay lỗi SQL DB (như `PostgreSQL/Hibernate connection error`) về client.
> * **Đồng bộ mã lỗi HTTP Header**: Đảm bảo mã lỗi số nằm trong thân JSON (`status: 403`) và mã phản hồi ở HTTP Header luôn khớp nhau. Hàm `Utils.buildResponse(...)` đã đảm bảo tính đồng bộ này thông qua việc sử dụng `ResponseEntity.status(statusCode).body(...)`.
