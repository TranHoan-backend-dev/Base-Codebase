import { test, expect } from '@playwright/test';

test.describe('SweButton Component E2E Tests', () => {
  test.setTimeout(60000);

  test.beforeEach(async ({ page }) => {
    await expect(async () => {
      await page.goto('/test/swe-button', { waitUntil: 'commit', timeout: 15000 });
    }).toPass({ timeout: 45000, intervals: [1000, 2000, 3000] });
  });

  test('nên hiển thị nút cơ bản và phản hồi sự kiện onPress thành công', async ({ page }) => {
    const basicBtn = page.getByTestId('basic-btn');
    await expect(basicBtn).toBeVisible();
    await expect(basicBtn).toBeEnabled();
    await expect(basicBtn).toHaveText('Click Me');

    const clickCount = page.getByTestId('click-count');
    await expect(clickCount).toHaveText('Click count: 0');

    // Click với force và delay nhỏ đảm bảo React Aria đã hydration xong
    await page.waitForTimeout(200);
    await basicBtn.click();
    await expect(clickCount).toHaveText('Click count: 1');
  });

  test('nên chuyển đổi sang loadingText khi nút chuyển sang trạng thái loading', async ({ page }) => {
    const loadingBtn = page.getByTestId('loading-btn');
    await expect(loadingBtn).toBeVisible();
    await expect(loadingBtn).toBeEnabled();
    await expect(loadingBtn).toHaveText('Start Process');

    await page.waitForTimeout(200);
    await loadingBtn.click();
    await expect(loadingBtn).toHaveText('Processing...');
    await expect(loadingBtn).toHaveAttribute('data-pending', 'true');
  });

  test('nên hiển thị icon ở vị trí bên phải tiêu đề nút', async ({ page }) => {
    const iconRightBtn = page.getByTestId('icon-right-btn');
    await expect(iconRightBtn).toBeVisible();
    await expect(iconRightBtn).toContainText('Next Step');

    const iconSvg = iconRightBtn.getByTestId('btn-icon-svg');
    await expect(iconSvg).toBeVisible();
  });

  test('nên hiển thị nhãn Badge và mở Tooltip khi di chuột vào nút', async ({ page }) => {
    const tooltipBadgeBtn = page.getByTestId('tooltip-badge-btn');
    await expect(tooltipBadgeBtn).toBeVisible();
    await expect(tooltipBadgeBtn).toContainText('Notifications');

    // Kiểm tra Badge
    const badge = page.locator('.badge--danger, [data-slot="badge"]').filter({ hasText: '5' });
    await expect(badge.first()).toBeVisible();

    // Hover để mở Tooltip
    await tooltipBadgeBtn.scrollIntoViewIfNeeded();
    await tooltipBadgeBtn.focus();
    await tooltipBadgeBtn.hover();
    const tooltipContent = page.getByTestId('tooltip-content');
    await expect(tooltipContent).toBeVisible({ timeout: 5000 });
    await expect(tooltipContent).toHaveText('Interactive Tooltip Content');
  });

  test('nên hiển thị nội dung HTML đã làm sạch trong Tooltip khi sử dụng tooltipHtml', async ({ page }) => {
    const htmlTooltipBtn = page.getByTestId('html-tooltip-btn');
    await expect(htmlTooltipBtn).toBeVisible();

    await htmlTooltipBtn.scrollIntoViewIfNeeded();
    await htmlTooltipBtn.focus();
    await htmlTooltipBtn.hover();
    const htmlTooltipContent = page.getByTestId('html-tooltip-content');
    await expect(htmlTooltipContent).toBeVisible({ timeout: 5000 });
    await expect(htmlTooltipContent.locator('strong')).toHaveText('HTML Header');
    await expect(htmlTooltipContent.locator('em')).toHaveText('HTML content');
  });
});
