/**
 * Component hiển thị nút thao tác (Action Button) cho Dynamic Grid.
 * Hỗ trợ hiển thị Modal xác nhận nếu cấu hình có confirmMessage, gọi API qua baseService.
 *
 * @created_at 06/07/2026
 * @author txhoan
 * @references plan/nextjs-grid-layout.md
 */

import { Button } from "@heroui/react";
import {
  ModalRoot,
  ModalBackdrop,
  ModalContainer,
  ModalDialog,
  ModalHeader,
  ModalHeading,
  ModalBody,
  ModalFooter,
} from "@heroui/react/modal";
import React, { useState } from "react";

import { useGridTranslations } from "@/hooks/useGridTranslations";
import { baseService } from "@/services/core/BaseService";
import { GridActionConfig } from "@/types/grid";

export interface GridActionProps {
  /** Cấu hình hành động */
  action: GridActionConfig;
  /** Danh sách ID các dòng đang được tích chọn */
  selectedKeys?: Set<string>;
  /** Callback kích hoạt khi gọi API thành công */
  onSuccess?: (actionCode: string, result: unknown) => void;
}

/**
 * GridAction thực thi hành động gọi API và hiển thị Toast/thông báo qua onSuccess callback.
 */
export const SweGridAction = ({
  action,
  selectedKeys,
  onSuccess,
}: GridActionProps): React.JSX.Element => {
  const { t } = useGridTranslations();
  const [isOpen, setIsOpen] = useState(false);
  const [isProcessing, setIsProcessing] = useState(false);

  const onOpen = () => setIsOpen(true);
  const onClose = () => setIsOpen(false);

  /**
   * Ánh xạ cấu hình loại nút (buttonType) từ backend sang variant tương thích với HeroUI v3.2.1.
   *
   * @param type - Loại nút từ config (danger, secondary, tertiary, ghost, outline, primary).
   * @returns Object chứa thuộc tính variant hợp lệ cho HeroUI Button.
   */
  const getButtonProps = (type?: string) => {
    switch (type) {
      case "danger":
        return { variant: "danger" as const };
      case "secondary":
        return { variant: "secondary" as const };
      case "tertiary":
      case "ghost":
        return { variant: "ghost" as const };
      case "outline":
        return { variant: "outline" as const };
      case "primary":
      default:
        return { variant: "primary" as const };
    }
  };

  /**
   * Thực thi gọi API hành động xuống Backend dựa theo method cấu hình (GET, POST, DELETE).
   * Tự động đính kèm danh sách các ID bản ghi đang được chọn (nếu có).
   */
  const executeAction = async (): Promise<void> => {
    setIsProcessing(true);
    try {
      const { actionConfig } = action;
      let result: unknown;
      const endpoint = actionConfig.endpoint || actionConfig.target || "";

      if (actionConfig.method === "DELETE") {
        result = await baseService.delete(endpoint);
      } else if (actionConfig.method === "POST") {
        result = await baseService.post(endpoint, {
          ids: selectedKeys ? Array.from(selectedKeys) : [],
        });
      } else {
        result = await baseService.get(endpoint);
      }

      onSuccess?.(action.code, result);
    } catch {
      // Error được xử lý bởi BaseService global error handler
    } finally {
      setIsProcessing(false);
      onClose();
    }
  };

  /**
   * Xử lý sự kiện khi người dùng bấm vào nút hành động.
   * Nếu cấu hình có yêu cầu xác nhận (confirmMessage), hiển thị Dialog xác nhận trước khi gọi API.
   */
  const handlePress = (): void => {
    if (action.actionConfig.confirmMessage) {
      onOpen();
    } else {
      executeAction();
    }
  };

  return (
    <>
      <Button
        {...getButtonProps(action.buttonType)}
        isDisabled={isProcessing}
        onPress={handlePress}
      >
        {action.label}
      </Button>

      <ModalRoot isOpen={isOpen} onOpenChange={setIsOpen}>
        <ModalBackdrop>
          <ModalContainer>
            <ModalDialog>
              <ModalHeader>
                <ModalHeading>{t("confirm_action_title")}</ModalHeading>
              </ModalHeader>
              <ModalBody>{action.actionConfig.confirmMessage}</ModalBody>
              <ModalFooter>
                <Button variant="secondary" onPress={onClose}>
                  {t("cancel")}
                </Button>
                <Button
                  {...getButtonProps(action.buttonType)}
                  isDisabled={isProcessing}
                  onPress={executeAction}
                >
                  {t("agree")}
                </Button>
              </ModalFooter>
            </ModalDialog>
          </ModalContainer>
        </ModalBackdrop>
      </ModalRoot>
    </>
  );
}
