# Coding Standards for BaseBackend

Đây là tài liệu quy định về chuẩn mực viết mã áp dụng riêng cho `BaseBackend`. AI phải kiểm tra code dựa trên các quy tắc này.

## 1. Lập trình Hướng đối tượng & Data-Oriented

- **OOP:** Sử dụng tính kế thừa, đa hình và đóng gói khi thiết kế các Service lớn.
- **Data-oriented:** Ưu tiên Record của Java 21 cho các DTO (Data Transfer Objects) thay vì Class thông thường (giảm boilerplate code lombok không cần thiết).

  ```java
  public record UserResponse(UUID id, String username) {}
  ```

## 2. Sử dụng `var`

- Mọi biến cục bộ (local variables) có thể tự suy luận kiểu dữ liệu một cách rõ ràng đều BẮT BUỘC sử dụng từ khóa `var`.

  ```java
  // Tốt:
  var user = userRepository.findById(id);
  var dtoList = new ArrayList<UserDto>();
  
  // Không tốt:
  UserEntity user = userRepository.findById(id);
  ```

## 3. Quy chuẩn Bình luận (Comments & Docs)

Bắt buộc có comment hoặc Javadoc ở các thành phần cốt lõi (Class, Public Method, Logic phức tạp).

- **Javadoc cho Interface/Class/Method:** Phải mô tả chi tiết chức năng, tác giả, thời gian tạo, và nguồn tham khảo nếu có.

  ```java
  /**
   * Cung cấp các thao tác liên quan đến xác thực người dùng.
   * Xử lý đăng nhập, cấp phát JWT token và phân quyền.
   *
   * @author [AI/Tên của bạn]
   * @created_at [Ngày tháng]
   * @references [URL hoặc Tên tài liệu nếu lấy logic từ nguồn khác]
   */
  public class AuthService { ... }
  ```

- **Inline Comments:** Giải thích "TẠI SAO" (Why) cho các block code tính toán phức tạp, thay vì mô tả "CÁI GÌ" (What) đang xảy ra.

## 4. Tài liệu Đặc tả (Specification)

- Bất kỳ API hoặc tính năng mới nào (ví dụ: Auth, Payment) trước khi code phải có bản thiết kế/đặc tả lưu vào thư mục `app/main/resources/docs/.specify`.
- Không bắt đầu viết Code Java nếu chưa viết xong file Markdown trong thư mục `.specify` mô tả Input/Output của tính năng đó.
