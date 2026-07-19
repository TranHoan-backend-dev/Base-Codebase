/**
 * BasePopup — Smart Container component cho Modal/Popup center.
 * Wrap HeroUI v3.2.1 Modal (compound pattern) với API thống nhất
 * cho các project con kế thừa.
 *
 * Hỗ trợ cả 2 pattern:
 * - Flat Props: title, footer render props (quick use)
 * - Compound: BasePopup.Header / BasePopup.Body / BasePopup.Footer (full control)
 *
 * @created_at 09/07/2026
 * @author txhoan
 */

"use client";

import type {
  PopupSize,
  PopupBackdropVariant,
  PopupScrollBehavior,
  PopupCloseConfig,
  PopupClassNames,
} from "@/types/popup";

import { Button } from "@heroui/react";
import {
  Modal,
  ModalRoot,
  ModalBackdrop,
  ModalContainer,
  ModalDialog,
  ModalHeader as HeroModalHeader,
  ModalHeading,
  ModalBody as HeroModalBody,
  ModalFooter as HeroModalFooter,
} from "@heroui/react/modal";
import React, {
  type ReactNode,
  useCallback,
  useState,
  createContext,
  useContext,
} from "react";

import { ExpandIcon, CollapseIcon } from "@/components/icons";
import { usePopupTranslations } from "@/hooks/usePopupTranslations";

import "./swe-base-popup.scss";

// ─── Constants ──────────────────────────────────────────────────────────────

const POPUP_SIZE_MAP: Record<PopupSize, string> = {
  xs: "xs",
  sm: "sm",
  md: "md",
  lg: "lg",
  xl: "cover",
  full: "full",
};

// ─── Context ────────────────────────────────────────────────────────────────

interface PopupContextValue {
  close: () => void;
}

const PopupContext = createContext<PopupContextValue>({ close: () => { } });

// ─── Props ──────────────────────────────────────────────────────────────────

