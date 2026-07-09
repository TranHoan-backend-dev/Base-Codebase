# Access Control & GBAC (Group-Based Access Control)

Tài liệu này định nghĩa các nguyên tắc cốt lõi về phân quyền, xác thực và bảo mật (Zero Trust) trên toàn bộ hệ thống Monorepo.

## 1. Tầng Backend (Spring Boot)

- **Nguyên tắc cốt lõi:** Backend là pháo đài cuối cùng. Không tin tưởng bất cứ request nào dù nó đến từ BFF.
- **GBAC Enforcement:** Tất cả các endpoint (ngoại trừ whitelist như Login/Register) phải sử dụng annotation phân quyền hợp lệ (ví dụ: `@PreAuthorize`).
- **Data Scoping:** Bắt buộc filter dữ liệu theo Tenant hoặc GroupID trực tiếp trong Query/Repository. Một User thuộc Group A tuyệt đối không thể fetch Data của Group B bằng cách thay đổi ID trên URL.
- **Base Validator:** Mọi DTO đều phải được validate chặt chẽ (độ dài, ký tự đặc biệt, SQL Injection).

## 2. Tầng BFF (Next.js / Nuxt.js API)

- **Session Management:** Client (Trình duyệt) không bao giờ được nắm giữ `Access Token` trực tiếp. Tầng BFF sẽ lưu Token dưới dạng `HttpOnly`, `Secure`, `SameSite` Cookie.
- **Token Propagation:** Khi có request từ Client, BFF sẽ đọc HttpOnly Cookie, giải mã, lấy Access Token và đính kèm vào Header `Authorization: Bearer ...` trước khi gọi xuống Backend Spring Boot.
- **Rate Limiting & Throttling:** BFF đóng vai trò là lá chắn chống Spam Request/DDoS trước khi request chạm vào Backend.

## 3. Tầng Frontend (Client-Side UI)

- **Route Guards:** Sử dụng Middleware (`middleware.ts` trong Next.js hoặc `routeRules`/`middleware/` trong Nuxt.js) để kiểm tra Session và chặn điều hướng tới các trang Private nếu chưa đăng nhập.
- **UI Element Visibility:** Các hành động nhạy cảm (Sửa, Xóa, Xét duyệt) trên giao diện phải kiểm tra JWT Payload hoặc User Permissions Context. Nếu User không có quyền, tự động ẩn hoặc vô hiệu hóa (disabled) nút bấm đó.
- Không bao giờ dựa vào việc ẩn UI để bảo mật. (Ví dụ: dù ẩn nút "Xóa", nhưng Backend vẫn phải kiểm tra quyền nếu có ai đó dùng Postman gọi thẳng API Xóa).
