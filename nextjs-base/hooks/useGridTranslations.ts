/**
 * Hook hỗ trợ dịch (i18n) cho các component Grid với cơ chế fallback an toàn.
 * Khi NextIntlClientProvider chưa được cấu hình hoặc key không tồn tại, hook sẽ trả về chuỗi mặc định.
 *
 * @created_at 05/07/2026
 * @author txhoan
 */

import { useTranslations } from "next-intl";

import viMessages from "@/messages/vi.json";

export type GridTranslations = typeof viMessages.grid;
export type GridTranslationKey = keyof GridTranslations | (string & {});

const defaultTranslations: Record<string, string> = viMessages.grid;

/**
 * Hook cung cấp phương thức dịch i18n cho toàn bộ hệ thống Dynamic Grid.
 * Tự động đồng bộ type từ file vi.json và có cơ chế fallback khi thiếu context next-intl.
 *
 * @returns Object chứa phương thức dịch `t`.
 */
export function useGridTranslations(): {
  /**
   * Phương thức dịch (translate) một key sang chuỗi tương ứng.
   *
   * @param key - Mã key cần dịch (tự động gợi ý từ viMessages.grid hoặc chuỗi tự do).
   * @param valuesOrFallback - Object chứa các biến động để nội suy (VD: `{ count: 5 }`) hoặc chuỗi fallback nếu truyền string.
   * @param fallback - Chuỗi fallback mặc định nếu không tìm thấy bản dịch và tham số thứ 2 là object biến.
   * @returns Chuỗi văn bản đã được dịch hoặc nội suy.
   */
  t: (
    key: GridTranslationKey,
    valuesOrFallback?: Record<string, string | number> | string,
    fallback?: string,
  ) => string;
} {
  let translator:
    | ((key: string, values?: Record<string, string | number>) => string)
    | null = null;

  try {
    // eslint-disable-next-line react-hooks/rules-of-hooks
    translator = useTranslations("grid");
  } catch {
    translator = null;
  }

  /**
   * Xử lý dịch và thay thế biến nội suy theo cú pháp {variableName}.
   */
  const t = (
    key: GridTranslationKey,
    valuesOrFallback?: Record<string, string | number> | string,
    fallback?: string,
  ): string => {
    const values =
      typeof valuesOrFallback === "object" ? valuesOrFallback : undefined;
    const fb =
      typeof valuesOrFallback === "string" ? valuesOrFallback : fallback;

    if (translator) {
      try {
        const val = translator(key, values);

        if (val && val !== `grid.${key}` && val !== key) {
          return val;
        }
      } catch {
        // Fallback bên dưới khi next-intl xảy ra ngoại lệ
      }
    }

    let result = fb || defaultTranslations[key] || String(key);

    if (values && typeof result === "string") {
      Object.entries(values).forEach(([k, v]) => {
        result = result.replace(new RegExp(`\\{${k}\\}`, "g"), String(v));
      });
    }

    return result;
  };

  return { t };
}
