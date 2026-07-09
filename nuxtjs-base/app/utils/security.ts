/**
 * Tiện ích bảo mật chung cho toàn ứng dụng Nuxt 3 (Base Security Utilities).
 * Cung cấp các hàm kiểm tra, làm sạch URL ngăn XSS / Open Redirect và Type Guard cho Grid.
 *
 * @created_at 08/07/2026
 * @author txhoan
 */

import type { GridConfigResponse, GridDataResponse } from '@/types/grid'

/**
 * Kiểm tra và làm sạch URL nhằm ngăn chặn XSS (javascript: scheme) và Open Redirect.
 *
 * @param url URL thô cần kiểm tra
 * @param fallback URL mặc định trả về nếu không hợp lệ (mặc định '#')
 * @returns URL an toàn đã xác thực
 */
export function sanitizeUrl(url?: string, fallback = '#'): string {
  if (!url || typeof url !== 'string') return fallback
  const trimmed = url.trim()
  return /^(https?:\/\/|\/)/i.test(trimmed) ? trimmed : fallback
}

/**
 * Kiểm tra URL có phải là đường dẫn nội bộ an toàn hay không.
 */
export function isInternalUrl(url?: string): boolean {
  if (!url || typeof url !== 'string') return false
  return url.startsWith('/') && !url.startsWith('//')
}

/**
 * Làm sạch URL cho cột Link trong Grid (kế thừa từ tiện ích bảo mật chung).
 */
export function sanitizeGridUrl(url?: string): string {
  return sanitizeUrl(url, '#')
}

/**
 * Kiểm tra tính hợp lệ của schema cấu hình Grid trả về từ API.
 */
export function isValidGridConfig(data: unknown): data is GridConfigResponse {
  return (
    typeof data === 'object'
    && data !== null
    && 'gridCode' in data
    && 'columns' in data
    && Array.isArray((data as Record<string, unknown>).columns)
  )
}

/**
 * Kiểm tra tính hợp lệ của schema dữ liệu Grid trả về từ API.
 */
export function isValidGridData(data: unknown): data is GridDataResponse {
  return (
    typeof data === 'object'
    && data !== null
    && 'items' in data
    && Array.isArray((data as Record<string, unknown>).items)
  )
}
