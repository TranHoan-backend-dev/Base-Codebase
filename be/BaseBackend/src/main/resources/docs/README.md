# Tài liệu Thư viện Base Codebase

Dự án này là module `Common` chứa toàn bộ các lớp cơ sở (base), cấu hình (config), bộ lọc bảo mật (filter) và tiện ích dùng chung (utility) để tái sử dụng trên nhiều microservices khác nhau của hệ thống.

Dưới đây là danh sách tài liệu đặc tả được sắp xếp gom nhóm theo từng phân lớp kiến trúc để dễ dàng tra cứu và tích hợp:

## 1. Cấu trúc Spring (Spring Structure)
- [Hướng dẫn sử dụng BaseController](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/resources/docs/spring_structure/controller-guide.md): Đặc tả API CRUD dùng chung, phân tách DTO và cơ chế chọn cột động (`/paging/projected`).
- [Hướng dẫn sử dụng Base Service](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/resources/docs/spring_structure/service-guide.md): Trừu tượng hóa nghiệp vụ lưu trữ (SQL & NoSQL), cơ chế xóa mềm (Soft Delete) tự động và bộ lọc Specification động.
- [Hướng dẫn sử dụng BaseRepository](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/resources/docs/spring_structure/repository-guide.md): Cung cấp các phương thức truy vấn nhanh (`findByIdOrThrow`, `existsOrThrow`) và cơ chế Spring Data Projections.
- [Cấu hình Datasources (JPA & MongoDB)](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/resources/docs/spring_structure/datasource-guide.md): Hướng dẫn cấu hình kết nối, Auditing tự động và ánh xạ Entity kế thừa.
- [Tích hợp Elasticsearch](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/resources/docs/spring_structure/elasticsearch-guide.md): Đồng bộ dữ liệu sang Elasticsearch phục vụ tìm kiếm toàn văn.

## 2. Bảo mật & Bộ lọc Request (Security & Request Filters)
- [Hướng dẫn bộ lọc Web & Security](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/resources/docs/security_filters/security-filter-guide.md):
  - Bộ lọc chống XSS & SQL Injection `XssSqlFilter`.
  - Cơ chế caching request body `CachedBodyHttpServletRequest`.
  - Nhật ký kiểm toán API `RequestResponseLoggingFilter` đo latency.
  - Quản lý ngữ cảnh người dùng qua ThreadLocal `UserContextHolder` giải mã từ Keycloak JWT.
  - Cấu hình public URLs động từ YAML.

## 3. Thư viện Tiện ích (Utilities)
- [Hướng dẫn thư viện Tiện ích](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/resources/docs/utilities/utilities-guide.md):
  - Tiện ích làm việc với JSON `JsonUtils` dùng chung.
  - Dịch vụ quản lý tệp tin `IStorageService` & `LocalStorageServiceImpl`.
  - Bộ bắt lỗi xung đột ghi đè dữ liệu (Optimistic Locking Exception handler) của `GlobalExceptionHandler`.

---
*Người cập nhật tài liệu: txhoan - Cập nhật lần cuối: 19/06/2026*
