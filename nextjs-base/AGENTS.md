# Cấu hình CLAUDE cho Next.js Frontend (nextjs-base)

Dự án này là Frontend cơ sở được phát triển bằng **React 19** và **Next.js 16 (App Router)**.
Các AI Agent khi hoạt động trong thư mục `nextjs-base/` PHẢI tuân thủ các quy tắc sau đây.

## 1. Lệnh Thực thi Bắt Buộc (Commands)

Agent BẮT BUỘC sử dụng **pnpm** thay vì npm/yarn để tránh xung đột dependency:

- **Khởi chạy:** `pnpm dev`
- **Build:** `pnpm build`
- **Lint & Typecheck:** `pnpm lint`, `pnpm typecheck`
- **Cài đặt thư viện:** Bắt buộc xin phép người dùng trước khi gọi `pnpm add <package>`

## 2. Ngữ cảnh & Hệ thống Kiến thức (Context & Knowledge)

Dự án có hệ thống quy tắc chi tiết nằm trong thư mục `.claude/`. Tham chiếu tới:

### A. Quy tắc Kiến trúc (`.claude/rules/architecture.md`)

- Mô hình **Atomic Design**.
- Tái sử dụng tối đa (TailwindCSS utility classes, UI Components).
- Phân biệt rõ Client Components (`"use client"`) và Server Components. Tách biệt UI (Dumb) và Logic (Hooks).
- **Quản lý Màu sắc:** Các style về màu sắc (color) BẮT BUỘC phải được tách ra thành các biến màu và cấu hình riêng vào thư mục `assets/style/`. Không hardcode mã màu trực tiếp trong các component.

### B. Chuẩn Viết Code (`.claude/rules/coding-standards.md`)

- TypeScript rõ ràng (Hạn chế tối đa `any`, chỉ dùng khi thực sự cần thiết).
- **Zero Trust Security:** Base Validator ở Client-side trước khi fetch API.
- Comment, JSDoc, `@created_at`, `@author` đầy đủ.

### C. Cấu trúc Dự án (`.claude/context/project-structure.md`)

- Mapping logic của thư mục `app/`, `components/`, `lib/`, `hooks/`.

## 3. Quy trình Vận hành

- Tuân thủ file `CLAUDE.md` ở thư mục Root. Ưu tiên file này nếu có xung đột.
- Bất cứ UI Component, Icon hay Thư viện ngoài nào đều phải được sự đồng ý của User trước khi dùng.
