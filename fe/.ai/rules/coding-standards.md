# Coding Standards for Frontend

Đây là tài liệu quy định về chuẩn mực viết mã áp dụng chung cho dự án Frontend. AI phải kiểm tra code dựa trên các quy tắc này.

## 1. TypeScript Nghiêm Ngặt

- Khai báo kiểu dữ liệu rõ ràng cho tất cả Props, Return Types, và Variables (đặc biệt trong `<script setup lang="ts">` hoặc React TSX).
- **Hạn chế tối đa** sử dụng từ khóa `any`. Chỉ dùng khi làm việc với thư viện ngoài không hỗ trợ kiểu dữ liệu.

## 2. Zero Trust Security

- Không bao giờ tin tưởng dữ liệu nhập vào từ form hay query URL.
- **Bắt buộc:** Phải xây dựng lớp Base Validator ở Client-side (như Zod hoặc VeeValidate/Yup) để kiểm tra chặt chẽ dữ liệu trước khi thực hiện gọi API tới Backend.

## 3. Quy chuẩn Bình luận (Comments & JSDoc)

- Các utils, hooks, composables hoặc component xử lý logic phức tạp phải có JSDoc đầy đủ.
- Bắt buộc có các metadata: `@created_at`, `@author`, và `@references` (nếu tham khảo code ngoài).

```typescript
/**
 * Hook/Composable quản lý thông tin giỏ hàng.
 *
 * @author [Tên tác giả]
 * @created_at [Ngày tháng]
 */
```

## 4. Giao tiếp API qua BFF (Backend For Frontend)

- Khi Frontend cần gọi API về Backend thực sự, **bắt buộc phải đi qua lớp API trung gian (BFF)**:
  - **Next.js:** Xây dựng Route Handlers trong thư mục `app/api/.../route.ts` sử dụng `NextRequest` và `NextResponse`.
  - **Nuxt.js:** Sử dụng Nitro engine thông qua các handler trong thư mục `server/api/` (sử dụng `defineEventHandler`).
- Mục đích: Ẩn Access Tokens, API Keys quan trọng khỏi Browser của người dùng, thực hiện tiền xử lý dữ liệu và định dạng lại payload trước khi trả về.

## 5. Quy định Thư viện

- Không tự ý cài đặt thêm thư viện ngoài (UI, Icons, State management) mà chưa được sự phê duyệt của người dùng.
