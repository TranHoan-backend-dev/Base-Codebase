/**
 * Hook quản lý trạng thái mở/đóng cho BasePopup và BasePopupSidebar.
 * Cung cấp API đơn giản: open, close, toggle, setOpen.
 *
 * @created_at 09/07/2026
 * @author txhoan
 */

import { useCallback, useState } from "react";

interface UsePopupOptions {
  /** Trạng thái mở mặc định khi khởi tạo */
  defaultOpen?: boolean;
  /** Callback khi trạng thái mở/đóng thay đổi */
  onOpenChange?: (isOpen: boolean) => void;
}

interface UsePopupReturn {
  /** Trạng thái mở/đóng hiện tại */
  isOpen: boolean;
  /** Mở popup */
  open: () => void;
  /** Đóng popup */
  close: () => void;
  /** Toggle trạng thái mở/đóng */
  toggle: () => void;
  /** Set trạng thái trực tiếp (dùng để truyền vào onOpenChange) */
  setOpen: (value: boolean) => void;
}

/**
 * Hook quản lý trạng thái đóng/mở cho Popup.
 *
 * @example
 * ```tsx
 * const popup = usePopup();
 *
 * <Button onPress={popup.open}>Mở Popup</Button>
 * <BasePopup isOpen={popup.isOpen} onOpenChange={popup.setOpen}>
 *   ...
 * </BasePopup>
 * ```
 */
export function usePopup(options?: UsePopupOptions): UsePopupReturn {
  const { defaultOpen = false, onOpenChange } = options ?? {};
  const [isOpen, setIsOpenState] = useState<boolean>(defaultOpen);

  const setOpen = useCallback(
    (value: boolean) => {
      setIsOpenState(value);
      onOpenChange?.(value);
    },
    [onOpenChange],
  );

  const open = useCallback(() => setOpen(true), [setOpen]);
  const close = useCallback(() => setOpen(false), [setOpen]);
  const toggle = useCallback(() => setOpen(!isOpen), [setOpen, isOpen]);

  return { isOpen, open, close, toggle, setOpen };
}