export interface BasePopupProps {
  /** Trạng thái mở/đóng (controlled) */
  isOpen: boolean;
  /** Callback khi trạng thái thay đổi */
  onOpenChange: (isOpen: boolean) => void;
  /** Tiêu đề header (flat mode) */
  title?: string;
  /** Mô tả dưới tiêu đề (flat mode) */
  description?: string;
  /** Kích thước popup */
  size?: PopupSize;
  /** Vị trí trên màn hình */
  placement?: "auto" | "center" | "top" | "bottom";
  /** Kiểu backdrop overlay */
  backdropVariant?: PopupBackdropVariant;
  /** Cho phép đóng bằng ESC/click outside */
  dismissible?: boolean;
  /** Hành vi cuộn nội dung */
  scrollBehavior?: PopupScrollBehavior;
  /** Cấu hình nút đóng */
  closeButton?: PopupCloseConfig | boolean;
  /** Khóa scroll body */
  lockBodyScroll?: boolean;
  /** Hiển thị nút phóng to toàn màn hình */
  showFullscreenButton?: boolean;
  /** Hiển thị nút Lưu ở Footer mặc định */
  showSaveButton?: boolean;
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

function PopupHeader({
  children,
  className = "",
}: {
  children: ReactNode;
  className?: string;
}) {
  return (
    <HeroModalHeader
      className={`swe_popup_header_compound ${className}`.trim()}
      data-testid="swe-popup-header"
    >
      {children}
    </HeroModalHeader>
  );
}
PopupHeader.displayName = "BasePopup.Header";

function PopupBody({
  children,
  className = "",
}: {
  children: ReactNode;
  className?: string;
}) {
  return (
    <HeroModalBody
      className={`swe_popup_body_compound ${className}`.trim()}
      data-testid="swe-popup-body"
    >
      {children}
    </HeroModalBody>
  );
}
PopupBody.displayName = "BasePopup.Body";

function PopupFooter({
  children,
  className = "",
}: {
  children: ReactNode;
  className?: string;
}) {
  return (
    <HeroModalFooter className={`swe_popup_footer ${className}`.trim()} data-testid="swe-popup-footer">
      {children}
    </HeroModalFooter>
  );
}
PopupFooter.displayName = "BasePopup.Footer";

// ─── Helpers ────────────────────────────────────────────────────────────────

/**
 * Kiểm tra children có chứa compound sub-components (Header/Body/Footer) hay không.
 */
function hasCompoundChildren(children: ReactNode): boolean {
  let found = false;

  React.Children.forEach(children, (child) => {
    if (React.isValidElement(child)) {
      const childType = child.type;

      if (
        childType === PopupHeader ||
        childType === PopupBody ||
        childType === PopupFooter
      ) {
        found = true;
      }
    }
  });

  return found;
}

// ─── Main Component ─────────────────────────────────────────────────────────

function BasePopupRoot({
  isOpen,
  onOpenChange,
  title,
  description,
  size = "md",
  placement = "center",
  backdropVariant = "opaque",
  dismissible = true,
  scrollBehavior = "inside",
  closeButton = true,
  showFullscreenButton = false,
  showSaveButton = true,
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
}: BasePopupProps): React.JSX.Element {
  const { t } = usePopupTranslations();
  const resolvedCancelLabel = cancelLabel ?? t("cancel", "Hủy");
  const resolvedSaveLabel = saveLabel ?? t("save", "Lưu");
  const [isFullscreen, setIsFullscreen] = useState(false);

  const resolvedSize = isFullscreen ? "full" : POPUP_SIZE_MAP[size] || "md";

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
    [
      onOpenChange,
      onBeforeClose,
      dismissible,
      onClosePrevent,
      onAfterOpen,
      onAfterClose,
    ],
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
    <PopupContext.Provider value={{ close }}>
      <ModalRoot isOpen={isOpen} onOpenChange={handleOpenChange}>
        <ModalBackdrop
          className={classNames?.backdrop}
          isDismissable={dismissible}
          variant={backdropVariant}
        >
          <ModalContainer
            className={classNames?.container}
            placement={placement}
            scroll={scrollBehavior}
            size={resolvedSize as "xs" | "sm" | "md" | "lg" | "cover" | "full"}
          >
            <ModalDialog
              aria-label={title || t("popup_label", "Popup")}
              className={
                `swe_popup_dialog ${isFullscreen ? "fullscreen" : ""} ${className || ""} ${classNames?.dialog || ""}`.trim()
              }
              data-testid="swe-popup-dialog"
            >
              {isCompound ? (
                children
              ) : (
                <>
                  {header || (
                    <HeroModalHeader
                      className={`swe_popup_header_flat border-b ${classNames?.header || ""}`.trim()}
                      data-testid="swe-popup-header"
                    >
                      <div className="swe_popup_header_content flex flex-col gap-2">
                        {title && (
                          <ModalHeading className="swe_popup_title" data-testid="swe-popup-title">
                            {title}
                          </ModalHeading>
                        )}
                        {description && (
                          <p className="swe_popup_description" data-testid="swe-popup-description">
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
                            data-testid="swe-popup-fullscreen-btn"
                          >
                            {isFullscreen ? (
                              <CollapseIcon size={16} />
                            ) : (
                              <ExpandIcon size={16} />
                            )}
                          </Button>
                        )}
                        {showCloseButton && (
                          <Modal.CloseTrigger
                            className="swe_popup_close_trigger"
                            data-testid="swe-popup-close-btn"
                          />
                        )}
                      </div>
                    </HeroModalHeader>
                  )}

                  <HeroModalBody
                    className={`swe_popup_body_flat ${classNames?.body || ""}`.trim()}
                    data-testid="swe-popup-body"
                  >
                    {children}
                  </HeroModalBody>

                  {footer !== undefined ? (
                    <HeroModalFooter
                      className={`swe_popup_footer ${classNames?.footer || ""}`.trim()}
                      data-testid="swe-popup-footer"
                    >
                      {footer}
                    </HeroModalFooter>
                  ) : (
                    <HeroModalFooter
                      className={`swe_popup_footer ${classNames?.footer || ""}`.trim()}
                      data-testid="swe-popup-footer"
                    >
                      <Button variant="secondary" onPress={handleCancel} data-testid="swe-popup-cancel-btn">
                        {resolvedCancelLabel}
                      </Button>
                      {showSaveButton && (
                        <Button variant="primary" onPress={handleSave} data-testid="swe-popup-save-btn">
                          {resolvedSaveLabel}
                        </Button>
                      )}
                    </HeroModalFooter>
                  )}
                </>
              )}
            </ModalDialog>
          </ModalContainer>
        </ModalBackdrop>
      </ModalRoot>
    </PopupContext.Provider>
  );
}

// ─── Compound Export ────────────────────────────────────────────────────────

/**
 * Hook lấy context popup bên trong compound children.
 * Cho phép sub-component gọi close() từ bên trong.
 */
export function usePopupContext(): PopupContextValue {
  return useContext(PopupContext);
}

export const SweBasePopup = Object.assign(BasePopupRoot, {
  Header: PopupHeader,
  Body: PopupBody,
  Footer: PopupFooter,
});

export default SweBasePopup;
