"use client";

import React, { type ComponentProps, useRef, useState, useEffect } from "react";
import { Button, Tooltip, Badge } from "@heroui/react";
import { sanitizeHtml } from "@/utils/security";

/**
 * Interface định nghĩa các thuộc tính và sự kiện (events) cho thành phần SweButton.
 * Kế thừa toàn bộ sự kiện tiêu chuẩn từ HeroUI Button (như onPress, onClick, onKeyDown, onFocus, v.v.).
 *
 * @created_at 19/07/2026
 * @author txhoan
 */
export interface SweButtonProps
  extends Omit<ComponentProps<typeof Button>, "size" | "variant" | "children"> {
  /** Tiêu đề hiển thị trên nút */
  btnTitle?: string;
  /** Kích thước nút: 'sm' | 'md' | 'lg' */
  btnSize?: ComponentProps<typeof Button>["size"];
  /** Biến thể giao diện của nút */
  variant?: ComponentProps<typeof Button>["variant"] | "danger-soft";
  /** Icon hiển thị kèm theo trước hoặc sau tiêu đề */
  icon?: React.ReactNode;
  /** Vị trí hiển thị của Icon: 'left' | 'right' (Mặc định: 'left') */
  iconPosition?: "left" | "right";
  /** Văn bản hiển thị thay thế cho btnTitle khi nút đang ở trạng thái loading (isPending=true) */
  loadingText?: string;
  /** Nội dung Tooltip hiển thị khi di chuột hoặc focus vào nút */
  tooltip?: React.ReactNode;
  /** Nội dung chuỗi HTML hiển thị trong Tooltip (đã được làm sạch an toàn XSS) */
  tooltipHtml?: string;
  /** Số lượng nhãn thông báo (Badge) hiển thị ở góc nút */
  badgeCount?: number;
  /**
   * Cho phép giữ Tooltip luôn mở khi người dùng di chuột xuống vùng nội dung Tooltip.
   * Khi bật prop này, Tooltip sẽ không tự động đóng ngay khi rời chuột khỏi nút mà chờ đợi kiểm tra di chuyển chuột vào Tooltip.
   */
  keepTooltipOpenOnHover?: boolean;
  /** Thời gian trễ đóng Tooltip (ms) khi keepTooltipOpenOnHover được bật (Mặc định: 300ms) */
  tooltipCloseDelay?: number;
}

/**
 * Thành phần nút bấm tái sử dụng (SweButton) dựa trên HeroUI Button.
 * Hỗ trợ đầy đủ các sự kiện như onPress, onClick, onKeyDown, onFocus cùng các tính năng nâng cao: Tooltip, Badge, Loading Text.
 *
 * @param props Các thuộc tính và sự kiện truyền vào SweButton
 */
const SweButton: React.FC<SweButtonProps> = ({
  btnSize = "md",
  variant = "outline",
  btnTitle = "",
  icon,
  iconPosition = "left",
  loadingText,
  tooltip,
  tooltipHtml,
  badgeCount,
  keepTooltipOpenOnHover = false,
  tooltipCloseDelay = 300,
  isPending,
  ...restProps
}) => {
  const [isTooltipOpen, setIsTooltipOpen] = useState(false);
  const timeoutRef = useRef<NodeJS.Timeout | null>(null);

  const clearCloseTimeout = () => {
    if (timeoutRef.current) {
      clearTimeout(timeoutRef.current);
      timeoutRef.current = null;
    }
  };

  useEffect(() => {
    return () => clearCloseTimeout();
  }, []);

  const hasTooltip = Boolean(tooltip || tooltipHtml);

  const handleMouseEnterTrigger = (
    e: React.MouseEvent<HTMLButtonElement>
  ) => {
    if (keepTooltipOpenOnHover && hasTooltip) {
      clearCloseTimeout();
      setIsTooltipOpen(true);
    }
    if (restProps.onMouseEnter) {
      restProps.onMouseEnter(e);
    }
  };

  const handleMouseLeaveTrigger = (
    e: React.MouseEvent<HTMLButtonElement>
  ) => {
    if (keepTooltipOpenOnHover && hasTooltip) {
      clearCloseTimeout();
      timeoutRef.current = setTimeout(() => {
        setIsTooltipOpen(false);
      }, tooltipCloseDelay);
    }
    if (restProps.onMouseLeave) {
      restProps.onMouseLeave(e);
    }
  };

  const handleMouseEnterContent = () => {
    if (keepTooltipOpenOnHover) {
      clearCloseTimeout();
      setIsTooltipOpen(true);
    }
  };

  const handleMouseLeaveContent = () => {
    if (keepTooltipOpenOnHover) {
      clearCloseTimeout();
      timeoutRef.current = setTimeout(() => {
        setIsTooltipOpen(false);
      }, tooltipCloseDelay);
    }
  };

  const displayText = isPending && loadingText ? loadingText : btnTitle;

  const buttonContent = (
    <>
      {icon && iconPosition === "left" && icon}
      {displayText}
      {icon && iconPosition === "right" && icon}
    </>
  );

  let buttonElement = (
    <Button
      size={btnSize}
      variant={variant as ComponentProps<typeof Button>["variant"]}
      isPending={isPending}
      onMouseEnter={keepTooltipOpenOnHover ? handleMouseEnterTrigger : restProps.onMouseEnter}
      onMouseLeave={keepTooltipOpenOnHover ? handleMouseLeaveTrigger : restProps.onMouseLeave}
      {...restProps}
    >
      {buttonContent}
    </Button>
  );

  if (typeof badgeCount === "number") {
    buttonElement = (
      <Badge.Anchor>
        {buttonElement}
        <Badge color="danger">{badgeCount}</Badge>
      </Badge.Anchor>
    );
  }

  if (hasTooltip) {
    const tooltipRenderContent = tooltipHtml ? (
      <div dangerouslySetInnerHTML={{ __html: sanitizeHtml(tooltipHtml) }} />
    ) : (
      tooltip
    );

    buttonElement = (
      <Tooltip
        closeDelay={tooltipCloseDelay}
        isOpen={keepTooltipOpenOnHover ? isTooltipOpen : undefined}
        onOpenChange={keepTooltipOpenOnHover ? setIsTooltipOpen : undefined}
      >
        <Tooltip.Trigger>{buttonElement}</Tooltip.Trigger>
        <Tooltip.Content
          className={keepTooltipOpenOnHover ? "pointer-events-auto" : undefined}
          onMouseEnter={keepTooltipOpenOnHover ? handleMouseEnterContent : undefined}
          onMouseLeave={keepTooltipOpenOnHover ? handleMouseLeaveContent : undefined}
        >
          {tooltipRenderContent}
        </Tooltip.Content>
      </Tooltip>
    );
  }

  return buttonElement;
};

export default SweButton;