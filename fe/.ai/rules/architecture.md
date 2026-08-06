# Architecture Rules for Frontend

Kiến trúc phần Frontend yêu cầu một thiết kế giao diện hiện đại, dễ bảo trì và tối ưu hóa hiệu năng tái sử dụng cho cả Next.js và Nuxt.js.

## 1. Atomic Design

Toàn bộ UI Component trong cả hai dự án phải được thiết kế theo tư tưởng Atomic Design:

- **Atoms:** Các thành phần nhỏ nhất không thể phân chia (Button, Input, Icon, Typography).
- **Molecules:** Tập hợp các Atoms (Search bar = Input + Button).
- **Organisms:** Thành phần phức tạp gồm Molecules và Atoms (Header, Sidebar, Forms).
- **Templates:** Layout bao quanh định hình cấu trúc hiển thị, không chứa logic nghiệp vụ.
- **Pages:** Lớp hiển thị cao nhất, chịu trách nhiệm lấy dữ liệu (Data Fetching) và truyền xuống các component con.

## 2. Server & Client Side Rendering

- **Next.js (App Router):** Mặc định là `Server Components` để tối ưu SEO. Chỉ thêm `"use client"` khi thực sự cần quản lý state, hook React hoặc hứng sự kiện click/change.
- **Nuxt.js (Nuxt 4):** Sử dụng các composables tích hợp như `useFetch` và `useAsyncData` để gọi API từ Backend và hỗ trợ Server-Side Rendering (SSR) tối ưu.

## 3. Separation of UI and Logic

- **Dumb Components (Presentational):** Chỉ chịu trách nhiệm render giao diện từ props và ném ra các sự kiện (emit / events).
- **Logic Extraction:** Tách rời toàn bộ logic nghiệp vụ phức tạp ra:
  - **Next.js:** Các Custom Hooks (`useSomething.ts`).
  - **Nuxt.js:** Các Composables (`useSomething.ts` trong thư mục `composables/`).

## 4. Tái Sử Dụng Giao Diện & Styles

- **Next.js:** Ưu tiên sử dụng TailwindCSS utility classes và components từ HeroUI v3.2.1.
- **Nuxt.js:** Ưu tiên sử dụng Nuxt UI components. Đối với custom style, bắt buộc sử dụng các SCSS Mixins và Variables dùng chung trong thư mục `assets/style/`.
- Không hardcode mã màu trực tiếp trong các component.
