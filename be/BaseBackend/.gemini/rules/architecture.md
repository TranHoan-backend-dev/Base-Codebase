# Architecture Rules for BaseBackend

Hệ thống BaseBackend yêu cầu một thiết kế kiến trúc rõ ràng, dễ bảo trì và mở rộng. Tất cả các AI Agent phải tuân theo các nguyên tắc kiến trúc sau khi khởi tạo code.

## 1. Separation of Concerns (Phân tách Trách nhiệm)

Bắt buộc chia dự án thành 3 tầng rõ biệt. Code ở tầng nào chỉ làm nhiệm vụ của tầng đó:

- **Tầng Boundary (API/Controller):**
  - Nhận yêu cầu HTTP (REST).
  - Khai báo Swagger/OpenAPI.
  - Validation dữ liệu đầu vào.
  - Gọi xuống tầng Service, tuyệt đối KHÔNG chứa business logic ở Controller.
- **Tầng Service (Core Business Logic):**
  - Chứa mọi nghiệp vụ quan trọng.
  - Xử lý tính toán, quy tắc nghiệp vụ, kiểm tra quyền hạn (Role/GBAC) bằng `@PreAuthorize`.
  - Không biết HTTP Request hay Response là gì.
- **Tầng Data (Repository/DAO):**
  - Chuyên trách tương tác với cơ sở dữ liệu.
  - Trả về Entity hoặc Projections.
  - Chuyển đổi (map) thành DTO trước khi trả ngược lại cho tầng Service nếu cần.

## 2. Microservices & Clean Architecture

- **Mô hình DTO:** KHÔNG BAO GIỜ trả Entity trực tiếp ra ngoài API. Bắt buộc tạo Class DTO riêng cho Input (Request) và Output (Response).
- **Global Exception Handling:** Mọi lỗi xảy ra phải ném ra Exception tùy chỉnh và được hứng ở lớp `GlobalExceptionHandler` (sử dụng `@RestControllerAdvice`), sau đó bọc thành cấu trúc JSON chuẩn trước khi trả về.

## 3. Dual Datasource (Nếu có)

- Khi sử dụng từ 2 Database trở lên (ví dụ: Postgres cho logic, ClickHouse cho analytics), phải thiết lập cấu hình DB config thành các package độc lập, tạo `LocalContainerEntityManagerFactoryBean` và `TransactionManager` riêng biệt.
- Sử dụng annotation `@Qualifier` rõ ràng khi inject bean.
