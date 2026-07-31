import { test, expect } from '@playwright/test'

test.describe('SweCombobox E2E - Hiển thị 5 kiểu dữ liệu (DataTypes)', () => {
  test.setTimeout(60000)

  test.beforeEach(async ({ page }) => {
    await expect(async () => {
      await page.goto('/test/combobox', { waitUntil: 'commit', timeout: 15000 })
    }).toPass({ timeout: 45000, intervals: [1000, 2000, 3000] })
  })

  test('nên hiển thị chính xác kiểu Text', async ({ page }) => {
    const textContainer = page.getByTestId('container-text')
    await expect(textContainer).toBeVisible()
    await expect(textContainer).toContainText('Tùy chọn 1')
  })

  test('nên hiển thị chính xác kiểu Tag với màu sắc chỉ định', async ({ page }) => {
    const tagContainer = page.getByTestId('container-tag')
    await expect(tagContainer).toBeVisible()
    await expect(tagContainer).toContainText('Hoạt động')
  })

  test('nên hiển thị chính xác kiểu Human với tên và ảnh đại diện', async ({ page }) => {
    const humanContainer = page.getByTestId('container-human')
    await expect(humanContainer).toBeVisible()
    await expect(humanContainer).toContainText('Trần Xuan Hoàn')
  })

  test('nên hiển thị chính xác kiểu Date định dạng Việt Nam', async ({ page }) => {
    const dateContainer = page.getByTestId('container-date')
    await expect(dateContainer).toBeVisible()
    await expect(dateContainer).toContainText('28/07/2026')
  })

  test('nên hiển thị chính xác kiểu Number phân cách hàng nghìn', async ({ page }) => {
    const numberContainer = page.getByTestId('container-number')
    await expect(numberContainer).toBeVisible()
    await expect(numberContainer).toContainText('1.500.000')
  })
})
