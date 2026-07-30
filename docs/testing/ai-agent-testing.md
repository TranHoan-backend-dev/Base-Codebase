# 🤖 Hướng dẫn Sử dụng AI Agents cho Kiểm thử Automation

> **Metadata**:  
>
> - `@created_at`: 2026-07-30  
> - `@author`: Base-Codebase AI Team  
> - `@references`: `config/agents/`, `Ai_agent_java_test_designmd.markdown`

Tài liệu này hướng dẫn chi tiết cách cấu hình và thực thi hệ thống **AI Agent Tool** (`google-antigravity` SDK) được thiết lập tại Root repository, đặc biệt là **Java Unit Test Automation AI Agent**.

---

## 🧠 1. Kiến trúc & Cấu trúc Thư mục

Toàn bộ cấu hình và code điều phối AI Agent được quản lý tập trung tại thư mục `config/agents/`:

```text
Base-Codebase/
├── config/
│   └── agents/
│       ├── agent_config.py            # Cấu hình Root Agent SDK (Google Antigravity)
│       ├── agent_runner.py            # Root Agent CLI Runner
│       └── java_test_agent/           # Package Java Unit Test Automation Agent
│           ├── __init__.py
│           ├── state_machine.py       # Quản lý 6 Phase Pipeline
│           ├── tools.py               # Maven/Gradle test runner, Log parser, Report writer, Diff fixer
│           ├── agent.py               # JavaTestAgent Controller
│           └── run.py                 # Java Test Agent CLI Runner Entrypoint
└── report/                            # Thư mục lưu báo cáo tự động (đã được .gitignore)
```

---

## ⚙️ 2. Yêu cầu Tiền đề & Cấu hình

1. **Khởi tạo Môi trường ảo (Virtualenv) & Cài đặt Thư viện**:
   - Tạo môi trường ảo Python đặt tên là `antigravity_env` tại gốc dự án:

     ```powershell
     python -m venv antigravity_env
     ```

   - Kích hoạt môi trường ảo:
     - Trên Windows (PowerShell / CMD):

       ```powershell
       antigravity_env\Scripts\activate
       ```

     - Trên Linux / macOS:

       ```bash
       source antigravity_env/bin/activate
       ```

   - Cài đặt SDK `google-antigravity`:

     ```powershell
     pip install google-antigravity
     ```

2. **Cấu hình Biến môi trường (`.env`)**:
   - Khai báo API Key Gemini trong file `.env` tại root repository:

     ```env
     BACKEND_URL=http://localhost:8080
     GEMINI_API_KEY=AIzaSy...
     DEFAULT_MODEL=gemini-2.5-flash
     ```

3. **Kiểm tra cấu hình**:
   - Lệnh xác minh cấu hình Root Agent:

     ```powershell
     antigravity_env\Scripts\python config/agents/agent_runner.py --check-config
     ```

---

## 🚀 3. Hướng dẫn Sử dụng Java Unit Test Automation Agent

### A. Kiểm tra nhanh (Dry-Run Mode)

Trước khi chạy kiểm thử thực tế trên dự án Java (Maven/Gradle), bạn có thể chạy dry-run để kiểm tra pipeline:

```powershell
antigravity_env\Scripts\python config/agents/java_test_agent/run.py --dry-run
```

### B. Thực thi Pipeline 6 Phase trên Dự án Backend (`BaseBackend`)

Chạy agent mặc định với dự án `BaseBackend` (dùng Maven):

```powershell
antigravity_env\Scripts\python config/agents/java_test_agent/run.py --path BaseBackend --build-tool maven
```

Đối với dự án dùng Gradle:

```powershell
antigravity_env\Scripts\python config/agents/java_test_agent/run.py --path apps/my-java-app --build-tool gradle
```

### C. Cơ chế Lọc Tự động theo Git Changes (Mặc định)

- **Quy tắc**: Mặc định Agent sử dụng `git status` / `git diff` để phát hiện các file `.java` (Source code hoặc Test code) bị thay đổi.
  - Nếu file test `XTest.java` thay đổi $\rightarrow$ Chạy test `XTest`.
  - Nếu file source `X.java` thay đổi $\rightarrow$ Tìm và chạy file test `XTest.java` tương ứng.
  - Loại trừ tất cả các file test và source code không có thay đổi.
  - Nếu không có file Java nào thay đổi (Git clean) $\rightarrow$ Agent tự động thực thi toàn bộ test.

- **Chạy toàn bộ test (Tắt lọc Git)**:

  ```powershell
  antigravity_env\Scripts\python config/agents/java_test_agent/run.py --path BaseBackend --all-tests
  ```

- **Chạy duy nhất 1 file test cụ thể**:

  ```powershell
  antigravity_env\Scripts\python config/agents/java_test_agent/run.py --path BaseBackend --test-class UserServiceTest
  ```

### D. Tự động Phê duyệt (Auto-Approve Mode)

Nếu muốn cho phép AI Agent tự động áp dụng bản vá code Java mà không dừng lại chờ duyệt ở Phase 4:

```powershell
antigravity_env\Scripts\python config/agents/java_test_agent/run.py --path BaseBackend --auto-approve
```

---

## 🔄 4. Quy trình 6 Phase Hoạt động (Pipeline)

1. **Phase 1: Run Test** – Agent thực thi `mvn test` hoặc `gradle test` và thu thập logs stdout/stderr.
2. **Phase 2: Analyze Errors** – Parse thông báo lỗi, exception type, stack trace và vị trí class/dòng lỗi.
3. **Phase 3: Generate Report** – Tự động tạo file báo cáo Markdown chuẩn tại gốc repo: `./report/test_report_<timestamp>.md`.
4. **Phase 4: Human Approval** – Hiển thị đường dẫn báo cáo và chờ người dùng xác nhận `(y/n)` có muốn sửa code không.
5. **Phase 5: Fix Code** – Sinh đề xuất bản vá và tiến hành cập nhật file `.java`.
6. **Phase 6: Re-run Test** – Chạy lại unit test để xác minh lỗi đã được khắc phục hoàn toàn.

---

## 📄 5. Quy định Báo cáo (Report Output Rules)

- **Vị trí**: Mọi báo cáo do Agent tạo ra **bắt buộc** lưu tại thư mục `./report` nằm ở **gốc Repository**.
- **Bảo mật**: Thư mục `./report/` đã được cấu hình trong `.gitignore` để không bị commit nhầm lên Git repository.

---

## 💡 6. Tích hợp Agent Skill (`java-unit-test`) cho `BaseBackend`

Đã khởi tạo Skill tại `BaseBackend/.agents/skills/java-unit-test/SKILL.md`.

Khi bạn đưa ra câu lệnh như `"chạy unit test cho BaseBackend"` hoặc `"fix test backend"`, AI Agent sẽ tự động kích hoạt Skill này để thực thi script runner `run_java_test_agent.py`.
