/**
 * Định nghĩa các kiểu dữ liệu cho BasePopup và BasePopupSidebar trong Nuxt 4 (NuxtUI v4).
 *
 * @created_at 09/07/2026
 * @author txhoan
 */

export type PopupSize = 'xs' | 'sm' | 'md' | 'lg' | 'xl' | 'full'

export type PopupSidebarSide = 'left' | 'right' | 'top' | 'bottom'

export interface PopupCloseConfig {
  visible?: boolean
  disabled?: boolean
}
