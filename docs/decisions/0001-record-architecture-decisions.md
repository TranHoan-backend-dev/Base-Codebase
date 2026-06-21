# 1. Ghi lại các Quyết định Kiến trúc (ADR) và Áp dụng Zero Trust

**Trạng thái (Status):** Chấp thuận (Accepted)
**Ngày tạo (Date):** 2026-06-21
**Người quyết định (Deciders):** AI Architect & Lead Developer

## Bối cảnh (Context)

Dự án được xây dựng dưới dạng Monorepo bao gồm một Backend (Java Spring Boot) và hai hệ thống Frontend (Next.js, Nuxt.js). Trong một hệ thống phức tạp với sự tham gia của AI Coding Assistants, việc ghi nhớ lý do tại sao một mô hình kiến trúc (như BFF hay Atomic Design) được lựa chọn là cực kỳ khó khăn nếu không có tài liệu lưu trữ.
Ngoài ra, bảo mật dữ liệu ở các hệ thống hiện đại đối mặt với nhiều rủi ro nếu chỉ xác thực ở Backend.

## Quyết định (Decision)

1. Chúng ta sẽ sử dụng Kiến trúc **ADR (Architecture Decision Records)** nằm trong thư mục `docs/decisions/` để lưu lại mọi quyết định thiết kế quan trọng.
2. Áp dụng kiến trúc bảo mật **Zero Trust** cho toàn bộ dự án:
   - Frontend không giao tiếp trực tiếp với Backend mà phải qua lớp **BFF (Backend For Frontend)**.
   - Luôn luôn có Base Validator ở mọi tầng (BFF, Services, Backend Controllers).
   - Truyền token dưới dạng HttpOnly cookie tại BFF, chặn XSS.

## Hậu quả (Consequences)

- **Tích cực:** AI Assistant và Developer mới vào dự án có thể đọc thư mục này để hiểu rõ toàn bộ bối cảnh và tuân thủ các quy định bảo mật một cách tự giác. Tăng cường khả năng Defense in Depth.
- **Tiêu cực:** Tốn thời gian tạo tài liệu ADR ban đầu, và tốn thêm chi phí thiết kế lớp BFF thay vì gọi thẳng API.
