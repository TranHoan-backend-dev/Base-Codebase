import { useCookie } from '#app'
import { TENANT_COOKIE_KEY } from '../utils/tenantCookie'

/**
 * Composable quản lý Tenant ID cho kiến trúc Multi-Tenant trong Nuxt 4.
 * Sử dụng `useCookie` để tự động đồng bộ giá trị cookie giữa Server (SSR) và Client (CSR).
 *
 * @created_at 19/07/2026
 * @author txhoan
 */
export function useTenant() {
  const tenantCookie = useCookie<string | null>(TENANT_COOKIE_KEY, {
    default: () => null,
    maxAge: 60 * 60 * 24 * 365,
    sameSite: 'lax',
    path: '/'
  })

  return {
    /** Ref chứa giá trị Tenant ID hiện tại */
    tenantId: tenantCookie,
    /**
     * Cập nhật Tenant ID mới vào cookie.
     * @param id Giá trị Tenant ID mới hoặc null để xóa.
     */
    setTenantId: (id: string | null) => {
      tenantCookie.value = id
    }
  }
}
