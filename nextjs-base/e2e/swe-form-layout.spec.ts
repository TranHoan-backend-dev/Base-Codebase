import { test, expect } from '@playwright/test';

test.describe('SwePageLayout & SweToolbar Component E2E Tests', () => {
  test.setTimeout(60000);

  test.beforeEach(async ({ page }) => {
    // Tự động thử lại tải trang nếu dev server Next.js từ chối kết nối hoặc bị treo do đang biên dịch HMR
    await expect(async () => {
      await page.goto('/test/toolbar', { waitUntil: 'commit', timeout: 15000 });
    }).toPass({ timeout: 45000, intervals: [1000, 2000, 3000] });
    await expect(page.getByTestId('swe-page-layout')).toBeVisible({ timeout: 30000 });
  });

  test('nên hiển thị đầy đủ cấu trúc Toolbar, Content và Footer', async ({ page }) => {
    const layout = page.getByTestId('swe-page-layout');
    const toolbar = layout.getByTestId('swe-toolbar');
    const content = layout.getByTestId('swe-page-content');
    const footer = layout.getByTestId('swe-form-layout-footer');

    await expect(toolbar).toBeVisible();
    await expect(toolbar.locator('[data-testid="swe-toolbar-title"].swe_toolbar_title')).toHaveText('Kiểm thử Form Layout');
    await expect(content).toBeVisible();
    await expect(footer).toBeVisible();
    await expect(footer.locator('[data-testid="swe-form-footer-save-btn"]')).toBeVisible();
    await expect(footer.locator('[data-testid="swe-form-footer-cancel-btn"]')).toBeVisible();
  });

  test('nên thao tác chuyển đổi sang Pick-Many Mode và kiểm tra nút Bỏ chọn, Xóa', async ({ page }) => {
    const layout = page.getByTestId('swe-page-layout');
    const toolbar = layout.getByTestId('swe-toolbar');
    const toolbarRight = toolbar.getByTestId('swe-toolbar-right');

    // Đảm bảo nút Toggle đã sẵn sàng và lặp click nếu hydration của dev server phản hồi chậm
    const toggleBtn = toolbarRight.locator('button:has-text("Toggle Pick Mode")');
    await expect(toggleBtn).toBeEnabled();

    const toolbarLeft = toolbar.getByTestId('swe-toolbar-left');
    const selectedCount = toolbarLeft.locator('[data-testid="swe-toolbar-selected-count"]');
    await expect(async () => {
      if (!(await selectedCount.isVisible())) {
        await toggleBtn.click();
      }
      await expect(selectedCount).toBeVisible({ timeout: 2000 });
    }).toPass({ timeout: 15000 });
    const deselectBtn = toolbarLeft.locator('[data-testid="swe-toolbar-deselect-btn"]');
    const deleteBtn = toolbarLeft.locator('[data-testid="swe-toolbar-delete-btn"]');

    await expect(deselectBtn).toBeVisible();
    await expect(deleteBtn).toBeVisible();

    // Nhấn bỏ chọn
    await deselectBtn.click();
    await expect(toolbar.locator('[data-testid="swe-toolbar-title"].swe_toolbar_title')).toBeVisible();
  });
});
