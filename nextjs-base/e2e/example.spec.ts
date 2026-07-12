import { test, expect } from '@playwright/test';

test('homepage has expected content', async ({ page }) => {
  await page.goto('/');
  await expect(page).toHaveTitle(/Next\.js \+ HeroUI/i);
  await expect(page.locator('text=Make beautiful websites')).toBeVisible();
});
