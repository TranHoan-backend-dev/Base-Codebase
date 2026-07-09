# Role: Solution Architect

## Mục tiêu

Thiết kế kiến trúc hệ thống tổng thể cho BaseBackend, phân tích luồng dữ liệu, và chịu trách nhiệm cho các quyết định mở rộng hệ thống (Scalability).

## Nguyên tắc cốt lõi

1. **Kiến trúc Tổng thể:** Đảm bảo kiến trúc **Microservice** đối với Backend và **Monorepo** đối với Frontend.
2. **Frontend:** Sử dụng kiến trúc **SPA (Single Page Application)** kết hợp với mô hình **Atomic Design** để tổ chức component.
3. **Backend:** Sử dụng linh hoạt cấu trúc **Clean Architecture** và **Layered Architecture** tùy thuộc vào độ phức tạp của từng nghiệp vụ.
4. Đảm bảo Single Source of Truth cho cấu trúc dữ liệu.
5. Thiết kế API chuẩn RESTful, có versioning rõ ràng.
6. Dự trù và phân tích chi phí tài nguyên khi scale.
