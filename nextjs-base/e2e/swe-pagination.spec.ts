import { test, expect } from '@playwright/test';

test.describe('SweGridPagination Component E2E Tests', () => {
  test.setTimeout(60000);

  test.beforeEach(async ({ page }) => {
    await expect(async () => {
      await page.goto('/test/pagination', { waitUntil: 'commit', timeout: 15000 });
    }).toPass({ timeout: 45000, intervals: [1000, 2000, 3000] });
    await expect(page.getByTestId('swe-grid-pagination')).toBeVisible({ timeout: 30000 });
  });

  test('nên hiển thị đúng thông tin tổng số bản ghi và các nút điều hướng phân trang', async ({ page }) => {
    const pagination = page.getByTestId('swe-grid-pagination');

    // Kiểm tra thông tin hiển thị
    const infoText = pagination.locator('[data-testid="swe-pagination-info"]');
    await expect(infoText).toBeVisible();
    await expect(infoText).toContainText('10');
    await expect(infoText).toContainText('50');

    // Kiểm tra nút Trang trước / Trang tiếp
    const prevBtn = pagination.locator('[data-testid="swe-pagination-prev-btn"]');
    const nextBtn = pagination.locator('[data-testid="swe-pagination-next-btn"]');

    await expect(prevBtn).toBeVisible();
    await expect(nextBtn).toBeVisible();
  });

  test('nên chuyển trang khi bấm vào số trang hoặc nút Trang tiếp', async ({ page }) => {
    const pagination = page.getByTestId('swe-grid-pagination');
    const nextBtn = pagination.locator('[data-testid="swe-pagination-next-btn"]');
    await expect(nextBtn).toBeEnabled();
    // Bấm trang tiếp (sang trang 2)
    const page2Text = page.locator('text=Trang hiện tại: 2');
    await expect(async () => {
      if (!(await page2Text.isVisible())) {
        await nextBtn.click();
      }
      await expect(page2Text).toBeVisible({ timeout: 2000 });
    }).toPass({ timeout: 15000 });

    // Bấm thẳng vào số trang 3
    const page3Btn = pagination.locator('[data-testid="swe-pagination-page-3"]');
    await expect(page3Btn).toBeVisible();
    const page3Text = page.locator('text=Trang hiện tại: 3');
    await expect(async () => {
      if (!(await page3Text.isVisible())) {
        await page3Btn.click();
      }
      await expect(page3Text).toBeVisible({ timeout: 2000 });
    }).toPass({ timeout: 15000 });
  });
});
