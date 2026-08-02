# 🤖 Danh mục Tài liệu Đặc tả AI Agents System (Antigravity SDK)

> **Metadata**:  
>
> - `@created_at`: 2026-07-31  
> - `@author`: Base-Codebase AI Team  
> - `@references`: `config/agents/`

Tài liệu chỉ mục tổng hợp các file đặc tả chi tiết cho từng **AI Agent** trong hệ thống `Base-Codebase`.

---

## 📚 Danh sách Tài liệu Đặc tả Chi tiết

| STT | AI Agent Name | Mục đích & Chức năng chính | Tài liệu đặc tả chi tiết |
| --- | --- | --- | --- |
| 1 | **Java Unit Test Agent** | Tự động chạy và fix Unit Tests trên dự án Java (be/SpringBoot) | 📄 [ai-agent-testing.md](file://docs/ai_agents/testing/ai-agent-testing.md) |
| 2 | **API Contract Checker** | Kiểm tra sự khớp nối REST Endpoints & API Calls giữa BE và FE | 📄 [api-contract-checker.md](file://docs/ai_agents/api-contract-checker.md) |
| 3 | **FE Build Analyzer** | Tự động chạy build FE (Next.js/Nuxt.js) và phân tích lỗi `tsc`/`eslint` | 📄 [fe-build-analyzer.md](file://docs/ai_agents/fe-build-analyzer.md) |
| 4 | **Dependency Audit** | Quét lỗ hổng bảo mật CVE và thư viện lỗi thời (`pom.xml`, `package.json`) | 📄 [dependency-audit.md](file://docs/ai_agents/dependency-audit.md) |
| 5 | **Code Sync Agent** | Đồng bộ DTO/Entity từ Java BE sang TypeScript interfaces/types cho FE | 📄 [code-sync.md](file://docs/ai_agents/code-sync.md) |
| 6 | **Changelog Generator** | Phân tích Git history và xuất file Release Notes chuẩn Markdown | 📄 [changelog-generator.md](file://docs/ai_agents/changelog-generator.md) |
| 7 | **Security Scan Agent** | Quét hardcoded secrets, rò rỉ `.env`, CORS wildcard và CSP headers | 📄 [security-scan.md](file:/docs/ai_agents/security-scan.md) |

---

## ⚡ Quick Start - Cách thực thi bất kỳ Agent nào

Chạy script runner duy nhất từ root repository:

```powershell
antigravity_env\Scripts\python config/agents/agent_runner.py --agent <tên_agent>
```

Ví dụ:

```powershell
antigravity_env\Scripts\python config/agents/agent_runner.py --agent security_scan_agent
```
