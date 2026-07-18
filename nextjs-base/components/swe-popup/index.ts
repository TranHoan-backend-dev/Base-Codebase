/**
 * Barrel export cho Popup components.
 *
 * @created_at 09/07/2026
 * @author txhoan
 */

export { BasePopup, usePopupContext } from "./popup/BasePopup";
export type { BasePopupProps } from "./popup/BasePopup";

export { BasePopupSidebar, useSidebarContext } from "./sidebar/BasePopupSidebar";
export type { BasePopupSidebarProps } from "./sidebar/BasePopupSidebar";

export { usePopupTranslations } from "@/hooks/usePopupTranslations";
