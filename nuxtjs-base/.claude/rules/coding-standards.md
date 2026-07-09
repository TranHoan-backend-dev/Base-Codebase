# Coding Standards for Nuxt.js

## 1. TypeScript Nghiêm Ngặt

- Nuxt 4 hỗ trợ TypeScript cực mạnh. Cố gắng thêm type cho logic trong `<script setup lang="ts">` và trong composables.
- **Hạn chế tối đa** sử dụng từ khóa `any`. Chỉ sử dụng trong những trường hợp bất khả kháng hoặc khi làm việc với thư viện ngoài không hỗ trợ type.

## 2. Zero Trust Security

- Không bao giờ tin tưởng dữ liệu nhập vào từ form hay từ query URL.
- **Bắt buộc:** Phải có Base Validator ở Client-side (ví dụ sử dụng VeeValidate kết hợp Zod/Yup) để validate toàn bộ dữ liệu trước khi gửi request tới Backend.

## 3. Quy chuẩn Bình luận (Comments & JSDoc)

- Bất kỳ composable nào (`use...`) hoặc component lớn đều phải được viết JSDoc.
- Bắt buộc có `@created_at`, `@author`, `@references`.

```typescript
/**
 * Composable xử lý xác thực thông qua hệ thống GBAC.
 * 
 * @author [Tên/AI]
 * @created_at [Ngày tháng]
 */
export const useAuth = () => { ... }
```

## 4. Quy định Thư viện

- Mọi quyết định thêm package (Nuxt Modules, UI library, Icon set) đều phải có sự phê duyệt trước từ User.

## 5. Giao tiếp API (API Communication - BFF)

- Khi frontend cần gọi API về Backend (BE), **bắt buộc phải đi qua lớp API trung gian của Nuxt.js** (Sử dụng Nitro engine trong thư mục `server/api/`).
- Quy trình: Lớp `*.service.ts` ở Client sẽ gọi đến các endpoint `/api/...` nội bộ của Nuxt (ví dụ bằng `$fetch` hoặc `useFetch`). Sau đó, các file xử lý trong `server/api/...` (hứng `defineEventHandler`) mới làm nhiệm vụ forward request xuống Backend thực sự.
- Mục đích: Ẩn đi các Access Token, API Key quan trọng khỏi trình duyệt của người dùng và dễ dàng tiền xử lý/format lại dữ liệu trước khi trả về Client.
