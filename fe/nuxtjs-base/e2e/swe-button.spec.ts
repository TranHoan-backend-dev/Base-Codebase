import { test, expect } from '@playwright/test'

test.describe('SweButton Nuxt 4 E2E Tests', () => {
  test.setTimeout(60000)

  test.beforeEach(async ({ page }) => {
    await expect(async () => {
      await page.goto('/test/button', { waitUntil: 'commit', timeout: 15000 })
    }).toPass({ timeout: 45000, intervals: [1000, 2000, 3000] })
  })

  test('nên hiển thị nút cơ bản và phản hồi sự kiện click thành công', async ({ page }) => {
    const basicBtn = page.getByTestId('basic-btn')
    await expect(basicBtn).toBeVisible()
    await expect(basicBtn).toContainText('Click Me')

    const clickCount = page.getByTestId('click-count')
    await expect(clickCount).toHaveText('Click count: 0')

    await basicBtn.click()
    await expect(clickCount).toHaveText('Click count: 1')
  })

  test('nên chuyển đổi sang loadingText khi nút chuyển sang trạng thái loading', async ({ page }) => {
    const loadingBtn = page.getByTestId('loading-btn')
    await expect(loadingBtn).toBeVisible()
    await expect(loadingBtn).toContainText('Start Process')

    await loadingBtn.click()
    await expect(loadingBtn).toContainText('Processing...')
  })

  test('nên hiển thị icon ở vị trí bên phải tiêu đề nút', async ({ page }) => {
    const iconRightBtn = page.getByTestId('icon-right-btn')
    await expect(iconRightBtn).toBeVisible()
    await expect(iconRightBtn).toContainText('Next Step')
  })

  test('nên hiển thị nhãn Badge và mở Tooltip khi di chuột vào nút', async ({ page }) => {
    const tooltipBadgeBtn = page.getByTestId('tooltip-badge-btn')
    await expect(tooltipBadgeBtn).toBeVisible()
    await expect(tooltipBadgeBtn).toContainText('Notifications')

    await tooltipBadgeBtn.scrollIntoViewIfNeeded()
    await tooltipBadgeBtn.hover()
    const tooltip = page.getByText('Interactive Tooltip Content')
    await expect(tooltip).toBeVisible({ timeout: 5000 })
  })

  test('nên hiển thị nội dung HTML đã làm sạch trong Tooltip khi sử dụng tooltipHtml', async ({ page }) => {
    const htmlTooltipBtn = page.getByTestId('html-tooltip-btn')
    await expect(htmlTooltipBtn).toBeVisible()

    await htmlTooltipBtn.scrollIntoViewIfNeeded()
    await htmlTooltipBtn.hover()
    const htmlTooltipContent = page.getByTestId('html-tooltip-content')
    await expect(htmlTooltipContent).toBeVisible({ timeout: 5000 })
    await expect(htmlTooltipContent.locator('strong')).toHaveText('HTML Header')
    await expect(htmlTooltipContent.locator('em')).toHaveText('HTML content')
  })
})
