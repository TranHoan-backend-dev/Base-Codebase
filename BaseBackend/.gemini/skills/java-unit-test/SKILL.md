---
name: java-unit-test
description: Tự động chạy, phân tích và sửa lỗi Java Unit Test cho dự án BaseBackend bằng cách gọi AI Agent Runner (config/agents/java_test_agent/run.py). Kích hoạt khi user yêu cầu "chạy unit test", "test backend", "unit test", "fix test", hoặc "kiểm thử unit test".
---

# Java Unit Test Skill cho BaseBackend

Skill này tự động kích hoạt **Java Unit Test Automation AI Agent** (`google-antigravity` SDK) để chạy test, phân tích log lỗi, sinh báo cáo và đề xuất/áp dụng sửa lỗi cho dự án `BaseBackend`.

## Quy trình Thực thi

Khi người dùng yêu cầu chạy unit test cho BaseBackend:

1. **Khởi chạy AI Agent Runner**:
   - Thực thi script CLI từ thư mục `config/agents/java_test_agent/run.py` bằng môi trường ảo `antigravity_env`:
     ```powershell
     antigravity_env\Scripts\python config/agents/java_test_agent/run.py --path BaseBackend --build-tool gradle
     ```

2. **Cơ chế Lọc Tự động (Git Changes)**:
   - Mặc định Agent chỉ tự động chạy test cho các file `.java` (Source/Test) có thay đổi theo Git status/diff.
   - Nếu muốn ép buộc chạy **toàn bộ test**, thêm cờ `--all-tests`:
     ```powershell
     antigravity_env\Scripts\python config/agents/java_test_agent/run.py --path BaseBackend --build-tool gradle --all-tests
     ```

3. **Chạy Test Cụ thể (1 File/Class)**:
   - Khi người dùng chỉ định 1 class (ví dụ `UserServiceTest`):
     ```powershell
     antigravity_env\Scripts\python config/agents/java_test_agent/run.py --path BaseBackend --build-tool gradle --test-class UserServiceTest
     ```

4. **Human-in-the-loop & Báo cáo**:
   - Báo cáo lỗi được sinh tại: `./report/test_report_<run_id>.md` ở gốc repo.
   - Agent hiển thị thông tin lỗi và chờ người dùng phê duyệt `(y/n)` trước khi áp dụng sửa code `.java` ngoại trừ trường hợp có cờ `--auto-approve`.
