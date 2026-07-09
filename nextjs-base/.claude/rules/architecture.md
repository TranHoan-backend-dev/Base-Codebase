# Architecture Rules for Next.js

## 1. Atomic Design

Toàn bộ UI Component trong dự án phải được thiết kế theo tư tưởng Atomic Design để tái sử dụng tối đa:

- **Atoms:** Các thành phần nhỏ nhất không thể phân chia (Button, Input, Icon).
- **Molecules:** Tập hợp các Atoms (Search bar = Input + Button).
- **Organisms:** Thành phần phức tạp gồm Molecules và Atoms (Header, Sidebar).
- **Templates:** Layout bao quanh không chứa logic nghiệp vụ.
- **Pages:** Lớp hiển thị cao nhất, chứa logic nạp dữ liệu (data fetching). Tương ứng với các file `page.tsx` nằm trong thư mục `app/` của Next.js (App Router).

## 2. Server & Client Components

- **Mặc định là Server Component:** Để tối ưu SEO và tốc độ tải trang, tuyệt đối không được thêm `"use client"` trừ khi thực sự cần.
- **Khi nào dùng `"use client"`?** Chỉ dùng khi component cần tương tác với người dùng (onClick, onChange) hoặc sử dụng các React Hooks (`useState`, `useEffect`).

## 3. Separation of UI and Logic

- Dumb Components (Presentational): Chỉ nhận props và hiển thị.
- Custom Hooks: Tách toàn bộ logic xử lý, state management, fetch data ra các file `useSomething.ts` riêng biệt.

## 4. Tái Sử Dụng Tối Đa

- Ưu tiên sử dụng TailwindCSS utility classes và các component từ HeroUI v3.2.1.
- Gom các classes Tailwind lặp đi lặp lại thành các thành phần dùng chung, tránh rác mã nguồn.
