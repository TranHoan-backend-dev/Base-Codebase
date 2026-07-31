# 📝 Hướng dẫn Sử dụng Changelog & Release Note Generator Agent

> **Metadata**:  
> - `@created_at`: 2026-07-31  
> - `@author`: Base-Codebase AI Team  
> - `@references`: `config/agents/changelog_agent/`

Agent này đọc thông điệp commit từ Git history, tự động phân loại theo chuẩn Conventional Commits (`feat`, `fix`, `docs`, `refactor`, `chore`) và tổng hợp ra Release Notes chuẩn Markdown.

---

## 🧠 1. Kiến trúc & Cấu trúc Thư mục

```text
Base-Codebase/
├── config/
│   └── agents/
│       └── changelog_agent/
│           ├── __init__.py
│           ├── agent.py          # ChangelogAgent Controller
│           ├── tools.py          # Git Log parser & Conventional Commit categorizer
│           └── run.py            # CLI Entrypoint
└── report/
    └── changelog-<date>.md       # File Release Notes xuất ra theo ngày
```

---

## 🚀 2. Hướng dẫn Sử dụng

Chạy agent mặc định (phân tích 50 commits gần nhất):

```powershell
antigravity_env\Scripts\python config/agents/agent_runner.py --agent changelog_agent
```

Chạy agent với số lượng commit tùy chỉnh:

```powershell
antigravity_env\Scripts\python config/agents/changelog_agent/run.py --limit 100
```

---

## 🔄 3. Quy trình Hoạt động (Pipeline)

1. **Phase 1: Fetch Git Logs**: Thực thi `git log` từ gốc repository.
2. **Phase 2: Conventional Categorization**: Lọc và đưa commit vào các danh mục (Features, Bug Fixes, Documentation, Refactoring, Chores).
3. **Phase 3: Generate Markdown**: Đóng gói thành file Release Notes hoàn chỉnh.
4. **Phase 4: Export Report**: Lưu file tại `./report/changelog-<YYYY-MM-DD>.md`.
