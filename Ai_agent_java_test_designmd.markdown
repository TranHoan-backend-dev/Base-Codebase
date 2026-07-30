# 🤖 AI Agent cho Java Unit Test Automation (Design Script)

## 🎯 Mục tiêu
Xây dựng một hệ thống **AI Agents** có khả năng:

- Chạy unit test (Maven/Gradle)
- Thu thập và phân tích lỗi
- Sinh báo cáo chi tiết
- Đề xuất cách sửa lỗi
- Chờ người dùng phê duyệt (Human-in-the-loop)
- Tự động sửa lỗi khi được duyệt
- Chạy lại test để xác nhận kết quả

---

## 🧠 Kiến trúc tổng thể

Hệ thống gồm một **Orchestrator Agent** và các **Sub-agents chuyên biệt**:

- **Orchestrator Agent**
  - Điều phối toàn bộ pipeline
  - Quản lý trạng thái (phase)
  - Giao tiếp với người dùng

- **Test Runner Agent**
  - Chạy lệnh: `mvn test` hoặc `gradle test`
  - Thu thập log và output

- **Error Analyzer Agent**
  - Parse log (JUnit, stack trace)
  - Xác định:
    - Test fail
    - Exception type
    - Root cause

- **Reporter Agent**
  - Tạo báo cáo chuẩn hóa:
    - Tổng quan test
    - Danh sách lỗi
    - Phân tích nguyên nhân
    - Đề xuất fix

- **Fixer Agent**
  - Sửa code Java
  - Chỉ sửa từng file một
  - Tuân thủ coding convention

---

## 🔄 Pipeline hoạt động

### ✅ Phase 1: Run Test
- Execute:
  - `mvn test` hoặc `gradle test`
- Output:
  - Raw logs
  - Danh sách test fail

---

### ✅ Phase 2: Analyze
- Parse:
  - Stack trace
  - Assertion failures
- Identify:
  - File lỗi
  - Dòng lỗi
  - Nguyên nhân

---

### ✅ Phase 3: Report
Sinh báo cáo phân tích lỗi và lưu file báo cáo vào thư mục:

`./report`

Quy tắc bắt buộc:
- `./report` luôn là thư mục nằm ở **gốc repo**
- Không lưu báo cáo trong thư mục của sub repo như `./backend/report`
- Nếu thư mục `./report` chưa tồn tại thì agent phải tự tạo
- Tên file nên có timestamp hoặc run id để tránh ghi đè

Ví dụ báo cáo:

```
## Báo cáo Unit Test

- Tổng test: X
- Passed: Y
- Failed: Z

### Chi tiết lỗi:
- Test: UserServiceTest#shouldCreateUser
- Lỗi: NullPointerException
- Nguyên nhân: service chưa được mock
- File: UserServiceTest.java:45

### Đề xuất fix:
Before:
userService.create()

After:
when(userService.create()).thenReturn(...)
```

---

### ⏸️ Phase 4: Human Approval
- Hỏi người dùng:
  - Có sửa lỗi không?
- Nếu NO → stop
- Nếu YES → tiếp tục

---

### ✅ Phase 5: Fix
- Sửa từng lỗi một
- Trước mỗi thay đổi:
  - Hiển thị diff
  - Yêu cầu approve

---

### ✅ Phase 6: Re-run Test
- Chạy lại test
- So sánh:
  - Lỗi cũ đã fix?
  - Có lỗi mới không?

---

## 🔐 Safety Policy

### ✅ Cho phép tự động:
- Đọc file
- Phân tích log
- Chạy test (`mvn test`, `gradle test`)

### ⚠️ Cần phê duyệt:
- Sửa file `.java`
- Ghi file

### ❌ Cấm:
- `rm -rf`
- `git reset --hard`
- `git push`

---

## 🧩 Tools cần thiết

- **shell**
  - chạy test
  - đọc log
- **file_io**
  - đọc file Java
- **code_editor**
  - sửa code

---

## 🧠 Quy tắc xử lý lỗi

- Ưu tiên sửa test trước source code
- Không refactor ngoài phạm vi lỗi
- Không đổi tên class/method
- Giữ nguyên style code

---

## 🪝 Human-in-the-loop Hook

Khi agent muốn sửa file:

1. Hiển thị:
   - File
   - Diff
2. Hỏi:
   - yes / no
3. Nếu yes → apply
4. Nếu no → bỏ qua hoặc thử cách khác

---

## 📁 Cấu trúc đề xuất

```
project/
├── agents/
│   ├── orchestrator.py
│   ├── test_runner.py
│   ├── analyzer.py
│   ├── fixer.py
│
├── .agents/
│   ├── skills/
│   │   └── java-test-analyzer/
│   │       └── SKILL.md
│   └── rules/
│       └── coding-rules.md
```

---

## 🚀 Use Cases

- Fix test fail trong CI
- Debug nhanh lỗi local
- Hỗ trợ dev junior
- Auto-suggest fix trong PR

---

## ⚠️ Ràng buộc & lưu ý

- Chỉ dùng trên branch local hoặc sandbox
- Không auto-commit nếu chưa duyệt
- Luôn log toàn bộ hành động

---

## 📁 Report Output Rules

- All generated reports must be written to `./report` at the **repository root**
- This path is resolved from the root repository, not from a backend sub-repository or current working submodule
- Example:
  - Correct: `<repo-root>/report`
  - Incorrect: `<repo-root>/backend/report`
- If `./report` does not exist, the agent must create it before writing files
- Report filenames should include timestamp, task id, or run id to avoid overwriting

---

## ✅ Kết luận

Đây là một hệ thống:
- **Agentic workflow + Human approval**
- Có khả năng:
  - Phân tích lỗi sâu
  - Tự động sửa có kiểm soát
  - Validate kết quả

Phù hợp để triển khai bằng:
- Antigravity SDK (agent runtime)
- Kết hợp Skills + Policy + Hooks

---

## 💡 Gợi ý thêm (rất đáng làm)

Nếu bạn muốn nâng cấp hệ thống này lên level “xịn” hơn:

- 🔹 Thêm **Git integration** (tạo commit sau khi fix)
- 🔹 Export report → **HTML / Slack / PR comment**
- 🔹 Cache lỗi → tránh phân tích lại
- 🔹 Support multi-module Maven

---

Nếu bạn muốn, mình có thể:
👉 Convert cái script này thành **code chạy được luôn (MVP)**  
👉 Hoặc design theo kiểu **clean architecture + plugin system** 👍
