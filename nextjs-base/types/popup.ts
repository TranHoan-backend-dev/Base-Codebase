/**
 * Kích thước popup.
 * Mapping: xs → 320px, sm → 480px, md → 640px, lg → 800px, xl → 1024px, full → 100vw
 */
export type PopupSize = "xs" | "sm" | "md" | "lg" | "xl" | "full";

/**
 * Vị trí xuất hiện của PopupSidebar.
 */
export type PopupSidebarPlacement = "left" | "right";

/**
 * Kiểu backdrop overlay phía sau popup.
 */
export type PopupBackdropVariant = "opaque" | "blur" | "transparent";

/**
 * Hành vi cuộn nội dung bên trong popup.
 * - "inside": Chỉ body scroll, header/footer cố định.
 * - "outside": Toàn bộ popup scroll cùng page.
 */
export type PopupScrollBehavior = "inside" | "outside";

/**
 * Cấu hình cho nút đóng popup.
 */
export interface PopupCloseConfig {
  /** Hiển thị nút đóng hay không */
  visible: boolean;
  /** Icon tùy chỉnh (tên icon từ thư viện icon đã import) */
  icon?: string;
  /** ARIA label cho accessibility */
  ariaLabel?: string;
}

/**
 * Class tùy chỉnh cho từng vùng bên trong Popup.
 */
export interface PopupClassNames {
  backdrop?: string;
  container?: string;
  dialog?: string;
  header?: string;
  body?: string;
  footer?: string;
}

/**
 * Cấu hình chung cho cả BasePopup và BasePopupSidebar.
 */
export interface BasePopupConfig {
  /** Tiêu đề popup */
  title?: string;
  /** Mô tả phụ dưới tiêu đề */
  description?: string;
  /** Kích thước popup */
  size?: PopupSize;
  /** Kiểu backdrop */
  backdropVariant?: PopupBackdropVariant;
  /** Cho phép đóng bằng click backdrop hoặc ESC */
  dismissible?: boolean;
  /** Hành vi cuộn nội dung */
  scrollBehavior?: PopupScrollBehavior;
  /** Cấu hình nút đóng */
  closeButton?: PopupCloseConfig | boolean;
  /** Khóa scroll body khi popup mở */
  lockBodyScroll?: boolean;
  /** Nút phóng to toàn màn hình (Resize) */
  showFullscreenButton?: boolean;
  /** Hiển thị nút Lưu (Save) ở Footer mặc định */
  showSaveButton?: boolean;
}
