/**
 * Hook hỗ trợ dịch (i18n) cho các component Popup với cơ chế fallback an toàn.
 *
 * @created_at 09/07/2026
 * @author txhoan
 */

import { useTranslations } from "next-intl";

import viMessages from "@/messages/vi.json";

export type PopupTranslations = typeof viMessages.popup;
export type PopupTranslationKey = keyof PopupTranslations | (string & {});

const defaultTranslations: Record<string, string> = viMessages.popup;

export function usePopupTranslations(): {
  t: (key: PopupTranslationKey, fallback?: string) => string;
} {
  let translator: ((key: string) => string) | null = null;

  try {
    // eslint-disable-next-line react-hooks/rules-of-hooks
    translator = useTranslations("popup");
  } catch {
    translator = null;
  }

  const t = (key: PopupTranslationKey, fallback?: string): string => {
    if (translator) {
      try {
        const val = translator(key);

        if (val && val !== `popup.${key}` && val !== key) {
          return val;
        }
      } catch {
        // Fallback
      }
    }

    return fallback || defaultTranslations[key] || String(key);
  };

  return { t };
}
