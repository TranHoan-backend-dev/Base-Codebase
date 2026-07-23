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

/**
 * Làm sạch chuỗi HTML đầu vào để ngăn ngừa các cuộc tấn công XSS (Cross-Site Scripting).
 * Loại bỏ các thẻ script, iframe, object, embed và các thuộc tính sự kiện nguy hiểm (onerror, onload, v.v.).
 *
 * @param html Chuỗi HTML thô cần làm sạch
 * @returns Chuỗi HTML an toàn
 *
 * @created_at 23/07/2026
 * @author txhoan
 */
export function sanitizeHtml(html?: string): string {
  if (!html || typeof html !== 'string') return ''

  if (typeof window !== 'undefined' && typeof DOMParser !== 'undefined') {
    try {
      const parser = new DOMParser()
      const doc = parser.parseFromString(html, 'text/html')

      const dangerousTags = ['script', 'iframe', 'object', 'embed', 'link', 'style', 'form']
      dangerousTags.forEach((tag) => {
        const elements = doc.querySelectorAll(tag)
        elements.forEach(el => el.remove())
      })

      const allElements = doc.querySelectorAll('*')
      allElements.forEach((el) => {
        Array.from(el.attributes).forEach((attr) => {
          if (attr.name.startsWith('on') || attr.value.trim().toLowerCase().startsWith('javascript:')) {
            el.removeAttribute(attr.name)
          }
        })
      })

      return doc.body.innerHTML
    } catch {
      // Fallback
    }
  }

  return html
    .replace(/<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi, '')
    .replace(/<iframe\b[^<]*(?:(?!<\/iframe>)<[^<]*)*<\/iframe>/gi, '')
    .replace(/\son\w+\s*=\s*(?:'[^']*'|"[^"]*"|[^\s>]+)/gi, '')
    .replace(/href\s*=\s*(?:'javascript:[^']*'|"javascript:[^"]*")/gi, 'href="#"')
}
