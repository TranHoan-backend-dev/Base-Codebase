"use client";

import React, { useMemo } from "react";
import {
  Chip,
  Avatar,
  Label,
  ListBoxRoot,
  ListBoxItem,
  SelectRoot,
  SelectTrigger,
  SelectValue,
  SelectIndicator,
  SelectPopover,
} from "@heroui/react";

/**
 * Chế độ hiển thị của Combobox
 */
export type ComboboxMode = "view" | "edit";

/**
 * Các loại kiểu dữ liệu hỗ trợ bởi Combobox
 */
export type ComboboxDataType = "text" | "date" | "number" | "tag" | "human";

/**
 * Cấu trúc đối tượng cho kiểu dữ liệu human
 */
export interface HumanItem {
  id: string | number;
  name: string;
  avatarUrl?: string;
  description?: string;
}

/**
 * Cấu trúc một Option trong Combobox
 */
export interface ComboboxOption {
  value: string | number;
  label: string;
  description?: string;
  avatarUrl?: string;
  tagColor?: "default" | "accent" | "success" | "warning" | "danger";
  raw?: any;
}

/**
 * Props cho thành phần SweCombobox
 *
 * @created_at 24/07/2026
 * @author txhoan
 */
export interface SweComboboxProps {
  /** Chế độ hiển thị: 'view' (gạch dưới kiểu Android) hoặc 'edit' (Combobox chọn) */
  mode?: ComboboxMode;
  /** Kiểu dữ liệu chứa bên trong Combobox: 'text' | 'date' | 'number' | 'tag' | 'human' */
  dataType?: ComboboxDataType;
  /** Giá trị hiện tại của Combobox */
  value?: string | number | null;
  /** Danh sách các options cho Combobox */
  options?: ComboboxOption[];
  /** Callback được gọi khi thay đổi giá trị */
  onChange?: (value: string | number | null, selectedOption?: ComboboxOption) => void;
  /** Văn bản gợi ý trong ô chọn */
  placeholder?: string;
  /** Tiêu đề (Label) của ô chọn */
  label?: string;
  /** Trạng thái vô hiệu hóa */
  isDisabled?: boolean;
  /** Thêm class CSS tùy chỉnh */
  className?: string;
  /** Chiều rộng tùy chỉnh (ví dụ: '300px', '100%', 250) */
  width?: string | number;
  /** Chiều cao tùy chỉnh (ví dụ: '40px', 38) */
  height?: string | number;
}

export interface ValueRendererProps {
  dataType: ComboboxDataType;
  selectedOption?: ComboboxOption;
  rawValue?: any;
  placeholder?: string;
}

/**
 * Helper component hiển thị nội dung theo từng `dataType`
 *
 * @created_at 24/07/2026
 * @author txhoan
 */
export const RenderTypedValue: React.FC<ValueRendererProps> = ({
  dataType,
  selectedOption,
  rawValue,
  placeholder = "Chưa chọn",
}) => {
  if (!selectedOption && rawValue === undefined) {
    return <span className="text-default-400 italic text-sm">{placeholder}</span>;
  }

  const displayLabel = selectedOption ? selectedOption.label : String(rawValue ?? "");

  switch (dataType) {
    case "human": {
      const name = selectedOption?.label ?? rawValue?.name ?? displayLabel;
      const avatarUrl = selectedOption?.avatarUrl ?? rawValue?.avatarUrl;
      const description = selectedOption?.description ?? rawValue?.description;
      const initial = name ? String(name).charAt(0).toUpperCase() : "?";

      return (
        <div className="flex items-center gap-3 py-1">
          <Avatar size="sm" className="shrink-0">
            {avatarUrl && <Avatar.Image src={avatarUrl} />}
            <Avatar.Fallback>{initial}</Avatar.Fallback>
          </Avatar>
          <div className="flex flex-col text-left">
            <span className="text-sm font-medium text-default-900 leading-tight">
              {name}
            </span>
            {description && (
              <span className="text-xs text-default-500 leading-tight">
                {description}
              </span>
            )}
          </div>
        </div>
      );
    }

    case "tag": {
      const tagColor = selectedOption?.tagColor ?? "accent";
      return (
        <Chip
          color={tagColor}
          variant="soft"
          size="sm"
          className="font-medium"
        >
          {displayLabel}
        </Chip>
      );
    }

    case "date": {
      let dateString = displayLabel;
      try {
        const d = new Date(displayLabel);
        if (!isNaN(d.getTime())) {
          dateString = d.toLocaleDateString("vi-VN", {
            year: "numeric",
            month: "2-digit",
            day: "2-digit",
          });
        }
      } catch {
        // Fallback giữ nguyên chuỗi
      }
      return (
        <span className="text-sm font-mono text-default-800 bg-default-100 px-2 py-0.5 rounded">
          {dateString}
        </span>
      );
    }

    case "number": {
      const numVal = Number(displayLabel);
      const formattedNum = !isNaN(numVal) ? numVal.toLocaleString("vi-VN") : displayLabel;
      return (
        <span className="text-sm font-semibold text-primary-600 font-mono">
          {formattedNum}
        </span>
      );
    }

    case "text":
    default: {
      return (
        <span className="text-sm text-default-900 font-normal">
          {displayLabel}
        </span>
      );
    }
  }
};

