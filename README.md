# Base-Codebase

Repository chứa các dự án cơ sở (Base Repositories) đóng vai trò làm nền tảng chuẩn hóa cho cả Backend và Frontend, giúp tái sử dụng mã nguồn, đồng bộ kiến trúc và chuẩn hóa quy trình phát triển cho các dự án con.

## 📁 Cấu trúc Dự án

- **`BaseBackend/`**: Dự án Backend cơ sở xây dựng trên Java & Spring Boot (cung cấp core logic, authentication, cấu hình DB, security, v.v.).
- **`nextjs-base/`**: Dự án Frontend cơ sở xây dựng trên React & Next.js (chứa các UI component, layout và utils dùng chung).
- **`nuxtjs-base/`**: Dự án Frontend cơ sở xây dựng trên Vue & Nuxt.js (chứa các UI component, layout và utils dùng chung).

## 🚀 Khởi chạy Nhanh

### Yêu cầu môi trường

- Node.js (v18+) & pnpm
- Java JDK 17+ (cho Backend)

### Cài đặt & Chạy ứng dụng

1. **Cài đặt các gói phụ thuộc Frontend (Monorepo):**

   ```bash
   pnpm install
   ```

2. **Khởi chạy Next.js Base:**

   ```bash
   cd nextjs-base
   pnpm dev
   ```

3. **Khởi chạy Nuxt.js Base:**

   ```bash
   cd nuxtjs-base
   pnpm dev
   ```

4. **Khởi chạy Backend (Spring Boot):**

   ```bash
   cd BaseBackend
   ./gradlew bootRun
   ```

---
*Lưu ý: Chi tiết quy định phát triển và đóng góp mã nguồn xem thêm tại [AGENTS.md](./AGENTS.md).*
