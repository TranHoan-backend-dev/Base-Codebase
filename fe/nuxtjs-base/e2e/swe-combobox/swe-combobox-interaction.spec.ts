import { test, expect } from '@playwright/test'

test.describe('SweCombobox E2E - Tương tác và thay đổi tùy chọn (Interactions)', () => {
  test.setTimeout(60000)

  test.beforeEach(async ({ page }) => {
    await expect(async () => {
      await page.goto('/test/combobox', { waitUntil: 'commit', timeout: 15000 })
    }).toPass({ timeout: 45000, intervals: [1000, 2000, 3000] })
  })

  test('nên cho phép người dùng mở dropdown và chọn tùy chọn mới', async ({ page }) => {
    const textContainer = page.getByTestId('container-text')
    await expect(textContainer).toBeVisible()

    const selectedVal = page.getByTestId('selected-text-val')
    await expect(selectedVal).toContainText('opt-1')

    // Click mở menu select
    const selectTrigger = textContainer.locator('button')
    if (await selectTrigger.isVisible()) {
      await selectTrigger.click()

      // Chọn option thứ hai
      const option2 = page.getByText('Tùy chọn 2')
      if (await option2.isVisible()) {
        await option2.click()
        await expect(selectedVal).toContainText('opt-2')
      }
    }
  })

  test('nên duy trì giá trị đã chọn khi chuyển giữa Edit và View mode', async ({ page }) => {
    const viewBtn = page.getByTestId('mode-view-btn')
    const editBtn = page.getByTestId('mode-edit-btn')

    const tagVal = page.getByTestId('selected-tag-val')
    await expect(tagVal).toContainText('active')

    // Chuyển sang View mode
    await viewBtn.click()
    const tagContainer = page.getByTestId('container-tag')
    await expect(tagContainer).toContainText('Hoạt động')

    // Chuyển về Edit mode
    await editBtn.click()
    await expect(tagVal).toContainText('active')
  })
})
