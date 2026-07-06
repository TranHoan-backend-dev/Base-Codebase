/**
 * Hook hỗ trợ dịch (i18n) cho các component Grid với cơ chế fallback an toàn.
 * Khi NextIntlClientProvider chưa được cấu hình hoặc key không tồn tại, hook sẽ trả về chuỗi mặc định.
 *
 * @created_at 05/07/2026
 * @author txhoan
 */

import { useTranslations } from "next-intl";

import viMessages from "@/messages/vi.json";

export interface GridTranslations {
  no_data: string;
  showing: string;
  in_total: string;
  records: string;
  previous_page: string;
  next_page: string;
  search: string;
  reset: string;
  select_placeholder: string;
  confirm_action_title: string;
  confirm_action_message: string;
  cancel: string;
  agree: string;
  action_success: string;
  action_error: string;
  grid_config_error: string;
  selected_count: string;
  index: string;
}

const defaultTranslations: GridTranslations = viMessages.grid;

export function useGridTranslations(): {
  t: (key: keyof GridTranslations, fallback?: string) => string;
} {
  let translator: ((key: string) => string) | null = null;

  try {
    // eslint-disable-next-line react-hooks/rules-of-hooks
    translator = useTranslations("grid");
  } catch {
    translator = null;
  }

  const t = (key: keyof GridTranslations, fallback?: string): string => {
    if (translator) {
      try {
        const val = translator(key);

        if (val && val !== `grid.${key}` && val !== key) {
          return val;
        }
      } catch {
        // Fallback below
      }
    }

    return fallback || defaultTranslations[key] || String(key);
  };

  return { t };
}
