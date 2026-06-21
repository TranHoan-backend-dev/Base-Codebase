# Project Structure cho BaseBackend

Tài liệu này cung cấp ánh xạ các thư mục quan trọng trong `BaseBackend` để AI hiểu rõ cần làm gì và đặt file ở đâu.

## 1. Cấu trúc Thư mục Chính

```text
BaseBackend/
├── src/
│   ├── main/
│   │   ├── java/         # Mã nguồn Java chính
│   │   │   └── com/example/base/ # Root package (Ví dụ)
│   │   │       ├── api/         # Tầng Boundary (Controllers, REST endpoints)
│   │   │       ├── service/     # Tầng Service (Business logic)
│   │   │       ├── repository/  # Tầng Data (Spring Data JPA)
│   │   │       ├── dto/         # Request/Response DTOs (sử dụng Records)
│   │   │       ├── entity/      # Database Entities (JPA)
│   │   │       ├── exception/   # Custom Exceptions và GlobalExceptionHandler
│   │   │       └── config/      # Cấu hình Spring (Security, Datasource)
│   │   └── resources/    # Cấu hình tài nguyên (.yml, .properties, tĩnh)
│   │       └── docs/
│   │           └── .specify/    # Thư mục bắt buộc chứa Tài liệu đặc tả tính năng
│   └── test/
│       └── java/         # Thư mục chứa Unit Test và Integration Test
├── build.gradle.kts      # Tệp cấu hình Gradle (Kotlin DSL)
├── settings.gradle.kts   # Tệp settings của Gradle
├── CLAUDE.md             # File quy định tổng quát cho AI khi làm việc
├── .claude.json          # Cấu hình nội bộ cho quyền chạy lệnh
├── .mcp.json             # File cấu hình lấy docs cho MCP
└── .claude/              # Chứa toàn bộ Context, Rules và Agents chuyên môn
```

## 2. Thư mục `resources/docs/.specify/` (Bắt Buộc)

- Đây là nơi chứa các file Markdown (`*.md`) mô tả chi tiết logic, API Contract, và thiết kế của mọi tính năng Backend **TRƯỚC KHI** code.
- Mỗi module nghiệp vụ lớn (ví dụ `authentication`, `payment`, `dynamic-grid`) sẽ có một file `.md` ở đây.
- AI không bao giờ được phép bắt đầu sinh code nếu file `.specify` tương ứng chưa tồn tại hoặc chưa được thống nhất với User.

## 3. Thư mục `.claude/agents/`

- Đây là nơi tổ chức các vai trò AI.
- Nếu phát sinh vấn đề liên quan đến bảo mật, AI có thể đọc file `devsecops.md`. Nếu liên quan thiết kế DB, đọc `data-architect.md`. Nếu liên quan tổ chức phân tán, đọc `microservice-expert.md`.
