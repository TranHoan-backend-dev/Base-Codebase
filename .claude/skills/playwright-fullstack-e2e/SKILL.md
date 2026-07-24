---
name: playwright-fullstack-e2e
description: Quy chuẩn lập Test Plan và viết E2E test cho Full-stack applications (Next.js/Nuxt.js) với Playwright, bao gồm kiểm thử GBAC, API Mocking và cấu trúc folder/file riêng biệt.
---

# Playwright Full-Stack E2E Testing Skill

Skill này quy định quy trình và chuẩn mực bắt buộc khi lên kế hoạch và triển khai End-to-End (E2E) testing bằng Playwright cho dự án Base-Codebase (bao gồm `nextjs-base` và `nuxtjs-base`).

---

## 1. QUY TRÌNH BẮT BUỘC: LẬP TEST PLAN TRƯỚC KHIN VIẾT CODE

**TẤT CẢ** các tác vụ viết E2E test PHẢI tuân thủ quy trình 2 bước sau:

### Bước 1: Trình bày Test Plan (Chờ người dùng phê duyệt)

Trước khi bắt đầu viết bất kỳ file code test nào, Agent PHẢI tạo hoặc trình bày một **Test Plan** gồm:

1. **Target Feature / Component:** Tên tính năng hoặc component được kiểm thử.
2. **Folder Structure:** Cấu trúc thư mục sẽ tạo.
3. **Test Suite Details:** Danh sách từng test case chi tiết:
   - **Test Case Name:** Tên file `.ts` tương ứng.
   - **Goal:** Mục tiêu kiểm thử của case này.
   - **Inputs / Setup:** Dữ liệu đầu vào, trạng thái đăng nhập (Role/Permissions GBAC), mock API data.
   - **Expected Outputs / Assertions:** Kết quả kỳ vọng (URL redirect, DOM elements, Toast message, HTTP status code).

> ⚠️ **CỔNG PHÊ DUYỆT:** Dừng lại và hỏi người dùng: *"Bạn có đồng ý với Test Plan này để tiến hành viết code test không?"*. CHỈ bắt đầu viết code khi nhận được sự đồng ý.

### Bước 2: Triển khai Code Test

Sau khi Test Plan được phê duyệt, tiến hành tạo code test theo đúng cấu trúc file/folder đã cam kết.

---

## 2. QUY CHUẨN CẤU TRÚC FILE VÀ THƯ MỤC

- **Folder theo Feature/Component:** Mỗi bộ Test Suite nhắm vào 1 tính năng hoặc 1 component PHẢI được đặt trong 1 thư mục riêng mang tên tính năng đó.
- **1 Test Case = 1 File `.ts`:** Mỗi file `.ts` chỉ chứa duy nhất 1 test case (hoặc 1 kịch bản đơn lẻ liên quan trực tiếp).

### Cấu trúc mẫu

```text
tests/
└── e2e/
    ├── auth-login/
    │   ├── login-success.spec.ts
    │   ├── login-invalid-credentials.spec.ts
    │   └── login-gbac-unauthorized.spec.ts
    └── user-management/
        ├── create-user-admin-role.spec.ts
        └── delete-user-forbidden.spec.ts
```

---

## 3. KIẾN TRÚC & QUY CHUẨN CODE E2E

### A. Page Object Model (POM)

- Mọi tương tác giao diện phức tạp PHẢI được encapsulate trong class POM đặt tại `tests/e2e/pages/` hoặc `tests/e2e/<feature>/pages/`.
- Không hardcode các selector phức tạp trực tiếp trong file `.spec.ts`.

### B. Kiểm thử Phân quyền GBAC (Group/Role/Permission-Based Access Control)

- Tạo các Auth State tái sử dụng (Storage State) cho các Role khác nhau (ví dụ: `admin.json`, `user.json`, `guest.json`).
- Kiểm thử chính xác hành vi hiển thị UI và chặn điều hướng theo quyền truy cập của User.

### C. API Mocking & Network Interception

- Ưu tiên mock các service bên ngoài hoặc dữ liệu backend tĩnh bằng `page.route()`.
- Xác minh dữ liệu payload gửi đi (Request Interception) và phản hồi từ server.

---

## 4. VÍ DỤ MINH HỌA FILE TEST CASE (`.spec.ts`)

```typescript
import { test, expect } from '@playwright/test';
import { LoginPage } from './pages/login.page';

test.describe('Auth Login - Success Case', () => {
  test('should login successfully with valid admin credentials', async ({ page }) => {
    const loginPage = new LoginPage(page);

    // Setup / Input
    await loginPage.goto();

    // Action
    await loginPage.login('admin@example.com', 'Password123!');

    // Assertions
    await expect(page).toHaveURL('/dashboard');
    await expect(loginPage.userAvatar).toBeVisible();
  });
});
```
