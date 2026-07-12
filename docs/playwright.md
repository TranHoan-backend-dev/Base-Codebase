# Hướng dẫn sử dụng Playwright (Shared Configuration)

- **@created_at:** 2026-07-11
- **@author:** Antigravity
- **@references:** [Playwright Docs](https://playwright.dev/)

Tài liệu này hướng dẫn cách sử dụng cấu hình Playwright dùng chung giữa các sub-repositories frontend trong dự án.

---

## 1. Cấu trúc cấu hình (Architecture)

Hệ thống sử dụng cấu hình kế thừa (Inherited Configuration) để tối ưu hóa việc tái sử dụng code:

1. **Cấu hình cơ sở (Root Config):** Định nghĩa tại [playwright.config.base.ts](file:///d:/Du_an_ca_nhan/Base-Codebase/playwright.config.base.ts) chứa các thiết lập chung như:
   - Chạy parallel: `fullyParallel: true`
   - Browsers test: Chromium, Firefox, WebKit
   - Reporter mặc định: `html`
   - Số lần thử lại (retries) và workers tùy biến theo môi trường CI.

2. **Cấu hình cụ thể từng dự án (Sub-repo Config):**
   - **Next.js:** [nextjs-base/playwright.config.ts](file:///d:/Du_an_ca_nhan/Base-Codebase/nextjs-base/playwright.config.ts)
   - **Nuxt.js:** [nuxtjs-base/playwright.config.ts](file:///d:/Du_an_ca_nhan/Base-Codebase/nuxtjs-base/playwright.config.ts)
   - Cả 2 đều import `baseConfig` từ root và bổ sung/override `baseURL`, `testDir` (`./e2e`), và cấu hình tự động khởi chạy môi trường dev qua `webServer`.

---

## 2. Cách viết test mới (Writing Tests)

Tất cả các file test E2E phải được đặt trong thư mục `e2e` của mỗi sub-repo (ví dụ: `nextjs-base/e2e/example.spec.ts`).

### Mẫu viết test cơ bản

```typescript
import { test, expect } from '@playwright/test';

test('Kiểm tra trang chủ', async ({ page }) => {
  // Đi tới baseURL được cấu hình trong config
  await page.goto('/');
  
  // Thực hiện các kiểm tra (expectations)
  await expect(page).toHaveTitle(/Tên dự án/i);
});
```

---

## 3. Lệnh thực thi (Commands)

Bạn có thể chạy kiểm thử từ thư mục root của dự án hoặc bên trong từng thư mục con:

### Chạy từ thư mục Root

- Chạy e2e tests cho Next.js:

  ```bash
  pnpm --filter next-app-template test:e2e
  ```

- Chạy e2e tests cho Nuxt.js:

  ```bash
  pnpm --filter nuxtjs-base test:e2e
  ```

### Chạy từ thư mục sub-repo

Di chuyển vào thư mục sub-repo tương ứng (`nextjs-base` hoặc `nuxtjs-base`) và chạy:

```bash
# Chạy tất cả test
pnpm test:e2e

# Chạy với giao diện Playwright UI
pnpm exec playwright test --ui

# Xem kết quả báo cáo (HTML Report)
pnpm exec playwright show-report
```
