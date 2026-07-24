---
name: playwright-visual-component
description: Quy chuẩn lập Test Plan và viết Visual Regression & Component tests cho UI Components (HeroUI / NuxtUI) với Playwright, bao gồm so sánh snapshot và cấu trúc file/folder riêng biệt.
---

# Playwright Visual & Component Testing Skill

Skill này quy định quy trình và tiêu chuẩn bắt buộc khi thực hiện kiểm thử giao diện bằng hình ảnh (Visual Regression Testing) và Component Testing cho các thư viện UI (HeroUI v3.2.1 trong `nextjs-base` và NuxtUI trong `nuxtjs-base`).

---

## 1. QUY TRÌNH BẮT BUỘC: LẬP TEST PLAN TRƯỚC KHIN VIẾT CODE

Mọi tác vụ kiểm thử Visual & Component test PHẢI tuân theo 2 bước:

### Bước 1: Trình bày Test Plan (Chờ người dùng phê duyệt)

Trước khi tạo file test, Agent PHẢI tạo một **Test Plan** chi tiết:

1. **Target Component / UI Feature:** Component hoặc giao diện cần kiểm thử (ví dụ: `Button`, `Modal`, `Navbar`).
2. **Folder Structure:** Cấu trúc thư mục sẽ tạo.
3. **Visual Test Cases Details:**
   - **Test Case Name:** Tên file `.ts` tương ứng.
   - **Mục tiêu (Goal):** Kiểm thử trạng thái nào (Default, Hover, Active, Disabled, Dark Mode, Responsive breakpoint).
   - **Đầu vào (Inputs / Props / State):** Props truyền vào component, kích thước Viewport, Theme mode.
   - **Kỳ vọng (Expected Output):** Tên snapshot reference, ngưỡng sai lệch tối đa cho phép (`maxDiffPixels` / `threshold`).

> ⚠️ **CỔNG PHÊ DUYỆT:** Dừng lại và hỏi người dùng: *"Bạn có đồng ý với Test Plan này để tiến hành viết code visual/component test không?"*. CHỈ thực thi sau khi nhận được lời đồng ý.

### Bước 2: Triển khai Code Test

Sau khi Test Plan được duyệt, tiến hành tạo các file `.ts` theo đúng cấu trúc.

---

## 2. QUY CHUẨN CẤU TRÚC FILE VÀ THƯ MỤC

- **Folder theo Component/Feature:** Bộ test visual cho 1 component PHẢI nằm trong thư mục mang tên component đó.
- **1 Test Case = 1 File `.ts`:** Mỗi file `.ts` chỉ phụ trách kiểm thử 1 khía cạnh / 1 nhóm variant trực quan của component.

### Cấu trúc mẫu

```text
tests/
└── visual/
    ├── hero-button/
    │   ├── button-variants-light.spec.ts
    │   ├── button-variants-dark.spec.ts
    │   └── button-states-hover.spec.ts
    └── header-navbar/
        ├── navbar-desktop.spec.ts
        └── navbar-mobile.spec.ts
```

---

## 3. TIÊU CHUẨN VISUAL & COMPONENT TESTING

### A. Visual Regression Testing (`toHaveScreenshot`)

- Sử dụng `toHaveScreenshot()` với các cấu hình chuẩn hóa:
  - `maxDiffPixels`: Định ngưỡng chênh lệch pixel chấp nhận được (mặc định <= 100px hoặc 0.2%).
  - `animations: 'disabled'`: Tắt animation trước khi chụp ảnh để tránh sai sót.
  - `mask`: Che các thành phần có nội dung động (như timestamp, avatar ngẫu nhiên).

### B. Theme Mode & Responsive Breakpoints

- Luôn kiểm thử ít nhất 2 chế độ: **Light Mode** và **Dark Mode**.
- Định nghĩa rõ Viewport (Mobile: 375x812, Desktop: 1280x720) cho các component responsive.

---

## 4. VÍ DỤ MINH HỌA FILE TEST CASE (`.spec.ts`)

```typescript
import { test, expect } from '@playwright/test';

test.describe('Visual Testing - HeroUI Button Variants (Dark Mode)', () => {
  test.use({ colorScheme: 'dark', viewport: { width: 1280, height: 720 } });

  test('should match visual snapshot for button primary variants', async ({ page }) => {
    // Setup / Input
    await page.goto('/storybook/button-variants');
    await page.waitForLoadState('networkidle');

    const buttonContainer = page.locator('#button-primary-group');

    // Assertion / Expected Snapshot
    await expect(buttonContainer).toHaveScreenshot('button-primary-dark.png', {
      maxDiffPixels: 50,
      animations: 'disabled',
    });
  });
});
```
