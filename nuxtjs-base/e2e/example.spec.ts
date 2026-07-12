import { test, expect } from '@playwright/test'

test('homepage has expected content', async ({ page }) => {
  await page.goto('/')
  await expect(page).toHaveTitle(/Nuxt Starter Template/i)
  await expect(page.locator('text=A production-ready starter template')).toBeVisible()
})
