# Project Structure cho Nuxt.js

Tài liệu này ánh xạ các thư mục cấu trúc trong `nuxtjs-base` để AI dễ dàng tìm vị trí file thích hợp.

## 1. Cấu trúc Thư mục Chính

```text
nuxtjs-base/
├── app.vue               # Entry point
├── pages/                # Tầng Pages & File-based Routing
├── components/           # Tầng UI Component (Atomic Design)
│   ├── atoms/
│   ├── molecules/
│   ├── organisms/
│   └── templates/
├── composables/          # Tầng Logic (Auto-imported custom composition functions)
├── services/             # Lớp giao tiếp API (*.service.ts)
├── utils/                # Utilities, Helpers, Base Validator logic (Auto-imported)
├── types/                # Khai báo TypeScript types / interfaces chung
├── public/               # Static assets (không đi qua webpack/vite)
├── assets/               # SCSS, images đi qua webpack/vite
├── server/               # API routes nội bộ của Nuxt (nếu có dùng)
├── nuxt.config.ts        # File cấu hình trung tâm
├── package.json          # Dependency
├── CLAUDE.md             # File quy định tổng quát cho AI
├── .claude.json          # Cấu hình AI nội bộ
└── .claude/              # Ngữ cảnh nội bộ chuyên biệt cho AI
```

## 2. Auto-imports

- Các thư mục `components/`, `composables/`, và `utils/` được Nuxt tự động scan và import. Do đó, AI KHÔNG CẦN và KHÔNG NÊN viết lệnh `import` cho các thành phần nằm trong này khi gọi từ `.vue` file.

## 3. Hệ thống Rules & Agents

- Tuân thủ thiết kế UI theo thư mục `.claude/rules/architecture.md`.
- Hướng tiếp cận Framework tuân theo `.claude/agents/nuxtjs-frontend-expert.md`.
