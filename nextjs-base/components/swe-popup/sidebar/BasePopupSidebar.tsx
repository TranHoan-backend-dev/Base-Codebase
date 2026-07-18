/**
 * BasePopupSidebar — Smart Container component cho Slide-over / Drawer sidebar.
 * Wrap HeroUI v3.2.1 Drawer (compound pattern) với API thống nhất.
 *
 * Hỗ trợ cả 2 pattern:
 * - Flat Props: title, footer render props (quick use)
 * - Compound: BasePopupSidebar.Header / .Body / .Footer (full control)
 *
 * @created_at 09/07/2026
 * @author txhoan
 */

"use client";

import type {
  PopupSize,
  PopupBackdropVariant,
  PopupSidebarPlacement,
  PopupCloseConfig,
  PopupClassNames,
} from "@/types/popup";

import { Drawer, Button } from "@heroui/react";
import React, {
  type ReactNode,
  useCallback,
  useState,
  createContext,
  useContext,
} from "react";

import { ExpandIcon, CollapseIcon } from "@/components/icons";
import { usePopupTranslations } from "@/hooks/usePopupTranslations";

import "./swe-base-popup-sidebar.scss";

// ─── Constants ──────────────────────────────────────────────────────────────

const SIDEBAR_WIDTH_MAP: Record<PopupSize, string> = {
  xs: "320px",
  sm: "380px",
  md: "420px",
  lg: "520px",
  xl: "640px",
  full: "100vw",
};

// ─── Context ────────────────────────────────────────────────────────────────

interface SidebarContextValue {
  close: () => void;
}

const SidebarContext = createContext<SidebarContextValue>({ close: () => { } });

// ─── Props ──────────────────────────────────────────────────────────────────

export interface BasePopupSidebarProps {
  /** Trạng thái mở/đóng (controlled) */
  isOpen: boolean;
  /** Callback khi trạng thái thay đổi */
  onOpenChange: (isOpen: boolean) => void;
  /** Tiêu đề header (flat mode) */
  title?: string;
  /** Mô tả dưới tiêu đề (flat mode) */
  description?: string;
  /** Kích thước sidebar */
  size?: PopupSize;
  /** Hướng trượt vào */
  placement?: PopupSidebarPlacement;
  /** Kiểu backdrop overlay */
  backdropVariant?: PopupBackdropVariant;
  /** Cho phép đóng bằng ESC/click outside */
  dismissible?: boolean;
  /** Cấu hình nút đóng */
  closeButton?: PopupCloseConfig | boolean;
  /** Hiển thị drag handle */
  showHandle?: boolean;
  /** Hiển thị nút phóng to toàn màn hình */
  showFullscreenButton?: boolean;
  /** Hiển thị nút Lưu ở Footer mặc định */
  showSaveButton?: boolean;
  /** Chiều rộng tùy chỉnh (ghi đè size) */
  width?: string;
  /** Callback sau khi animation mở hoàn tất */
  onAfterOpen?: () => void;
  /** Callback sau khi animation đóng hoàn tất */
  onAfterClose?: () => void;
  /** Callback trước khi đóng, return false để ngăn */
  onBeforeClose?: () => boolean | Promise<boolean>;
  /** Callback khi user cố đóng nhưng dismissible=false */
  onClosePrevent?: () => void;
  /** Callback khi bấm nút Hủy */
  onCancel?: () => void;
  /** Callback khi bấm nút Lưu */
  onSave?: () => void;
  /** Custom footer (flat mode, ghi đè footer mặc định) */
  footer?: ReactNode;
  /** Custom header (flat mode, ghi đè title/description) */
  header?: ReactNode;
  /** Label nút Hủy */
  cancelLabel?: string;
  /** Label nút Lưu */
  saveLabel?: string;
  /** Class CSS ngoài cùng */
  className?: string;
  /** Class tùy chỉnh cho từng vùng */
  classNames?: PopupClassNames;
  /** Nội dung body / compound children */
  children: ReactNode;
}

// ─── Sub-components (Compound Pattern) ──────────────────────────────────────

function SidebarHeader({
  children,
  className = "",
}: {
  children: ReactNode;
  className?: string;
}) {
  return (
    <Drawer.Header
      className={`swe_popup_header_compound ${className}`.trim()}
    >
      {children}
    </Drawer.Header>
  );
}
SidebarHeader.displayName = "BasePopupSidebar.Header";

function SidebarBody({
  children,
  className = "",
}: {
  children: ReactNode;
  className?: string;
}) {
  return (
    <Drawer.Body className={`swe_popup_body_compound ${className}`.trim()}>
      {children}
    </Drawer.Body>
  );
}
SidebarBody.displayName = "BasePopupSidebar.Body";

function SidebarFooter({
  children,
  className = "",
}: {
  children: ReactNode;
  className?: string;
}) {
  return (
    <Drawer.Footer className={`swe_popup_footer ${className}`.trim()}>
      {children}
    </Drawer.Footer>
  );
}
SidebarFooter.displayName = "BasePopupSidebar.Footer";

// ─── Helpers ────────────────────────────────────────────────────────────────

function hasCompoundChildren(children: ReactNode): boolean {
  let found = false;

  React.Children.forEach(children, (child) => {
    if (React.isValidElement(child)) {
      const childType = child.type;

      if (
        childType === SidebarHeader ||
        childType === SidebarBody ||
        childType === SidebarFooter
      ) {
        found = true;
      }
    }
  });

  return found;
}

// ─── Main Component ─────────────────────────────────────────────────────────

