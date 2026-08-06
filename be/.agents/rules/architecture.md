---
trigger: always_on
---

# Architecture Rules for BaseBackend

Hệ thống BaseBackend yêu cầu một thiết kế kiến trúc rõ ràng, dễ bảo trì và mở rộng cho cả Spring Boot và .NET. Tất cả các AI Agent phải tuân theo các nguyên tắc kiến trúc sau khi khởi tạo code.

## 1. Separation of Concerns (Phân tách Trách nhiệm)

### A. Cấu trúc Spring Boot (Java)

Bắt buộc chia dự án thành 3 tầng rõ biệt:

- **Tầng Boundary (API/Controller):**
  - Nhận yêu cầu HTTP (REST).
  - Khai báo Swagger/OpenAPI.
  - Validation dữ liệu đầu vào.
  - Gọi xuống tầng Service, tuyệt đối KHÔNG chứa business logic ở Controller.
- **Tầng Service (Core Business Logic):**
  - Chứa mọi nghiệp vụ quan trọng.
  - Xử lý tính toán, quy tắc nghiệp vụ, kiểm tra quyền hạn (Role/GBAC) (bằng `@PreAuthorize` đối với java).
  - Không biết HTTP Request hay Response là gì.
- **Tầng Data (Repository/DAO):**
  - Chuyên trách tương tác với cơ sở dữ liệu.
  - Trả về Entity hoặc Projections.
  - Chuyển đổi (map) thành DTO trước khi trả ngược lại cho tầng Service nếu cần.

### B. Cấu trúc .NET (C#)

Bắt buộc phân bổ theo các Project trong Solution:

- **Swe.API (Tầng API/Presentation):**
  - Chứa Controllers nhận HTTP requests, cấu hình DI (Dependency Injection), Middleware, filters.
- **Swe.BL (Business Logic Layer):**
  - Chứa các Services thực hiện logic nghiệp vụ, DTOs, validations.
- **Swe.DL (Data Layer):**
  - Chứa Repositories, DbContext, và các logic truy vấn database (Dapper, EF Core).
- **Swe.Common (Common Layer):**
  - Chứa các helpers, constants, enums, exceptions và attributes dùng chung.

## 2. Microservices & Clean Architecture

- **Mô hình DTO:** KHÔNG BAO GIỜ trả Entity trực tiếp ra ngoài API. Bắt buộc tạo Class/Record DTO riêng cho Input (Request) và Output (Response) cho cả Java và C#.
- **Global Exception Handling:**
  - **Spring Boot:** Sử dụng `@RestControllerAdvice` và bọc qua `GlobalExceptionHandler`.
  - **.NET:** Sử dụng Exception Handler Middleware hoặc Custom Exception Filter để hứng các exception và trả về payload API Response chuẩn (`WrapperApiResponse`).

## 3. Dual Datasource (Nếu có đối với SpringBoot)

- Khi sử dụng từ 2 Database trở lên (ví dụ: Postgres cho logic, ClickHouse cho analytics), phải thiết lập cấu hình DB config thành các package độc lập, tạo `LocalContainerEntityManagerFactoryBean` và `TransactionManager` riêng biệt.
- Sử dụng annotation `@Qualifier` rõ ràng khi inject bean.

## 4. Zero Trust Security & Base Validator

- Không tin tưởng bất cứ đầu vào nào từ Client. Mọi Request DTO phải được validate chặt chẽ (sử dụng Data Annotations trong .NET hoặc Jakarta Validation trong Spring Boot).
