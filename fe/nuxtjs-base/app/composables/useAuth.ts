import { ref, computed } from "vue";
import { UserProfile, authService } from "../../services/auth.service";
import { setCookieClient, removeCookieClient, ACCESS_TOKEN_KEY, REFRESH_TOKEN_KEY } from "../../utils/cookie";

/**
 * Composable quản lý trạng thái Authentication & User Profile tập trung cho Nuxt.js (tương đương Zustand store của React).
 *
 * @created_at 01/08/2026
 * @author txhoan
 */
export const useAuth = () => {
  const currentUser = useState<UserProfile | null>("auth_user", () => null);
  const isLoading = useState<boolean>("auth_loading", () => false);
  const isAuthenticated = computed(() => currentUser.value !== null);

  const login = async (payload: any) => {
    isLoading.value = true;
    try {
      const res = await authService.login(payload);
      setCookieClient(ACCESS_TOKEN_KEY, res.accessToken);
      setCookieClient(REFRESH_TOKEN_KEY, res.refreshToken);
      await loadProfile();
    } finally {
      isLoading.value = false;
    }
  };

  const logout = async () => {
    isLoading.value = true;
    try {
      const refreshToken = getCookieClient(REFRESH_TOKEN_KEY) || "";
      await authService.logout(refreshToken).catch(() => { });
    } finally {
      removeCookieClient(ACCESS_TOKEN_KEY);
      removeCookieClient(REFRESH_TOKEN_KEY);
      currentUser.value = null;
      isLoading.value = false;
    }
  };

  const loadProfile = async () => {
    try {
      const user = await authService.getProfile();
      currentUser.value = user;
    } catch {
      currentUser.value = null;
    }
  };

  return {
    currentUser,
    isLoading,
    isAuthenticated,
    login,
    logout,
    loadProfile,
  };
};
