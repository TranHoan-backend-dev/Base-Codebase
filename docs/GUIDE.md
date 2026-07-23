# Hướng dẫn Sử dụng & Khởi tạo Dự án từ Base Repository

Tài liệu này hướng dẫn chi tiết cách khởi tạo, phát triển các dự án con (Child Projects) và quy trình đóng góp mã nguồn (Contribution Workflow) dựa trên kho lưu trữ cơ sở **Base-Codebase** cho cả Frontend và Backend.

---

## 1. Mô hình Vận hành & Kế thừa Mã nguồn

### A. Frontend Sub-Repos (`nextjs-base` / `nuxtjs-base`)

* **Kiến trúc Workspace**: Áp dụng mô hình **pnpm workspace** để chia sẻ thành phần UI (`components/`, `utils/`) giữa Base Repo và ứng dụng con.
* **Cơ chế Dev song song (Hot Reload)**: Nhờ liên kết Symlink của `pnpm workspace`, các chỉnh sửa tại component dùng chung sẽ lập tức phản hồi (HMR) trên dự án con.
* **Quy trình đóng góp (Contribution Workflow)**: Chi tiết quy định và các bước đóng góp mã nguồn (Pull Request) cho Base Repo xem tại tài liệu [docs/CONTRIBUTING.md](file:///d:/Du_an_ca_nhan/Base-Codebase/docs/CONTRIBUTING.md).

### B. Backend Sub-Repo (`BaseBackend`)

* **Kiến trúc Maven / Gradle Artifact**: Dự án `BaseBackend` (Java / Spring Boot) được đóng gói thành các thư viện cơ sở (`base-backend-core.jar`).
* **Kế thừa Dependency**: Các dự án con Backend khai báo phụ thuộc vào `BaseBackend` thông qua Maven Local hoặc Private Maven Repository:

  ```kotlin
  // build.gradle.kts của dự án con
  dependencies {
      implementation("com.base:base-backend-core:1.0.0")
  }
  ```

* **Tính năng kế thừa**: Tự động thừa hưởng hệ thống Authentication, Security Filters, Base Entity, Exception Handler, Event Bus và các cấu hình Database chung.

---

## 2. Hướng dẫn Khởi tạo Dự án Con (Child Project)

### A. Khởi tạo Dự án Frontend Con

1. Sao chép mẫu dự án cơ sở từ `nextjs-base` hoặc `nuxtjs-base`:

   ```bash
   cp -r nextjs-base apps/my-frontend-app
   ```

2. Cấu hình biến môi trường:

   ```bash
   cd apps/my-frontend-app
   cp .env.example .env
   ```

3. Cài đặt phụ thuộc & khởi chạy:

   ```bash
   pnpm install
   pnpm dev
   ```

### B. Khởi tạo Dự án Backend Con

1. Tạo dự án Spring Boot mới hoặc kế thừa từ `BaseBackend`.
2. Khai báo dependency `base-backend-core` trong `build.gradle.kts` hoặc `pom.xml`.
3. Tạo file `application.yaml` định nghĩa các thông số cấu hình riêng (Database URL, JWT Secret, v.v.).

---

## 3. Danh mục Tài liệu Đặc tả Component & Backend Architecture

### A. Backend Architecture & Specs (`BaseBackend`)

* [BaseBackend - Tổng quan Kiến trúc & Hướng dẫn Core](file:///d:/Du_an_ca_nhan/Base-Codebase/BaseBackend/src/main/resources/docs/README.md)
* *Lưu ý: Mọi tính năng Backend mới bắt buộc phải bổ sung tài liệu đặc tả tại `BaseBackend/src/main/resources/docs/.specify`.*

### B. Next.js Base Components (`nextjs-base`)

* [SweButton - Đặc tả Component](file:///d:/Du_an_ca_nhan/Base-Codebase/nextjs-base/docs/components/swe-button.md)

### C. Nuxt.js Base Components (`nuxtjs-base`)

* [SweButton - Đặc tả Component](file:///d:/Du_an_ca_nhan/Base-Codebase/nuxtjs-base/docs/components/swe-button.md)
* [SweFormLayout - Đặc tả Component](file:///d:/Du_an_ca_nhan/Base-Codebase/nuxtjs-base/docs/components/swe-form-layout.md)
* [BasePopup - Đặc tả Component](file:///d:/Du_an_ca_nhan/Base-Codebase/nuxtjs-base/docs/components/base-popup.md)
* [DynamicGrid - Đặc tả Component](file:///d:/Du_an_ca_nhan/Base-Codebase/nuxtjs-base/docs/components/dynamic-grid.md)
* [GridPagination - Đặc tả Component](file:///d:/Du_an_ca_nhan/Base-Codebase/nuxtjs-base/docs/components/grid-pagination.md)

### D. Testing & Automation

* [Playwright E2E Testing Guide - Cấu hình & Hướng dẫn viết test E2E](file:///d:/Du_an_ca_nhan/Base-Codebase/docs/playwright.md)