/**
 * Thành phần SweCombobox hỗ trợ 2 chế độ View/Edit và 5 dạng dữ liệu (text, date, number, tag, human).
 *
 * Mode View: Hiển thị giá trị kèm đường kẻ gạch dưới (Android Material Input underline style).
 * Mode Edit: Hiển thị Combobox tiêu chuẩn cho phép tìm kiếm và lựa chọn options.
 *
 * @param props Thuộc tính và sự kiện truyền vào SweCombobox
 * @created_at 24/07/2026
 * @author txhoan
 */
const SweCombobox: React.FC<SweComboboxProps> = ({
  mode = "edit",
  dataType = "text",
  value,
  options = [],
  onChange,
  placeholder = "Chọn một tùy chọn",
  label,
  isDisabled = false,
  className = "",
  width,
  height,
}) => {
  const selectedOption = useMemo<ComboboxOption | undefined>(() => {
    if (value === null || value === undefined) return undefined;
    return options.find((opt) => String(opt.value) === String(value));
  }, [value, options]);

  const containerStyle = useMemo<React.CSSProperties>(() => {
    const style: React.CSSProperties = {};
    if (width !== undefined) {
      style.width = typeof width === "number" ? `${width}px` : width;
    }
    if (height !== undefined) {
      style.height = typeof height === "number" ? `${height}px` : height;
    }
    return style;
  }, [width, height]);

  const handleSelectionChange = (val: any) => {
    if (val === null || val === undefined) {
      if (onChange) onChange(null, undefined);
      return;
    }
    const opt = options.find((o) => String(o.value) === String(val));
    if (onChange) {
      onChange(opt ? opt.value : val, opt);
    }
  };

  // --- RENDERING CHẾ ĐỘ VIEW (Android Underline Style) ---
  if (mode === "view") {
    return (
      <div className={`flex flex-col gap-1 ${className}`} style={containerStyle}>
        {label && (
          <span className="text-xs font-semibold text-default-500 uppercase tracking-wider">
            {label}
          </span>
        )}
        <div
          className={`w-full min-h-9.5 border-b-2 border-default-300 py-1 flex items-center transition-colors ${isDisabled ? "opacity-50 cursor-not-allowed" : "hover:border-primary-500"
            }`}
        >
          <RenderTypedValue
            dataType={dataType}
            selectedOption={selectedOption}
            rawValue={value}
            placeholder={placeholder}
          />
        </div>
      </div>
    );
  }

  // --- RENDERING CHẾ ĐỘ EDIT (Standard Combobox/Select) ---
  return (
    <div className={`${className}`} style={containerStyle}>
      <SelectRoot
        placeholder={placeholder}
        isDisabled={isDisabled}
        value={value !== undefined && value !== null ? String(value) : null}
        onChange={handleSelectionChange}
      >
        {label && <Label>{label}</Label>}
        <SelectTrigger>
          <SelectValue>
            <RenderTypedValue
              dataType={dataType}
              selectedOption={selectedOption}
              rawValue={value}
              placeholder={placeholder}
            />
          </SelectValue>
          <SelectIndicator />
        </SelectTrigger>
        <SelectPopover>
          <ListBoxRoot>
            {options.map((opt) => (
              <ListBoxItem
                key={String(opt.value)}
                id={String(opt.value)}
                textValue={opt.label}
              >
                <RenderTypedValue
                  dataType={dataType}
                  selectedOption={opt}
                  rawValue={opt.value}
                />
              </ListBoxItem>
            ))}
          </ListBoxRoot>
        </SelectPopover>
      </SelectRoot>
    </div>
  );
};

export default SweCombobox;
