/**
 * Hook dịch i18n cho trang demo Popup.
 *
 * @created_at 09/07/2026
 * @author txhoan
 */

import { useTranslations } from "next-intl";

import viMessages from "@/messages/vi.json";

export type PopupDemoTranslations = typeof viMessages.popup_demo;
export type PopupDemoTranslationKey =
  | keyof PopupDemoTranslations
  | (string & {});

const defaultTranslations: Record<string, string> = viMessages.popup_demo;

export function usePopupDemoTranslations(): {
  t: (key: PopupDemoTranslationKey, fallback?: string) => string;
} {
  let translator: ((key: string) => string) | null = null;

  try {
    // eslint-disable-next-line react-hooks/rules-of-hooks
    translator = useTranslations("popup_demo");
  } catch {
    translator = null;
  }

  const t = (key: PopupDemoTranslationKey, fallback?: string): string => {
    if (translator) {
      try {
        const val = translator(key);

        if (val && val !== `popup_demo.${key}` && val !== key) {
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
