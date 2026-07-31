/**
 * Tiện ích quản lý Tenant ID trong cookie cho ứng dụng Next.js (Multi-Tenant).
 * Cung cấp hàm đọc/ghi cookie cho cả Client-side (trình duyệt) và Server-side (SSR / Server Components).
 *
 * @created_at 19/07/2026
 * @author txhoan
 */

/** Tên khóa (key) lưu trữ Tenant ID trong cookie */
export const TENANT_COOKIE_KEY = "tenant_id";

/**
 * Lấy Tenant ID từ cookie phía Client (Trình duyệt).
 *
 * @returns {string | null} Giá trị Tenant ID hoặc null nếu không tồn tại/chạy trên Server.
 */
export function getTenantIdClient(): string | null {
  if (typeof document === "undefined") {
    return null;
  }
  const match = document.cookie.match(
    new RegExp(`(^| )${TENANT_COOKIE_KEY}=([^;]+)`),
  );

  return match && match[2] ? decodeURIComponent(match[2]) : null;
}

/**
 * Lưu Tenant ID vào cookie phía Client (Trình duyệt).
 *
 * @param {string} tenantId - Mã định danh khách hàng cần lưu.
 * @param {number} [days=365] - Số ngày hết hạn của cookie (mặc định 365 ngày).
 */
export function setTenantIdClient(tenantId: string, days = 365): void {
  if (typeof document === "undefined") {
    return;
  }
  const expires = new Date(Date.now() + days * 864e5).toUTCString();
  document.cookie = `${TENANT_COOKIE_KEY}=${encodeURIComponent(tenantId)}; expires=${expires}; path=/; SameSite=Lax`;
}

/**
 * Xóa Tenant ID khỏi cookie phía Client (Trình duyệt).
 */
export function removeTenantIdClient(): void {
  if (typeof document === "undefined") {
    return;
  }
  document.cookie = `${TENANT_COOKIE_KEY}=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/; SameSite=Lax`;
}

/**
 * Lấy Tenant ID từ cookie phía Server (SSR / Server Components / API Routes).
 * Trong Next.js 15+, cookies() trả về Promise<ReadonlyRequestCookies>.
 *
 * @returns {Promise<string | null>} Promise chứa Tenant ID hoặc null nếu không tìm thấy.
 */
export async function getTenantIdServer(): Promise<string | null> {
  if (typeof window !== "undefined") {
    return getTenantIdClient();
  }
  try {
    const { cookies } = await import("next/headers");
    const cookieStore = await cookies();
    const tenantCookie = cookieStore.get(TENANT_COOKIE_KEY);

    return tenantCookie?.value ?? null;
  } catch {
    return null;
  }
}
