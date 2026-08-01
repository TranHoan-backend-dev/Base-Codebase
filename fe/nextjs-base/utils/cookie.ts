/**
 * Tiện ích quản lý JWT tokens trong cookie cho cả Next.js và Nuxt.js.
 * Hỗ trợ đọc/ghi cookie cho cả Client-side và Server-side.
 *
 * @created_at 01/08/2026
 * @author txhoan
 */

export const ACCESS_TOKEN_KEY = "access_token";
export const REFRESH_TOKEN_KEY = "refresh_token";

/**
 * Đọc cookie theo key tại client.
 */
export function getCookieClient(key: string): string | null {
  if (typeof document === "undefined") return null;
  const match = document.cookie.match(new RegExp(`(^| )${key}=([^;]+)`));
  return match && match[2] ? decodeURIComponent(match[2]) : null;
}

/**
 * Ghi cookie tại client.
 */
export function setCookieClient(key: string, value: string, days = 7): void {
  if (typeof document === "undefined") return;
  const expires = new Date(Date.now() + days * 864e5).toUTCString();
  document.cookie = `${key}=${encodeURIComponent(value)}; expires=${expires}; path=/; SameSite=Lax; Secure`;
}

/**
 * Xóa cookie tại client.
 */
export function removeCookieClient(key: string): void {
  if (typeof document === "undefined") return;
  document.cookie = `${key}=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/; SameSite=Lax; Secure`;
}

/**
 * Lấy token từ server-side (Next.js context).
 */
export async function getCookieServer(key: string): Promise<string | null> {
  if (typeof window !== "undefined") {
    return getCookieClient(key);
  }
  try {
    const { cookies } = await import("next/headers");
    const cookieStore = await cookies();
    return cookieStore.get(key)?.value ?? null;
  } catch {
    return null;
  }
}
