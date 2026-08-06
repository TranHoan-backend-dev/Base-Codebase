# Cấu hình CLAUDE / GEMINI cho Base Backend

Dự án này là Backend cơ sở được phát triển bằng 2 công nghệ chính:

1. **Java 21+ & Spring Boot 3.x** (nằm trong `SpringBoot/`)
2. **C# & .NET 8+** (nằm trong `.Net/`)

Các AI Agent khi hoạt động trong thư mục `be/` PHẢI tuân thủ các quy tắc sau đây.

## 1. Lệnh Thực thi Bắt Buộc (Commands)

### A. Spring Boot (Java)

- **Build (không test):** `./gradlew build -x test` (hoặc `.\gradlew.bat build -x test` trên Windows)
- **Test:** `./gradlew test`
- **Clean:** `./gradlew clean`
- **Chạy ứng dụng:** `./gradlew bootRun`

### B. .NET (C#)

- **Build:** `dotnet build .Net/backend.sln`
- **Test:** `dotnet test .Net/backend.sln`
- **Clean:** `dotnet clean .Net/backend.sln`
- **Chạy ứng dụng API:** `dotnet run --project .Net/Swe.API/Swe.API.csproj`

## 2. Ngữ cảnh & Hệ thống Kiến thức (Context & Knowledge)

Base Backend có hệ thống quy tắc chi tiết nằm trong thư mục `.claude/`. Trước khi bắt tay vào thiết kế hay code, AI phải tham chiếu tới:

### A. Quy tắc Kiến trúc (`.claude/rules/architecture.md`)

- Phân chia trách nhiệm nghiêm ngặt: Tầng Boundary (API/Controller), Tầng Service (Core Business Logic), Tầng Data (Repository/DL).
- Clean Architecture / Hexagonal.
- GBAC (Group-Based Access Control) và Microservices.
- **Zero Trust Security:** Không tin tưởng bất cứ DTO/Input nào. Bắt buộc tạo Base Validator ở mọi tầng để chặn dữ liệu độc hại (Defense in Depth).

### B. Chuẩn Viết Code (`.claude/rules/coding-standards.md`)

- **Java:** Sử dụng Record cho DTO, sử dụng triệt để `var`.
- **C#:** Sử dụng `record` cho DTO, sử dụng triệt để `var`.
- Comment, JDocs/XML comments, `@created_at`, `@author` và quy trình tạo tài liệu đặc tả (specs).

### C. Cấu trúc Dự án (`.claude/context/project-structure.md`)

- Ánh xạ cấu trúc thư mục của Spring Boot và .NET.

### D. Hệ thống Agent Chuyên gia (`.claude/agents/`)

- Mọi quyết định về công nghệ, bảo mật, hay phân tích DB phải được tham vấn chéo các vai trò: `java-backend-expert`, `devsecops`, `data-architect`, `solution-architect`, `microservice-expert`.

## 3. Quy trình Vận hành Riêng

1. **Tuân thủ Monorepo:** Quy tắc trong file này bổ sung cho `CLAUDE.md` ở thư mục Root. Nếu có xung đột về Backend, ưu tiên file này.
2. **Docs-driven Development:** Không tạo code Backend khi chưa thiết kế tài liệu đặc tả trong thư mục specs tương ứng (ví dụ: `src/main/resources/docs/.specify` hoặc `.Net/docs/`).
3. **Môi trường:** Đảm bảo sử dụng JDK 21+ cho Java (tương thích Records và Pattern Matching) và SDK .NET 10 cho dự án .NET.
