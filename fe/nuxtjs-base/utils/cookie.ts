/**
 * Tiện ích quản lý JWT tokens trong cookie cho Nuxt.js.
 * Hỗ trợ đọc/ghi cookie cho cả Client-side và Server-side trong Nuxt.
 *
 * @created_at 01/08/2026
 * @author txhoan
 */

export const ACCESS_TOKEN_KEY = "access_token";
export const REFRESH_TOKEN_KEY = "refresh_token";

export function getCookieClient(key: string): string | null {
  if (typeof document === "undefined") return null;
  const match = document.cookie.match(new RegExp(`(^| )${key}=([^;]+)`));
  return match && match[2] ? decodeURIComponent(match[2]) : null;
}

export function setCookieClient(key: string, value: string, days = 7): void {
  if (typeof document === "undefined") return;
  const expires = new Date(Date.now() + days * 864e5).toUTCString();
  document.cookie = `${key}=${encodeURIComponent(value)}; expires=${expires}; path=/; SameSite=Lax; Secure`;
}

export function removeCookieClient(key: string): void {
  if (typeof document === "undefined") return;
  document.cookie = `${key}=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/; SameSite=Lax; Secure`;
}
