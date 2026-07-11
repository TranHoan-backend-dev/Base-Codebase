/**
 * Composable quản lý trạng thái mở/đóng cho BasePopup và BasePopupSidebar (Nuxt 4).
 * Cung cấp API đơn giản: isOpen (Ref), open(), close(), toggle().
 *
 * @created_at 09/07/2026
 * @author txhoan
 */

import { ref } from 'vue'
import type { Ref } from 'vue'

export interface UsePopupReturn {
  isOpen: Ref<boolean>
  open: () => void
  close: () => void
  toggle: () => void
}

export function usePopup(defaultOpen = false): UsePopupReturn {
  const isOpen = ref<boolean>(defaultOpen)

  const open = () => {
    isOpen.value = true
  }

  const close = () => {
    isOpen.value = false
  }

  const toggle = () => {
    isOpen.value = !isOpen.value
  }

  return {
    isOpen,
    open,
    close,
    toggle
  }
}
