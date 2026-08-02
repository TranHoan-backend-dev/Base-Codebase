# 📦 Hướng dẫn Sử dụng Dependency Audit Agent

> **Metadata**:  
>
> - `@created_at`: 2026-07-31  
> - `@author`: Base-Codebase AI Team  
> - `@references`: `config/agents/dep_audit_agent/`

Agent này thực hiện quét toàn bộ dependency trong `pom.xml` (Java) và `package.json` (Next.js, Nuxt.js) để phát hiện lỗ hổng bảo mật (CVE), dependency lỗi thời và dependency không sử dụng.

---

## 🧠 1. Kiến trúc & Cấu trúc Thư mục

```text
Base-Codebase/
├── config/
│   └── agents/
│       └── dep_audit_agent/
│           ├── __init__.py
│           ├── agent.py          # DepAuditAgent Controller
│           ├── tools.py          # npm audit & mvn dependency:analyze runner
│           └── run.py            # CLI Entrypoint
└── report/
    └── dep_audit_report.md       # Báo cáo tổng hợp lỗ hổng bảo mật & CVE
```

---

## 🚀 2. Hướng dẫn Sử dụng

Chạy agent bằng lệnh CLI:

```powershell
antigravity_env\Scripts\python config/agents/agent_runner.py --agent dep_audit_agent
```

---

## 🔄 3. Quy trình Hoạt động (Pipeline)

1. **Phase 1: NPM Audit**: Chạy `npm audit --json` trên `fe/nextjs-base` và `fe/nuxtjs-base`.
2. **Phase 2: Maven Dependency Analysis**: Chạy `mvn dependency:analyze` trên `be/SpringBoot`.
3. **Phase 3: CVE Categorization**: Phân loại mức độ nghiêm trọng (Critical, High, Moderate, Low).
4. **Phase 4: Export Report**: Xuất báo cáo tại `./report/dep_audit_report.md`.
