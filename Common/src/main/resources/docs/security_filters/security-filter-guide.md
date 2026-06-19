# Hướng Dẫn Sử Dụng Hệ Thống Security & Web Filters

Tài liệu này mô tả chi tiết cơ chế hoạt động, bộ lọc an ninh, kiểm toán HTTP và quản lý ThreadLocal Context người dùng trong thư viện `Common`.

---

## 1. Bộ Lọc An Ninh `XssSqlFilter` (XSS & SQL Injection Protection)

`XssSqlFilter` hoạt động ở mức Servlet Container (Ordered.HIGHEST_PRECEDENCE) để quét tất cả dữ liệu đầu vào trước khi đi vào bộ lọc phân quyền hoặc Controller nghiệp vụ.

### Cơ Chế Hoạt Động

- **Query String & Params**: Tự động quét và đối khớp các regex mã độc SQL/XSS trong `queryString` và `parameterMap`.
- **Request Body (JSON)**: Đối với các request `application.json`, bộ lọc bọc request bằng [CachedBodyHttpServletRequest](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/filter/CachedBodyHttpServletRequest.java) để lưu tạm mảng byte của body vào bộ nhớ, cho phép đọc đi đọc lại nhiều lần mà không bị lỗi cạn luồng (`ServletInputStream`).
- **Từ Chối Giao Dịch (Validation & Rejection)**: Nếu phát hiện mã độc, lập tức dừng request và trả về mã lỗi `400 Bad Request` cùng thông điệp giải phân i18n chuẩn.

---

## 2. Nhật Ký Kiểm Toán API `RequestResponseLoggingFilter`

Bộ lọc chạy ngay sau bộ lọc XSS để thu thập thông tin và ghi log kiểm toán (Auditing) cho tất cả các HTTP request/response.

### Các Thông Tin Ghi Log

- **IP Client** & **HTTP Method** & **Request URL** & **Query String**.
- **Request Body**: Nếu request dạng JSON và đã được cached bởi `XssSqlFilter`, bộ lọc sẽ trích xuất body dạng rút gọn tối đa 500 ký tự trên một dòng log duy nhất.
- **Status Code** & **Latency**: Ghi nhận mã trạng thái trả về cùng độ trễ xử lý (Latency) bằng mili giây, giúp dễ dàng theo dõi hiệu năng hệ thống.

---

## 3. Quản Lý Ngữ Cảnh Người Dùng `UserContextHolder`

Để loại bỏ việc gọi lặp đi lặp lại mã nguồn dài dòng để lấy thông tin người dùng từ Spring Security Context, thư viện cung cấp cơ chế giải nén JWT tự động sang ThreadLocal Context.

### Cơ Chế Thiết Lập

1. **UserContext**: Đối tượng POJO lưu trữ các thông tin rút gọn: `userId` (sub của JWT), `username`, và `roles` (danh sách quyền).
2. **UserContextFilter**: Lấy đối tượng `Authentication` từ `SecurityContextHolder`. Nếu là `JwtAuthenticationToken` của Keycloak, giải nén thông tin và ghi nhận vào `UserContextHolder`.
3. **Giải Phóng ThreadLocal**: Sử dụng khối lệnh `finally` để gọi `UserContextHolder.clear()`, tránh hiện tượng rò rỉ bộ nhớ (memory leak) do Tomcat tái sử dụng thread.

### Cách Sử Dụng Trong Code Nghiệp Vụ

Ở bất kỳ class nào (Service, Repository, Listener), bạn có thể lấy ngay thông tin user mà không cần inject dependencies:

```java
String userId = UserContextHolder.get().getUserId();
List<String> userRoles = UserContextHolder.get().getRoles();
```

---

## 4. Cấu Hình URL Công Khai Động (Dynamic Public URLs)

Để hỗ trợ các dự án khác nhau cấu hình các endpoint public không cần bảo mật (như Swagger, public API, Webhook) mà không cần viết lại code Java, bạn cấu hình trực tiếp tại file `application.yaml`:

```yaml
security:
  public-urls:
    - /api/v1/public/**
    - /api/v1/webhooks/**
```

Hệ thống [SecurityConfiguration](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/config/security/SecurityConfiguration.java) sẽ tự động nạp danh sách này để cấp quyền truy cập tự do (`permitAll()`).

