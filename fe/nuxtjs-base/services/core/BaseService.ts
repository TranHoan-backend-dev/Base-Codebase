import { getTenantIdClient } from '../../app/utils/tenantCookie'
import { getCookieClient, setCookieClient, removeCookieClient, ACCESS_TOKEN_KEY, REFRESH_TOKEN_KEY } from '../../utils/cookie'

/**
 * Lớp cơ sở (Core BaseService) cung cấp nền tảng xử lý HTTP request chung cho toàn ứng dụng Nuxt.js.
 * Có trách nhiệm cấu hình headers mặc định, đính kèm authentication token, tenant id và xử lý lỗi global.
 *
 * Created at: 21/06/2026
 * @author txhoan
 */
export class BaseService {
  private isRefreshing = false
  private refreshSubscribers: ((token: string) => void)[] = []

  private subscribeTokenRefresh(cb: (token: string) => void) {
    this.refreshSubscribers.push(cb)
  }

  private onRefreshed(token: string) {
    this.refreshSubscribers.forEach(cb => cb(token))
    this.refreshSubscribers = []
  }

  protected async request<T>(url: string, options: RequestInit = {}): Promise<T> {
    const tenantId = getTenantIdClient()
    const token = getCookieClient(ACCESS_TOKEN_KEY)

    const defaultHeaders: Record<string, string> = {
      'Content-Type': 'application/json',
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
      ...(tenantId ? { 'X-Tenant-ID': tenantId } : {})
    }

    const response = await fetch(url, {
      ...options,
      headers: { ...defaultHeaders, ...options.headers }
    })

    if (!response.ok) {
      if (response.status === 401) {
        if (url.includes('/auth/refresh-token') || url.includes('/auth/refresh')) {
          this.handleAuthFailure()
          throw new Error('Session expired')
        }

        try {
          const newToken = await this.handleRefreshToken()
          const retryHeaders = {
            ...options.headers,
            Authorization: `Bearer ${newToken}`
          }
          const retryResponse = await fetch(url, {
            ...options,
            headers: retryHeaders as Record<string, string>
          })
          return retryResponse.json()
        } catch (refreshError) {
          this.handleAuthFailure()
          throw refreshError
        }
      }
      throw new Error(`HTTP error! status: ${response.status}`)
    }

    return response.json()
  }

  private async handleRefreshToken(): Promise<string> {
    if (this.isRefreshing) {
      return new Promise((resolve) => {
        this.subscribeTokenRefresh((token) => {
          resolve(token)
        })
      })
    }

    this.isRefreshing = true
    const refreshToken = getCookieClient(REFRESH_TOKEN_KEY)

    if (!refreshToken) {
      this.isRefreshing = false
      throw new Error('No refresh token')
    }

    try {
      const response = await fetch('/api/auth/refresh-token', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ token: refreshToken })
      })

      if (!response.ok) throw new Error('Failed to refresh token')

      const data = await response.json()
      const newAccessToken = data.accessToken
      setCookieClient(ACCESS_TOKEN_KEY, newAccessToken)

      this.isRefreshing = false
      this.onRefreshed(newAccessToken)
      return newAccessToken
    } catch (err) {
      this.isRefreshing = false
      throw err
    }
  }

  private handleAuthFailure() {
    removeCookieClient(ACCESS_TOKEN_KEY)
    removeCookieClient(REFRESH_TOKEN_KEY)
    if (typeof window !== 'undefined') {
      window.location.href = '/login'
    }
  }

  public get<T>(url: string, options?: RequestInit) {
    return this.request<T>(url, { ...options, method: 'GET' })
  }

  public post<T>(url: string, body: unknown, options?: RequestInit) {
    return this.request<T>(url, { ...options, method: 'POST', body: JSON.stringify(body) })
  }

  public put<T>(url: string, body: unknown, options?: RequestInit) {
    return this.request<T>(url, { ...options, method: 'PUT', body: JSON.stringify(body) })
  }

  public delete<T>(url: string, options?: RequestInit) {
    return this.request<T>(url, { ...options, method: 'DELETE' })
  }
}

export const baseService = new BaseService()
