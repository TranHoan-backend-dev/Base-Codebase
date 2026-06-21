# Cấu hình CLAUDE cho BaseBackend

Dự án này là Backend cơ sở được phát triển bằng **Java 21+** và **Spring Boot 3.x**.
Các AI Agent (như Claude) khi hoạt động trong thư mục `BaseBackend/` PHẢI tuân thủ các quy tắc sau đây.

## 1. Lệnh Thực thi Bắt Buộc (Commands)

Agent được phép và được khuyến khích sử dụng các lệnh Gradle wrapper sau để thao tác:

- **Build (không test):** `./gradlew build -x test`
- **Test:** `./gradlew test`
- **Clean:** `./gradlew clean`
- **Chạy ứng dụng:** `./gradlew bootRun`

*Lưu ý:* Bắt buộc dùng `./gradlew` (Linux/Mac) hoặc `.\gradlew.bat` (Windows) thay vì lệnh `gradle` cục bộ để đảm bảo đồng bộ phiên bản.

## 2. Ngữ cảnh & Hệ thống Kiến thức (Context & Knowledge)

BaseBackend có hệ thống quy tắc chi tiết nằm trong thư mục `.claude/`. Trước khi bắt tay vào thiết kế hay code, AI phải tham chiếu tới:

### A. Quy tắc Kiến trúc (`.claude/rules/architecture.md`)

- Phân chia trách nhiệm nghiêm ngặt: Tầng Boundary (API/Controller), Tầng Service (Core Business Logic), Tầng Data (Repository/DAO).
- Clean Architecture / Hexagonal.
- GBAC (Group-Based Access Control) và Microservices.

### B. Chuẩn Viết Code (`.claude/rules/coding-standards.md`)

- Lập trình hướng đối tượng (OOP) & Data-oriented.
- Sử dụng triệt để `var`.
- Comment, Javadocs, `@created_at`, `@author` và quy trình tạo tài liệu đặc tả (specs).

### C. Cấu trúc Dự án (`.claude/context/project-structure.md`)

- Mô tả mapping logic các package, quy định thư mục tài liệu `resources/docs/.specify`.

### D. Hệ thống Agent Chuyên gia (`.claude/agents/`)

- Mọi quyết định về công nghệ, bảo mật, hay phân tích DB phải được tham vấn chéo các vai trò: `java-backend-expert`, `devsecops`, `data-architect`, `solution-architect`, `microservice-expert`.

## 3. Quy trình Vận hành Riêng

1. **Tuân thủ Monorepo:** Quy tắc trong file `CLAUDE.md` này bổ sung cho `CLAUDE.md` ở thư mục Root. Nếu có xung đột về Backend, ưu tiên file này.
2. **Docs-driven Development:** Không tạo code Backend khi chưa thiết kế tài liệu đặc tả trong `resources/docs/.specify`.
3. **Môi trường:** Đảm bảo IDE sử dụng JDK 21+ để tương thích tính năng Records và Pattern Matching.
