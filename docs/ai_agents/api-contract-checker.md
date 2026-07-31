# 🔍 Hướng dẫn Sử dụng API Contract Checker Agent

> **Metadata**:  
>
> - `@created_at`: 2026-07-31  
> - `@author`: Base-Codebase AI Team  
> - `@references`: `config/agents/api_contract_agent/`

Agent này thực hiện tự động kiểm tra và đối soát tính đồng bộ về REST API Contract giữa Backend (`BaseBackend`) và Frontend (`nextjs-base`, `nuxtjs-base`).

---

## 🧠 1. Kiến trúc & Cấu trúc Thư mục

```text
Base-Codebase/
├── config/
│   └── agents/
│       └── api_contract_agent/
│           ├── __init__.py
│           ├── agent.py          # Controller chính cho API Contract Agent
│           ├── tools.py          # Parsers cho Spring Controllers và FE API calls
│           └── run.py            # CLI Runner Entrypoint
└── report/
    └── api_contract_report.md    # Báo cáo kết quả đối soát contract (Auto-generated)
```

---

## ⚙️ 2. Yêu cầu Tiền đề & Cấu hình

Khởi tạo môi trường ảo Python (`antigravity_env`) tại gốc dự án:

```powershell
antigravity_env\Scripts\python config/agents/agent_runner.py --check-config
```

---

## 🚀 3. Hướng dẫn Sử dụng

Chạy agent bằng CLI runner:

```powershell
antigravity_env\Scripts\python config/agents/agent_runner.py --agent api_contract_agent
```

Hoặc chạy trực tiếp entrypoint:

```powershell
antigravity_env\Scripts\python config/agents/api_contract_agent/run.py
```

---

## 🔄 4. Quy trình Hoạt động (Pipeline)

1. **Phase 1: Scan Backend**: Quét tất cả các RestControllers Java trong `BaseBackend` (`@GetMapping`, `@PostMapping`, `@RequestMapping`).
2. **Phase 2: Scan Frontend**: Quét các lệnh gọi API (`axios`, `fetch`, `$fetch`, `useFetch`) trong `nextjs-base` và `nuxtjs-base`.
3. **Phase 3: Compare Contract**: So sánh URL endpoint và HTTP method giữa BE và FE.
4. **Phase 4: Generate Report**: Xuất báo cáo danh sách API calls ở FE chưa tìm thấy ở BE tại `./report/api_contract_report.md`.

---

## 📄 5. Quy định Báo cáo (Report Output Rules)

- Output lưu tại: `./report/api_contract_report.md`
- Thư mục `./report` đã được ghi vào `.gitignore`.
