# Coding Standards for Next.js

## 1. TypeScript Nghiêm Ngặt

- Khuyến khích khai báo kiểu dữ liệu rõ ràng cho tất cả Props, Return Types, và Variables.
- **Hạn chế tối đa** sử dụng từ khóa `any`. Chỉ sử dụng trong những trường hợp bất khả kháng hoặc khi làm việc với thư viện ngoài không hỗ trợ type.

## 2. Zero Trust Security

- Không bao giờ tin tưởng dữ liệu từ người dùng (Input/Forms) hoặc từ URL (Params/Query).
- **Bắt buộc:** Phải xây dựng Base Validator ở Client-side (ví dụ dùng Zod hoặc Yup) để kiểm tra chặt chẽ dữ liệu trước khi thực hiện gọi API (fetch) tới Backend.

## 3. Quy chuẩn Bình luận (Comments & JSDoc)

- Các utils, hooks, hay component xử lý logic phức tạp phải có JSDoc đầy đủ.
- Bắt buộc phải có các metadata: `@created_at`, `@author`, và `@references` (nếu tham khảo code từ bên ngoài).

```typescript
/**
 * Hook tùy chỉnh để quản lý state của Giỏ hàng.
 * Tính toán tổng giá trị tiền dựa trên số lượng item.
 *
 * @author [Tên/AI]
 * @created_at [Ngày tháng]
 */
export const useCart = () => { ... }
```

## 4. Quy định Thư viện

- Không tự ý chạy lệnh `pnpm install` một thư viện UI/Icon/State Management mới mà chưa xin phép User.

## 5. Giao tiếp API (API Communication)

- Khi frontend cần gọi API về Backend (BE), bên cạnh việc định nghĩa và gọi trực tiếp tại các file `*.service.ts`, **bắt buộc phải đi qua lớp API trung gian của Next.js** (BFF - Backend For Frontend).
- Xây dựng các Route Handlers trong thư mục `app/api/.../route.ts` sử dụng `NextRequest` và `NextResponse` để nhận request từ Client, sau đó mới forward request xuống Backend thực sự. Điều này giúp bảo mật thông tin (ẩn token, API key) và dễ dàng format lại dữ liệu.
