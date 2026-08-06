---
trigger: always_on
---

# Coding Standards for BaseBackend

Đây là tài liệu quy định về chuẩn mực viết mã áp dụng riêng cho `BaseBackend` (bao gồm cả Spring Boot và .NET). AI phải kiểm tra code dựa trên các quy tắc này.

## 1. Lập trình Hướng đối tượng & Data-Oriented

- **OOP:** Sử dụng tính kế thừa, đa hình và đóng gói khi thiết kế các Service lớn.
- **Data-oriented:**
  - **Java:** Ưu tiên Record của Java 21 cho các DTO (Data Transfer Objects) thay vì Class thông thường.
  - **C#:** Ưu tiên `record` hoặc `record struct` cho các DTO/Request/Response.

## 2. Sử dụng `var`

- Mọi biến cục bộ (local variables) có thể tự suy luận kiểu dữ liệu một cách rõ ràng đều BẮT BUỘC sử dụng từ khóa `var` cho cả C# và Java.

  ```java
  // Java:
  var user = userRepository.findById(id);
  var dtoList = new ArrayList<UserDto>();

  ```csharp
  // C#:
  var user = userRepository.GetById(id);
  ```

## 3. Quy chuẩn Bình luận (Comments & Docs)

Bắt buộc có comment hoặc XML Documentation/Javadoc ở các thành phần cốt lõi (Class, Public Method, Logic phức tạp).

- **Javadoc cho Interface/Class/Method:** Phải mô tả chi tiết chức năng, tác giả, thời gian tạo, và nguồn tham khảo nếu có.

  ```java
  /**
   * Cung cấp các thao tác liên quan đến xác thực người dùng.
   * Xử lý đăng nhập, cấp phát JWT token và phân quyền.
   *
   * @author [Tên tác giả]
   * @created_at [Ngày tháng]
   */
  public class AuthService { ... }
  ```

- **Inline Comments:** Giải thích "TẠI SAO" (Why) cho các block code tính toán phức tạp, thay vì mô tả "CÁI GÌ" (What) đang xảy ra.

- **C# (XML Comments):**

  ```csharp
  /// <summary>
  /// Cung cấp các thao tác liên quan đến xác thực người dùng.
  /// </summary>
  /// <remarks>
  /// @author [Tên tác giả]
  /// @created_at [Ngày tháng]
  /// </remarks>
  ```

## 4. Tài liệu Đặc tả (Specification)

- Bất kỳ API hoặc tính năng mới nào trước khi code phải có bản thiết kế/đặc tả lưu vào thư mục docs tương ứng.
- Không bắt đầu viết Code nếu chưa viết xong file Markdown đặc tả mô tả Input/Output của tính năng đó.
