# Architecture Rules for Nuxt.js

## 1. Atomic Design

Toàn bộ UI Component trong dự án phải được thiết kế theo tư tưởng Atomic Design để tái sử dụng tối đa:

- **Atoms:** Các thành phần cơ bản (Button, Input, Icon).
- **Molecules:** Tập hợp các Atoms (Search form).
- **Organisms:** Khối phức tạp (Header, Footer).
- **Templates:** Cấu trúc trang.
- **Pages:** Nơi lấy dữ liệu (useAsyncData/useFetch) và truyền xuống các component con.

## 2. Composition API

- Sử dụng triệt để `<script setup>` trong mọi file `.vue`.
- Tận dụng hệ thống Auto-imports của Nuxt để không phải khai báo lại các composables hay components.

## 3. Separation of UI and Logic

- Tách rời các logic xử lý phức tạp ra thư mục `composables/` (ví dụ `useAuth.ts`, `useProduct.ts`).
- File `.vue` chỉ nên tập trung vào việc hiển thị và hứng sự kiện.

## 4. Tái Sử Dụng Tối Đa

- Ưu tiên sử dụng các Component từ Nuxt UI.
- Đối với Custom Styles, sử dụng các SCSS Mixin và Variables dùng chung. Không viết lặp lại các đoạn mã SCSS giống nhau ở nhiều file.
