# Project Structure cho Next.js

Tài liệu này ánh xạ các thư mục cấu trúc trong `nextjs-base` để AI dễ dàng tìm vị trí file thích hợp.

## 1. Cấu trúc Thư mục Chính

```text
nextjs-base/
├── app/                  # Tầng Pages & Routing (Next.js App Router)
│   ├── layout.tsx        # Root layout, bọc các Provider
│   └── page.tsx          # Home page
├── components/           # Tầng UI Component (Atomic Design)
│   ├── atoms/
│   ├── molecules/
│   ├── organisms/
│   └── templates/
├── hooks/                # Tầng Logic (Custom React Hooks)
├── services/             # Lớp giao tiếp API (*.service.ts) gọi đến BFF Next.js
├── utils/                # Các hàm tiện ích, helpers dùng chung
├── lib/                  # Cấu hình thư viện ngoài, Base Validator logic
├── types/                # Khai báo TypeScript types / interfaces chung
├── public/               # Static assets (images, fonts)
├── styles/               # CSS/SCSS/Tailwind global configs
├── package.json          # Dependency
├── pnpm-workspace.yaml   # (Nếu có) Cấu hình Monorepo
├── CLAUDE.md             # File quy định tổng quát cho AI
├── .claude.json          # Cấu hình AI nội bộ
└── .claude/              # Ngữ cảnh nội bộ chuyên biệt cho AI
```

## 2. Tổ chức Component

- Mọi component nên được lưu trong `components/` theo mô hình Atomic Design.
- Component nào chỉ dùng riêng cho 1 Route thì có thể xem xét đặt trực tiếp chung với Route đó trong `app/` để dễ quản lý.

## 3. Hệ thống Rules & Agents

- Đọc `rules/architecture.md` trước khi tổ chức UI.
- Tham khảo ý kiến chuyên môn từ `agents/nextjs-frontend-expert.md`.
