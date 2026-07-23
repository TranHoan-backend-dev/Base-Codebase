/**
 * Tiện ích bảo mật chung cho toàn bộ ứng dụng (Base Security Utilities).
 * Cung cấp các hàm kiểm tra, làm sạch URL và type guard kiểm tra tính hợp lệ dữ liệu.
 *
 * @created_at 08/07/2026
 * @author txhoan
 */

import { GridConfigResponse, GridDataResponse } from "@/types/grid";

/**
 * Kiểm tra và làm sạch URL để ngăn chặn tấn công XSS (như javascript: scheme) hoặc Open Redirect nguy hiểm.
 * Sử dụng chung cho toàn ứng dụng (thẻ Link, nút điều hướng, nút hành động, markdown, v.v.).
 *
 * @param url URL thô cần kiểm tra
 * @param fallback URL mặc định trả về nếu url không hợp lệ (mặc định '#')
 * @returns URL an toàn đã được xác thực
 */
export function sanitizeUrl(url?: string, fallback = "#"): string {
  if (!url || typeof url !== "string") return fallback;
  const trimmed = url.trim();

  return /^(https?:\/\/|\/)/i.test(trimmed) ? trimmed : fallback;
}

/**
 * Kiểm tra xem một URL có an toàn để điều hướng nội bộ (Internal Redirect) hay không.
 *
 * @param url URL cần kiểm tra
 * @returns true nếu là đường dẫn nội bộ an toàn (bắt đầu bằng '/' và không phải '//')
 */
export function isInternalUrl(url?: string): boolean {
  if (!url || typeof url !== "string") return false;

  return url.startsWith("/") && !url.startsWith("//");
}

/**
 * Kiểm tra và làm sạch URL ngăn XSS / Open Redirect khi render thẻ Link cho Grid.
 * Kế thừa từ tiện ích bảo mật chung sanitizeUrl.
 */
export function sanitizeGridUrl(url?: string): string {
  return sanitizeUrl(url, "#");
}

/**
 * Type guard kiểm tra tính hợp lệ của cấu hình Grid trả về từ API.
 */
export function isValidGridConfig(data: unknown): data is GridConfigResponse {
  return (
    typeof data === "object" &&
    data !== null &&
    "gridCode" in data &&
    "columns" in data &&
    Array.isArray((data as Record<string, unknown>).columns)
  );
}

/**
 * Type guard kiểm tra tính hợp lệ của dữ liệu Grid trả về từ API.
 */
export function isValidGridData(data: unknown): data is GridDataResponse {
  return (
    typeof data === "object" &&
    data !== null &&
    "items" in data &&
    Array.isArray((data as Record<string, unknown>).items)
  );
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
  if (!html || typeof html !== "string") return "";

  // Nếu đang ở môi trường trình duyệt, sử dụng DOMParser để làm sạch các node độc hại
  if (typeof window !== "undefined" && typeof DOMParser !== "undefined") {
    try {
      const parser = new DOMParser();
      const doc = parser.parseFromString(html, "text/html");

      // Loại bỏ các thẻ nguy hiểm
      const dangerousTags = ["script", "iframe", "object", "embed", "link", "style", "form"];
      dangerousTags.forEach((tag) => {
        const elements = doc.querySelectorAll(tag);
        elements.forEach((el) => el.remove());
      });

      // Loại bỏ thuộc tính sự kiện on* và javascript: URLs
      const allElements = doc.querySelectorAll("*");
      allElements.forEach((el) => {
        Array.from(el.attributes).forEach((attr) => {
          if (attr.name.startsWith("on") || attr.value.trim().toLowerCase().startsWith("javascript:")) {
            el.removeAttribute(attr.name);
          }
        });
      });

      return doc.body.innerHTML;
    } catch {
      // Fallback nếu DOMParser gặp lỗi
    }
  }

  // Fallback regex đơn giản cho Server Side Rendering (SSR)
  return html
    .replace(/<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi, "")
    .replace(/<iframe\b[^<]*(?:(?!<\/iframe>)<[^<]*)*<\/iframe>/gi, "")
    .replace(/\son\w+\s*=\s*(?:'[^']*'|"[^"]*"|[^\s>]+)/gi, "")
    .replace(/href\s*=\s*(?:'javascript:[^']*'|"javascript:[^"]*")/gi, 'href="#"');
}

