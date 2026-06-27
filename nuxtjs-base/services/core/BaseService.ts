/**
 * Lớp cơ sở (Core BaseService) cung cấp nền tảng xử lý HTTP request chung cho toàn ứng dụng Nuxt.js.
 * Có trách nhiệm cấu hình headers mặc định, đính kèm authentication token và xử lý lỗi global.
 *
 * Created at: 21/06/2026
 * @author txhoan
 */
export class BaseService {
  protected async request<T>(url: string, options: RequestInit = {}): Promise<T> {
    const defaultHeaders = {
      'Content-Type': 'application/json'
      // Add Authorization header here from cookies/local storage in Nuxt context
    }

    const response = await fetch(url, {
      ...options,
      headers: { ...defaultHeaders, ...options.headers }
    })

    if (!response.ok) {
      if (response.status === 401) {
        // redirect to login
      }
      throw new Error(`HTTP error! status: ${response.status}`)
    }

    return response.json()
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
