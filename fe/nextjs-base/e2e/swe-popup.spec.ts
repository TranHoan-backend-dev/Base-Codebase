import { test, expect } from '@playwright/test';

test.describe('SweBasePopup & SweBasePopupSidebar Component E2E Tests', () => {
  test.setTimeout(60000);

  test.beforeEach(async ({ page }) => {
    await expect(async () => {
      await page.goto('/test/popup', { waitUntil: 'commit', timeout: 15000 });
    }).toPass({ timeout: 45000, intervals: [1000, 2000, 3000] });
  });

  test('nên mở và đóng Base Popup thành công với đầy đủ cấu trúc Header, Body, Footer', async ({ page }) => {
    const openBtn = page.locator('button:has-text("Open Base Popup")');
    await expect(openBtn).toBeEnabled();

    const dialog = page.getByTestId('swe-popup-dialog');
    await expect(async () => {
      if (!(await dialog.isVisible())) {
        await openBtn.click();
      }
      await expect(dialog).toBeVisible({ timeout: 2000 });
    }).toPass({ timeout: 15000 });

    // Kiểm tra Header & Title
    const header = dialog.getByTestId('swe-popup-header');
    await expect(header).toBeVisible();
    await expect(header.locator('[data-testid="swe-popup-title"].swe_popup_title')).toHaveText('Base Popup Test');
    await expect(header.locator('[data-testid="swe-popup-description"].swe_popup_description')).toBeVisible();

    // Kiểm tra Body
    const body = dialog.getByTestId('swe-popup-body');
    await expect(body).toBeVisible();

    // Kiểm tra Footer & Nút Hủy / Lưu
    const footer = dialog.getByTestId('swe-popup-footer');
    await expect(footer).toBeVisible();
    const cancelBtn = footer.locator('[data-testid="swe-popup-cancel-btn"]');
    await expect(cancelBtn).toBeVisible();

    // Đóng Popup bằng nút Hủy
    await cancelBtn.click();
    await expect(dialog).toBeHidden();
  });

  test('nên mở và đóng Sidebar Drawer thành công', async ({ page }) => {
    const openSidebarBtn = page.locator('button:has-text("Open Sidebar Drawer")');
    await expect(openSidebarBtn).toBeEnabled();

    const dialog = page.getByTestId('swe-sidebar-dialog');
    await expect(async () => {
      if (!(await dialog.isVisible())) {
        await openSidebarBtn.click();
      }
      await expect(dialog).toBeVisible({ timeout: 2000 });
    }).toPass({ timeout: 15000 });

    // Kiểm tra Header & Title của Sidebar
    const header = dialog.getByTestId('swe-sidebar-header');
    await expect(header).toBeVisible();
    await expect(header.locator('[data-testid="swe-sidebar-title"].swe_popup_title')).toHaveText('Sidebar Drawer Test');

    // Kiểm tra Body & Footer của Sidebar
    await expect(dialog.getByTestId('swe-sidebar-body')).toBeVisible();
    const footer = dialog.getByTestId('swe-sidebar-footer');
    await expect(footer).toBeVisible();

    // Đóng Sidebar bằng nút close trigger trên header
    const closeTrigger = header.locator('[data-testid="swe-sidebar-close-btn"]');
    if (await closeTrigger.isVisible()) {
      await closeTrigger.click();
    } else {
      await footer.locator('[data-testid="swe-sidebar-cancel-btn"]').click();
    }
    await expect(dialog).toBeHidden();
  });
});
