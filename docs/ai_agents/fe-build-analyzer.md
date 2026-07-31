# 🌐 Hướng dẫn Sử dụng Frontend Build & Error Analyzer Agent

> **Metadata**:  
>
> - `@created_at`: 2026-07-31  
> - `@author`: Base-Codebase AI Team  
> - `@references`: `config/agents/fe_build_analyzer_agent/`

Agent này tự động chạy lệnh build cho các dự án Frontend (`fe/nextjs-base` và `fe/nuxtjs-base`), thu thập và phân tích các lỗi TypeScript (`tsc`), ESLint và build errors.

---

## 🧠 1. Kiến trúc & Cấu trúc Thư mục

```text
Base-Codebase/
├── config/
│   └── agents/
│       └── fe_build_analyzer_agent/
│           ├── __init__.py
│           ├── agent.py          # FEBuildAnalyzerAgent Controller
│           ├── tools.py          # Process runner & Log parser
│           └── run.py            # CLI Entrypoint
└── report/
    └── fe_build_report.md       # Báo cáo kết quả build & lỗi chi tiết
```

---

## ⚙️ 2. Yêu cầu Tiền đề & Cấu hình

Đảm bảo đã cài đặt Node.js và pnpm/npm dependencies cho các sub-projects Frontend:

```powershell
cd fe/nextjs-base && pnpm install
cd ../fe/nuxtjs-base && pnpm install
```

---

## 🚀 3. Hướng dẫn Sử dụng

Thực thi agent qua runner chính:

```powershell
antigravity_env\Scripts\python config/agents/agent_runner.py --agent fe_build_analyzer_agent
```

---

## 🔄 4. Quy trình Hoạt động (Pipeline)

1. **Phase 1: Build Execution**: Chạy `npm run build` độc lập trên từng dự án FE.
2. **Phase 2: Error Collection**: Thu thập logs `stdout` và `stderr`.
3. **Phase 3: Error Parsing**: Lọc và phân loại lỗi liên quan tới missing imports, type mismatches, missing env.
4. **Phase 4: Report & Fix Suggestions**: Xuất báo cáo và gợi ý bản vá tại `./report/fe_build_report.md`.
