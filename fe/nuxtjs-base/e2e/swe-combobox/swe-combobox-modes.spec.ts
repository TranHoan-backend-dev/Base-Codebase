import { test, expect } from '@playwright/test'

test.describe('SweCombobox E2E - Chế độ hiển thị (Edit / View Modes)', () => {
  test.setTimeout(60000)

  test.beforeEach(async ({ page }) => {
    await expect(async () => {
      await page.goto('/test/combobox', { waitUntil: 'commit', timeout: 15000 })
    }).toPass({ timeout: 45000, intervals: [1000, 2000, 3000] })
  })

  test('nên hiển thị trang thử nghiệm SweCombobox thành công', async ({ page }) => {
    const header = page.getByTestId('page-header')
    await expect(header).toBeVisible()
    await expect(header).toContainText('SweCombobox Test Page')
  })

  test('nên chuyển đổi giữa Edit Mode và View Mode khi click button', async ({ page }) => {
    const editBtn = page.getByTestId('mode-edit-btn')
    const viewBtn = page.getByTestId('mode-view-btn')

    await expect(editBtn).toBeVisible()
    await expect(viewBtn).toBeVisible()

    // Chuyển sang View Mode
    await viewBtn.click()
    const containerText = page.getByTestId('container-text')
    await expect(containerText.locator('.border-b-2')).toBeVisible()

    // Chuyển lại Edit Mode
    await editBtn.click()
    await expect(containerText).toBeVisible()
  })
})