function BasePopupSidebarRoot({
  isOpen,
  onOpenChange,
  title,
  description,
  size = "md",
  placement = "right",
  backdropVariant = "opaque",
  dismissible = true,
  closeButton = true,
  showHandle = false,
  showFullscreenButton = false,
  showSaveButton = true,
  width,
  onAfterOpen,
  onAfterClose,
  onBeforeClose,
  onClosePrevent,
  onCancel,
  onSave,
  footer,
  header,
  cancelLabel,
  saveLabel,
  className,
  classNames,
  children,
}: BasePopupSidebarProps): React.JSX.Element {
  const { t } = usePopupTranslations();
  const resolvedCancelLabel = cancelLabel ?? t("cancel", "Hủy");
  const resolvedSaveLabel = saveLabel ?? t("save", "Lưu");
  const [isFullscreen, setIsFullscreen] = useState(false);

  const resolvedWidth = isFullscreen
    ? "100vw"
    : width || SIDEBAR_WIDTH_MAP[size] || SIDEBAR_WIDTH_MAP.md;

  const handleOpenChange = useCallback(
    async (nextOpen: boolean) => {
      if (!nextOpen && onBeforeClose) {
        const canClose = await Promise.resolve(onBeforeClose());

        if (!canClose) return;
      }

      if (!nextOpen && !dismissible) {
        onClosePrevent?.();

        return;
      }

      onOpenChange(nextOpen);

      if (nextOpen) {
        onAfterOpen?.();
      } else {
        onAfterClose?.();
      }
    },
    [onOpenChange, onBeforeClose, onAfterOpen, onAfterClose],
  );

  const handleCancel = useCallback(() => {
    onCancel?.();
    handleOpenChange(false);
  }, [onCancel, handleOpenChange]);

  const handleSave = useCallback(() => {
    onSave?.();
  }, [onSave]);

  const toggleFullscreen = useCallback(() => {
    setIsFullscreen((prev) => !prev);
  }, []);

  const close = useCallback(() => {
    handleOpenChange(false);
  }, [handleOpenChange]);

  const isCompound = hasCompoundChildren(children);
  const showCloseButton =
    typeof closeButton === "boolean" ? closeButton : closeButton.visible;

  return (
    <SidebarContext.Provider value={{ close }}>
      <Drawer>
        <Drawer.Backdrop
          className={classNames?.backdrop}
          isDismissable={dismissible}
          isOpen={isOpen}
          variant={backdropVariant}
          onOpenChange={handleOpenChange}
        >
          <Drawer.Content
            className={`${classNames?.container || ""} rounded-none`.trim()}
            placement={placement}
          >
            <Drawer.Dialog
              aria-label={title || t("sidebar_label", "Sidebar")}
              className={
                `swe_sidebar_dialog ${className || ""} ${classNames?.dialog || ""}`.trim()
              }
              style={{
                width: resolvedWidth,
              }}
            >
              {showHandle && <Drawer.Handle />}

              {isCompound ? (
                children
              ) : (
                <>
                  {header || (
                    <Drawer.Header
                      className={`swe_popup_header_flat border-b ${classNames?.header || ""}`.trim()}
                    >
                      <div className="swe_popup_header_content flex flex-col gap-2">
                        {title && (
                          <Drawer.Heading className="swe_popup_title">
                            {title}
                          </Drawer.Heading>
                        )}
                        {description && (
                          <p className="swe_popup_description">
                            {description}
                          </p>
                        )}
                      </div>
                      <div className="swe_popup_header_actions">
                        {showFullscreenButton && (
                          <Button
                            aria-label={
                              isFullscreen
                                ? t("collapse", "Thu nhỏ")
                                : t("expand", "Phóng to")
                            }
                            isIconOnly
                            size="sm"
                            variant="ghost"
                            onPress={toggleFullscreen}
                          >
                            {isFullscreen ? (
                              <CollapseIcon size={16} />
                            ) : (
                              <ExpandIcon size={16} />
                            )}
                          </Button>
                        )}
                        {showCloseButton && (
                          <Drawer.CloseTrigger
                            className="swe_popup_close_trigger"
                          />
                        )}
                      </div>
                    </Drawer.Header>
                  )}

                  <Drawer.Body
                    className={`swe_popup_body_flat ${classNames?.body || ""}`.trim()}
                  >
                    {children}
                  </Drawer.Body>

                  {footer !== undefined ? (
                    <Drawer.Footer
                      className={`swe_popup_footer ${classNames?.footer || ""}`.trim()}
                    >
                      {footer}
                    </Drawer.Footer>
                  ) : (
                    <Drawer.Footer
                      className={`swe_popup_footer ${classNames?.footer || ""}`.trim()}
                    >
                      <Button variant="secondary" onPress={handleCancel}>
                        {resolvedCancelLabel}
                      </Button>
                      {showSaveButton && (
                        <Button variant="primary" onPress={handleSave}>
                          {resolvedSaveLabel}
                        </Button>
                      )}
                    </Drawer.Footer>
                  )}
                </>
              )}
            </Drawer.Dialog>
          </Drawer.Content>
        </Drawer.Backdrop>
      </Drawer>
    </SidebarContext.Provider>
  );
}

// ─── Compound Export ────────────────────────────────────────────────────────

/**
 * Hook lấy context sidebar bên trong compound children.
 */
export function useSidebarContext(): SidebarContextValue {
  return useContext(SidebarContext);
}

export const BasePopupSidebar = Object.assign(BasePopupSidebarRoot, {
  Header: SidebarHeader,
  Body: SidebarBody,
  Footer: SidebarFooter,
});

export default BasePopupSidebar;
