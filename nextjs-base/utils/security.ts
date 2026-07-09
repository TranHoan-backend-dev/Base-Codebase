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
