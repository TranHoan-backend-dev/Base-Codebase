# Project Structure cho BaseBackend

Tài liệu này cung cấp ánh xạ các thư mục quan trọng trong `be/` để AI hiểu rõ cần làm gì và đặt file ở đâu.

## 1. Cấu trúc Thư mục Chính

```text
be/
├── .Net/                 # Dự án Backend cơ sở (.NET 8+)
│   ├── Swe.API/          # Tầng API/Presentation (Controllers, Program.cs)
│   ├── Swe.BL/           # Tầng Business Logic (Services, DTOs)
│   ├── Swe.DL/           # Tầng Data Access (Context, Repositories)
│   ├── Swe.Common/       # Thư mục chứa Common, Utilities, Attributes
│   └── backend.sln       # Solution file của .NET
├── SpringBoot/           # Dự án Backend cơ sở (Spring Boot 3)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/     # Mã nguồn Java
│   │   │   └── resources/# Cấu hình application.yaml, docs/.specify
│   │   └── test/         # Thư mục unit test
│   ├── build.gradle.kts  # Tệp cấu hình Gradle
│   └── settings.gradle.kts
├── CLAUDE.md             # File quy định tổng quát cho AI
├── .claude.json          # Cấu hình nội bộ cho quyền chạy lệnh
├── .mcp.json             # File cấu hình lấy docs cho MCP
└── .claude/              # Chứa toàn bộ Context, Rules và Agents chuyên môn
```

## 2. Tài liệu Đặc tả (Specification)

- **Spring Boot:** Lưu tại `be/SpringBoot/src/main/resources/docs/.specify/`
- **.NET:** Lưu tại `be/.Net/docs/` hoặc theo quy định cụ thể của dự án .NET.
- AI không bao giờ được phép bắt đầu sinh code nếu file đặc tả tương ứng chưa tồn tại hoặc chưa được thống nhất với User.

## 3. Thư mục `.agents/agents/`

- Đây là nơi tổ chức các vai trò AI.
- Nếu phát sinh vấn đề liên quan đến bảo mật, AI có thể đọc file `devsecops.md`. Nếu liên quan thiết kế DB, đọc `data-architect.md`. Nếu liên quan tổ chức phân tán, đọc `microservice-expert.md`.