---

## 5. Ràng Buộc Validation Bảo Mật `@SqlSafe` & `@XssSafe`

Để tăng cường an ninh mạng ngay từ tầng Validate dữ liệu đầu vào của HTTP Request (trước khi đi vào Controller), thư viện cung cấp các Annotation ràng buộc Jakarta Bean Validation (JSR-380).

### Annotation `@SqlSafe`

- **Chức năng**: Quét chuỗi nhập liệu của Client để tìm kiếm các từ khóa SQL độc hại và cấu trúc truy vấn SQL Injection (ví dụ: `UNION SELECT`, `OR 1=1`, `--`, `xp_cmdshell`).
- **Cách sử dụng**:

  ```java
  public class UserLoginRequest {
      @NotBlank
      @SqlSafe
      private String username;
      
      private String password;
  }
  ```

### Annotation `@XssSafe`

- **Chức năng**: Ngăn chặn XSS (Cross-Site Scripting) bằng cách quét và loại bỏ các chuỗi script hoặc các thẻ HTML độc hại (ví dụ: `<script>`, `javascript:`, `onerror=`, `<iframe`).
- **Cách sử dụng**:

  ```java
  public class PostCreateRequest {
      @NotBlank
      private String title;

      @XssSafe
      private String content; // Ngăn chặn script độc hại từ nội dung bài viết
  }
  ```

### Hỗ Trợ Đa Ngôn Ngữ (i18n)

Thông báo lỗi mặc định được tải từ file `ValidationMessages.properties` dựa vào Locale của Client gửi lên (hỗ trợ Tiếng Việt và Tiếng Anh):

- Key `validation.sql_safe`: *"Dữ liệu đầu vào chứa từ khóa hoặc ký tự SQL Injection nguy hiểm"*
- Key `validation.xss_safe`: *"Dữ liệu đầu vào chứa các thẻ HTML hoặc mã kịch bản XSS không an toàn"*

---

## 6. Che Giấu Dữ Liệu Nhạy Cảm `@SensitiveMask`

Để kiểm soát và phòng ngừa việc rò rỉ dữ liệu cá nhân nhạy cảm của người dùng (Information Sensitive Disclosure), thư viện cung cấp annotation `@SensitiveMask` bọc trên tầng serialization của Jackson.

### Định Dạng Che Giấu (MaskType)

- **EMAIL**: Giữ lại 3 ký tự đầu tiên của phần tên hòm thư, phần còn lại che bằng `****` (Ví dụ: `txhoan.dev@gmail.com` -> `txh****@gmail.com`).
- **PHONE**: Giữ 3 số đầu và 3 số cuối, ở giữa che bằng `****` (Ví dụ: `0987654321` -> `098****321`).
- **PASSWORD**: Luôn che giấu hoàn toàn thành `******`.
- **CREDIT_CARD**: Che giấu toàn bộ chỉ giữ lại 4 chữ số cuối (Ví dụ: `1234567890123456` -> `****-****-****-3456`).
- **ID_CARD**: Giữ 3 số đầu và 3 số cuối của CCCD/CMND, ở giữa che bằng `******` (Ví dụ: `001095123456` -> `001******456`).

### Cách Sử Dụng Trong DTO Response

Bạn chỉ cần khai báo annotation trên trường mong muốn của DTO Response:

```java
public class UserProfileResponse {
    private String id;
    
    private String name;

    @SensitiveMask(MaskType.EMAIL)
    private String email;

    @SensitiveMask(MaskType.PHONE)
    private String phoneNumber;

    @SensitiveMask(MaskType.CREDIT_CARD)
    private String creditCardNo;
}
```

### Cơ Chế Tự Động Hoạt Động

- **Tự động trong Controller**: Khi REST Controller trả về đối tượng này cho Client, Jackson Serializer sẽ tự động nhận diện annotation (thông qua `@JacksonAnnotationsInside`) và thực hiện che thông tin trước khi chuyển thành JSON gửi qua mạng.
- **Tuần tự hóa thủ công**: Khi chuyển đổi thủ công thông qua lớp tiện ích `JsonUtils.toJson(dto)`, dữ liệu cũng tự động được che giấu tương tự.
