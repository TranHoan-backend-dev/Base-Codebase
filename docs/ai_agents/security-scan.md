# 🛡️ Hướng dẫn Sử dụng Security Scan Agent

> **Metadata**:  
> - `@created_at`: 2026-07-31  
> - `@author`: Base-Codebase AI Team  
> - `@references`: `config/agents/security_scan_agent/`

Agent này thực hiện quét toàn bộ repository để bảo vệ an ninh thông tin theo mô hình Defense in Depth (Zero Trust): quét hardcoded secrets/API keys, kiểm tra sự cố rò rỉ file `.env`, kiểm tra cấu hình CORS ở Backend và Content-Security-Policy (CSP) ở Frontend.

---

## 🧠 1. Kiến trúc & Cấu trúc Thư mục

```text
Base-Codebase/
├── config/
│   └── agents/
│       └── security_scan_agent/
│           ├── __init__.py
│           ├── agent.py          # SecurityScanAgent Controller
│           ├── tools.py          # Secrets scanner, git status checker, CORS/CSP parsers
│           └── run.py            # CLI Entrypoint
└── report/
    └── security_scan_report.md   # Báo cáo kết quả quét bảo mật monorepo
```

---

## 🚀 2. Hướng dẫn Sử dụng

Thực thi agent qua runner:

```powershell
antigravity_env\Scripts\python config/agents/agent_runner.py --agent security_scan_agent
```

---

## 🔄 3. Quy trình Hoạt động (Pipeline)

1. **Phase 1: Secret Scanning**: Regex scanner quét qua mã nguồn tìm API Keys (Google, OpenAI...), Passwords, Private Keys.
2. **Phase 2: Git Leak Check**: Kiểm tra file `.env` có bị Git theo dõi hoặc commit không (`git ls-files`).
3. **Phase 3: CORS & CSP Audit**: Kiểm tra `@CrossOrigin(origins = "*")` ở Spring Boot và CSP headers ở Next.js/Nuxt.js.
4. **Phase 4: Export Report**: Xuất báo cáo tổng hợp tại `./report/security_scan_report.md`.
