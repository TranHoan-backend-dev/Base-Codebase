# Cấu hình CLAUDE / GEMINI cho Base Frontend

Dự án này là Frontend cơ sở được phát triển bằng 2 công nghệ chính:

1. **Next.js 16+ & HeroUI** (nằm trong `nextjs-base/`)
2. **Vue 3 & Nuxt 4** (nằm trong `nuxtjs-base/`)

Các AI Agent khi hoạt động trong thư mục `fe/` PHẢI tuân thủ các quy tắc sau đây.

## 1. Lệnh Thực thi Bắt Buộc (Commands)

Agent BẮT BUỘC sử dụng **pnpm** thay vì npm/yarn:

- **Chạy dev:** `pnpm --filter nextjs-base dev` hoặc `pnpm --filter nuxtjs-base dev`
- **Build:** `pnpm --filter nextjs-base build` hoặc `pnpm --filter nuxtjs-base build`
- **Lint & Typecheck:** `pnpm lint`, `pnpm typecheck`
- **Cài đặt thư viện:** Bắt buộc xin phép người dùng trước khi gọi `pnpm add <package>`

## 2. Ngữ cảnh & Hệ thống Kiến thức (Context & Knowledge)

Base Frontend có hệ thống quy tắc chi tiết nằm trong thư mục `.ai/`. Trước khi bắt tay vào thiết kế hay code, AI phải tham chiếu tới:

### A. Quy tắc Kiến trúc (`.ai/rules/architecture.md`)

- Mô hình **Atomic Design** cho cả Next.js và Nuxt.js.
- Phân tách UI và Logic (Hooks/Composables).
- Hướng tiếp cận Server-First component cho Next.js, and SSR optimization cho Nuxt.js.

### B. Chuẩn Viết Code (`.ai/rules/coding-standards.md`)

- TypeScript nghiêm ngặt, hạn chế `any`.
- **Zero Trust Security:** Lớp Base Validator ở Client-side trước khi fetch API.
- Bắt buộc đi qua lớp API trung gian BFF (Next.js Route Handlers hoặc Nitro Server API).
- Đa ngôn ngữ & i18n: Không hardcode text, đưa toàn bộ vào file dictionary JSON tương ứng.

### C. Cấu trúc Dự án (`.ai/context/project-structure.md`)

- Ánh xạ cấu trúc thư mục của Next.js và Nuxt.js.

### D. Hệ thống Agent Chuyên gia (`.ai/agents/`)

- Tham khảo ý kiến chuyên môn của `nextjs-frontend-expert.md` hoặc `nuxtjs-frontend-expert.md` tương ứng.

## 3. Quy trình Vận hành

- Tuân thủ file `CLAUDE.md` ở thư mục Root. Ưu tiên file này nếu có xung đột.
- Bất cứ UI Component, Icon hay Thư viện ngoài nào đều phải được sự đồng ý của User trước khi dùng.
