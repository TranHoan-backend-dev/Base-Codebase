import { defineConfig } from '@playwright/test'
import { baseConfig } from '../playwright.config.base'

/**
 * Playwright configuration for nuxtjs-base, extending root base config.
 */
export default defineConfig({
  ...baseConfig,
  testDir: './e2e',
  webServer: {
    command: 'pnpm dev',
    url: 'http://127.0.0.1:3000',
    reuseExistingServer: !process.env.CI,
    timeout: 120000,
    env: {
      PORT: '3000'
    }
  },
  use: {
    ...baseConfig.use,
    baseURL: 'http://127.0.0.1:3000'
  }
})
