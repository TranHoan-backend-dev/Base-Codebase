import { baseService } from "./core/BaseService";

export interface LoginRequest {
  username: string;
  password: string;
  deviceId?: string;
  deviceInfo?: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
}

export interface UserProfile {
  id: string;
  username: string;
  email: string;
  avatar?: string;
  roles: string[];
}

/**
 * Service xác thực API người dùng Nuxt.js, kết nối trực tiếp với Spring Boot backend endpoints.
 *
 * @created_at 01/08/2026
 * @author txhoan
 */
export class AuthService {
  public login(payload: LoginRequest): Promise<AuthResponse> {
    return baseService.post<AuthResponse>("/api/auth/login", payload);
  }

  public logout(refreshToken: string): Promise<void> {
    return baseService.post<void>("/api/auth/logout", { token: refreshToken });
  }

  public getProfile(): Promise<UserProfile> {
    return baseService.get<UserProfile>("/api/auth/me");
  }

  public changePassword(payload: any): Promise<any> {
    return baseService.post("/api/auth/change-password", payload);
  }
}

export const authService = new AuthService();
