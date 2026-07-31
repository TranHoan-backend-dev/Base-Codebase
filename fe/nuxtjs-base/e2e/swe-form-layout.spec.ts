import { test, expect } from '@playwright/test'

test.describe('SweFormLayout Component E2E Tests', () => {
  test.setTimeout(60000)

  test.beforeEach(async ({ page }) => {
    await page.goto('/test/form-layout', { waitUntil: 'domcontentloaded' })
    await page.waitForSelector('body[data-hydrated="true"]', { timeout: 60000 })
    await expect(page.getByTestId('swe-form-layout')).toBeVisible({ timeout: 60000 })
  })

  test('nên hiển thị đầy đủ cấu trúc 3 phần: Toolbar, Content và Footer', async ({ page }) => {
    // 1. Kiểm tra wrapper chính
    const layout = page.getByTestId('swe-form-layout')
    await expect(layout).toBeVisible()

    // 2. Kiểm tra Toolbar
    const toolbar = page.getByTestId('swe-form-toolbar')
    await expect(toolbar).toBeVisible()
    await expect(toolbar.locator('.swe-form-toolbar__title')).toHaveText('Test SweFormLayout')
    await expect(toolbar.locator('.swe-form-toolbar__back-icon')).toBeVisible()

    // 3. Kiểm tra Content và các trường thông tin mẫu
    const content = page.getByTestId('swe-form-content')
    await expect(content).toBeVisible()
    await expect(content.locator('text=Thông tin cơ bản')).toBeVisible()
    await expect(content.locator('input[placeholder="Nhập họ và tên..."]')).toBeVisible()

    // 4. Kiểm tra Footer với 2 nút Hủy và Lưu
    const footer = page.getByTestId('swe-form-footer')
    await expect(footer).toBeVisible()
    await expect(footer.locator('.swe-form-footer__cancel-btn')).toBeVisible()
    await expect(footer.locator('.swe-form-footer__save-btn')).toBeVisible()
  })

  test('nên chỉ cuộn ở vùng nội dung (swe-form-content) trong khi Toolbar và Footer cố định', async ({ page }) => {
    // Kiểm tra body có overflow hidden
    const bodyOverflow = await page.evaluate(() => {
      return window.getComputedStyle(document.body).overflow
    })
    expect(bodyOverflow).toBe('hidden')

    const content = page.getByTestId('swe-form-content')
    const toolbar = page.getByTestId('swe-form-toolbar')
    const footer = page.getByTestId('swe-form-footer')

    // Kiểm tra toolbar và footer vẫn ở vị trí cố định trước khi cuộn
    const toolbarBoxBefore = await toolbar.boundingBox()
    const footerBoxBefore = await footer.boundingBox()

    // Cuộn xuống cuối vùng content (đến Section 6)
    const lastSection = content.locator('h3', { hasText: 'Section 6' })
    await lastSection.scrollIntoViewIfNeeded()

    // Kiểm tra toolbar và footer không bị di chuyển sau khi cuộn
    const toolbarBoxAfter = await toolbar.boundingBox()
    const footerBoxAfter = await footer.boundingBox()

    expect(toolbarBoxAfter?.y).toBe(toolbarBoxBefore?.y)
    expect(footerBoxAfter?.y).toBe(footerBoxBefore?.y)
    await expect(lastSection).toBeVisible()
  })

  test('nên hiển thị loading state và thông báo thành công khi bấm nút Lưu', async ({ page }) => {
    const saveButton = page.getByTestId('swe-form-footer').locator('.swe-form-footer__save-btn')

    // Nhấn Lưu
    await saveButton.evaluate(node => node.click())

    // Kiểm tra nút bị disable trong trạng thái loading
    await expect(saveButton).toBeDisabled()

    // Đợi toast xuất hiện sau khi hoàn tất lưu
    const toastMessage = page.locator('text=Đã lưu thành công!').first()
    await expect(toastMessage).toBeVisible({ timeout: 5000 })
  })

  test('nên hiển thị thông báo khi bấm nút Hủy', async ({ page }) => {
    const cancelButton = page.getByTestId('swe-form-footer').locator('.swe-form-footer__cancel-btn')

    // Nhấn Hủy bằng evaluate click
    await cancelButton.evaluate(node => node.click())

    // Kiểm tra toast thông báo hủy
    const toastMessage = page.locator('text=Đã hủy thao tác.').first()
    await expect(toastMessage).toBeVisible({ timeout: 5000 })
  })

  test('nên chuyển đổi giữa chế độ thường và Pick-Many Mode khi thao tác trên Toolbar', async ({ page }) => {
    const pickManyBtn = page.getByTestId('swe-form-toolbar-right').locator('.swe-form-toolbar__pick-many-btn')

    // 1. Bật Pick Many Mode
    await pickManyBtn.evaluate(node => node.click())

    // Kiểm tra Toolbar chuyển sang giao diện Pick Many
    const toolbarLeft = page.getByTestId('swe-form-toolbar-left')
    await expect(toolbarLeft.locator('text=Đã chọn 5 bản ghi')).toBeVisible()
    const deselectBtn = toolbarLeft.locator('.swe-form-toolbar__deselect-btn')
    const deleteBtn = toolbarLeft.locator('.swe-form-toolbar__delete-btn')
    await expect(deselectBtn).toBeVisible()
    await expect(deleteBtn).toBeVisible()

    // 2. Kiểm tra thao tác "Bỏ chọn"
    await deselectBtn.evaluate(node => node.click())

    // Quay lại chế độ thường
    await expect(page.getByTestId('swe-form-toolbar').locator('.swe-form-toolbar__title')).toBeVisible()
    await expect(toolbarLeft.locator('text=Đã chọn')).toBeHidden()

    // 3. Bật lại Pick Many và kiểm tra thao tác "Xóa"
    await pickManyBtn.evaluate(node => node.click())
    await deleteBtn.evaluate(node => node.click())

    // Hiển thị toast xóa và quay lại chế độ thường
    await expect(page.locator('text=Đã xóa 5 bản ghi.').first()).toBeVisible({ timeout: 5000 })
    await expect(page.getByTestId('swe-form-toolbar').locator('.swe-form-toolbar__title')).toBeVisible()
  })
})
