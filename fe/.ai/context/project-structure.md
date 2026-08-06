# Project Structure cho Frontend

Tài liệu này ánh xạ các thư mục cấu trúc trong các dự án Frontend của `fe/` để AI dễ dàng tìm vị trí file thích hợp.

## 1. Cấu trúc Thư mục Chính

```text
fe/
├── nextjs-base/          # Dự án Next.js Frontend
│   ├── app/              # Tầng Pages & Routing (App Router)
│   ├── components/       # Tầng UI Component (Atomic Design)
│   ├── hooks/            # Tầng Logic (React Custom Hooks)
│   ├── services/         # Lớp giao tiếp API
│   ├── utils/            # Các hàm tiện ích, helpers
│   ├── package.json      # Dependency
│   └── tsconfig.json
├── nuxtjs-base/          # Dự án Nuxt.js Frontend
│   ├── pages/            # Tầng Pages & Routing (File-based Routing)
│   ├── components/       # Tầng UI Component (Atomic Design)
│   ├── composables/      # Tầng Logic (Auto-imported composables)
│   ├── server/           # API routes nội bộ (Nitro engine - BFF)
│   ├── package.json      # Dependency
│   └── nuxt.config.ts
├── CLAUDE.md             # File quy định tổng quát cho AI
├── .claude.json          # Cấu hình lệnh được chạy nội bộ
├── .mcp.json             # File cấu hình lấy docs cho MCP
└── .ai/                  # Chứa toàn bộ Context, Rules và Agents chuyên môn
```

## 2. Tổ chức Components (Atomic Design)

Mọi components của cả 2 dự án nên được đặt trong thư mục `components/` tương ứng và tuân theo mô hình Atomic Design:

- `components/atoms/`
- `components/molecules/`
- `components/organisms/`
- `components/templates/`

## 3. Hệ thống Rules & Agents

- Đọc `rules/architecture.md` trước khi tổ chức UI.
- Tham khảo ý kiến chuyên môn từ `agents/nextjs-frontend-expert.md` cho Next.js hoặc `agents/nuxtjs-frontend-expert.md` cho Nuxt.js.
