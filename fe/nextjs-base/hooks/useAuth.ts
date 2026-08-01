import { create } from "zustand";
import { UserProfile, authService } from "@/services/auth.service";
import { setCookieClient, removeCookieClient, ACCESS_TOKEN_KEY, REFRESH_TOKEN_KEY } from "@/utils/cookie";

interface AuthState {
  currentUser: UserProfile | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  login: (payload: any) => Promise<void>;
  logout: () => Promise<void>;
  loadProfile: () => Promise<void>;
}

/**
 * Zustand Store quản lý trạng thái authentication tập trung ở client cho ứng dụng Next.js.
 *
 * @created_at 01/08/2026
 * @author txhoan
 */
export const useAuth = create<AuthState>((set, get) => ({
  currentUser: null,
  isAuthenticated: false,
  isLoading: false,

  login: async (payload) => {
    set({ isLoading: true });
    try {
      const res = await authService.login(payload);
      setCookieClient(ACCESS_TOKEN_KEY, res.accessToken);
      setCookieClient(REFRESH_TOKEN_KEY, res.refreshToken);
      set({ isAuthenticated: true });
      await get().loadProfile();
    } finally {
      set({ isLoading: false });
    }
  },

  logout: async () => {
    set({ isLoading: true });
    try {
      const refreshToken = localStorage.getItem(REFRESH_TOKEN_KEY) || "";
      await authService.logout(refreshToken).catch(() => {});
    } finally {
      removeCookieClient(ACCESS_TOKEN_KEY);
      removeCookieClient(REFRESH_TOKEN_KEY);
      set({ currentUser: null, isAuthenticated: false, isLoading: false });
    }
  },

  loadProfile: async () => {
    try {
      const user = await authService.getProfile();
      set({ currentUser: user, isAuthenticated: true });
    } catch {
      set({ currentUser: null, isAuthenticated: false });
    }
  },
}));
