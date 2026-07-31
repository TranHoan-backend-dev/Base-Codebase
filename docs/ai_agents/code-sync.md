# 🔄 Hướng dẫn Sử dụng Code Sync Agent (BE ↔ FE)

> **Metadata**:  
>
> - `@created_at`: 2026-07-31  
> - `@author`: Base-Codebase AI Team  
> - `@references`: `config/agents/code_sync_agent/`

Agent này đọc các Java DTO/Entity từ Backend (`be/BaseBackend`) và tự động sinh ra TypeScript interfaces/types tương ứng cho `fe/nextjs-base` và `fe/nuxtjs-base`.

---

## 🧠 1. Kiến trúc & Cấu trúc Thư mục

```text
Base-Codebase/
├── config/
│   └── agents/
│       └── code_sync_agent/
│           ├── __init__.py
│           ├── agent.py          # CodeSyncAgent Controller
│           ├── tools.py          # Java to TS type mapper & parser
│           └── run.py            # CLI Entrypoint
└── report/
    └── generated_types_sync.ts   # File preview các type đã sinh
```

---

## 🚀 2. Hướng dẫn Sử dụng

### Dry-run Mode (Chỉ tạo file preview trong `./report/`)

```powershell
antigravity_env\Scripts\python config/agents/agent_runner.py --agent code_sync_agent
```

### Write Mode (Ghi trực tiếp file `.d.ts` vào Frontend)

```powershell
antigravity_env\Scripts\python config/agents/agent_runner.py --agent code_sync_agent --write
```

---

## 🔄 3. Quy trình Hoạt động (Pipeline)

1. **Phase 1: Parse Java DTOs**: Quét các file `*DTO.java`, `*Request.java`, `*Response.java` trong `be/BaseBackend`.
2. **Phase 2: Type Mapping**: Chuyển đổi kiểu dữ liệu Java (`String`, `Long`, `Boolean`, `LocalDateTime`...) sang TypeScript (`string`, `number`, `boolean`...).
3. **Phase 3: Code Generation**: Sinh mã nguồn TypeScript interfaces.
4. **Phase 4: Output / Sync**: Lưu bản preview tại `./report/generated_types_sync.ts` hoặc ghi vào `fe/nextjs-base/types/backend-api.d.ts` và `fe/nuxtjs-base/types/backend-api.d.ts`.
