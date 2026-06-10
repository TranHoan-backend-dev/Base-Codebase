# Hướng Dẫn Sử Dụng Bộ Lọc An Ninh XssSqlFilter

Tài liệu này mô tả cơ chế hoạt động và cấu hình của bộ lọc an ninh `XssSqlFilter` trong thư viện `Common`, giúp bảo vệ toàn bộ ứng dụng khỏi các cuộc tấn công SQL Injection và XSS (Cross-Site Scripting) từ cấp độ Servlet Filter đầu vào.

---

## 1. Cơ Chế Hoạt Động

`XssSqlFilter` hoạt động ở cấp độ mạng (Servlet Container), trước khi Request đi vào Spring Security và các Controller của Spring MVC.

Bộ lọc thực hiện quét các thành phần sau của Request:

1. **Query String:** Quét toàn bộ chuỗi truy vấn trên URL.
2. **Request Parameters:** Quét toàn bộ tham số truyền lên qua URL (Query Params) hoặc Form Data.
3. **Request Body (JSON):** Chỉ thực hiện đối với các Request có header `Content-Type: application/json`.

> [!NOTE]
> Để tránh làm hỏng luồng tải lên tệp tin (Multipart Upload), bộ lọc tự động bỏ qua việc quét Request Body đối với các request upload file (`multipart/form-data`). Tuy nhiên, các tham số URL đi kèm vẫn được quét bình thường.

---

## 2. Giải Pháp Xử Lý Caching Request Body

Trong môi trường Servlet tiêu chuẩn, luồng dữ liệu (Input Stream) của Request Body chỉ có thể đọc **một lần duy nhất**. Nếu Filter đọc nội dung để kiểm tra mã độc, Spring Controller sẽ không thể đọc lại dữ liệu này nữa, gây ra lỗi ứng dụng.

Để khắc phục hạn chế này, thư viện cung cấp bộ đôi:

* [CachedBodyHttpServletRequest](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/filter/CachedBodyHttpServletRequest.java): Lưu trữ (caching) dữ liệu thô của Request Body vào bộ nhớ tạm dưới dạng mảng byte.
* [CachedBodyServletInputStream](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/filter/CachedBodyServletInputStream.java): Luồng đọc dữ liệu tùy chỉnh cho phép Spring Controller đọc lại luồng dữ liệu này nhiều lần một cách bình thường.

---

## 3. Quy Trình Validate và Phản Hồi Lỗi

Bộ lọc sử dụng các hàm kiểm tra mã độc đã được định nghĩa tối ưu trong [Utils.java](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/utilities/Utils.java):

* `Utils.containsSqlInjection(value)`
* `Utils.containsXss(value)`

Khi phát hiện bất kỳ chuỗi dữ liệu nào chứa ký tự hoặc từ khóa độc hại, bộ lọc lập tức dừng xử lý Request và trả về mã lỗi **400 Bad Request** với cấu trúc JSON chuẩn `WrapperApiResponse`. 

Thông điệp lỗi (`message`) sẽ được phân giải đa ngôn ngữ động bằng `MessageService` dựa trên loại lỗi phát hiện (ví dụ: lỗi trong URL, lỗi ở tham số hay trong request body):

```json
{
  "status": 400,
  "message": "Nội dung Request Body chứa ký tự hoặc từ khóa không an toàn.", // Tự động hiển thị Tiếng Việt hoặc Tiếng Anh tương ứng
  "data": null,
  "timestamp": "2026-06-10T21:39:00+07:00"
}
```


> [!TIP]
> **Vì sao lại từ chối thay vì tự động làm sạch (Sanitization)?**
> Việc tự động thay thế/loại bỏ thẻ (ví dụ loại bỏ `<script>`) có thể làm thay đổi nội dung dữ liệu gốc của người dùng hợp lệ một cách không mong muốn. Do đó, việc từ chối thẳng thừng (Validation & Rejection) là giải pháp an toàn và chuẩn mực nhất cho thiết kế API hiện đại.

---

## 4. Tích Hợp Độc Lập Với Annotation `@SafeString`

Bên cạnh bộ lọc kiểm tra toàn bộ Request ở mức toàn cục (`XssSqlFilter`), bạn vẫn có thể sử dụng annotation [@SafeString](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/utilities/SafeString.java) ở mức DTO (Request Body payload) để kiểm tra cục bộ cho từng trường cụ thể bằng cơ chế Hibernate Validator:

```java
public record CreateUserRequest(
    @NotBlank
    String username,

    @SafeString // <-- Kiểm tra trường này không chứa SQL Injection/XSS
    String bio
) {}
```
