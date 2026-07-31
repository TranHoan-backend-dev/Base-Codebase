/**
 * Component hiển thị thanh lọc dữ liệu cho Dynamic Grid.
 * Hỗ trợ lọc bằng Input, Select (HeroUI v3.2.1) hoặc Custom Component truyền từ bên ngoài.
 *
 * @created_at 06/07/2026
 * @author txhoan
 * @references plan/nextjs-grid-layout.md
 */

import { Label, Input, Button } from "@heroui/react";
import { ListBoxRoot } from "@heroui/react/list-box";
import { ListBoxItem } from "@heroui/react/list-box-item";
import {
  SelectRoot,
  SelectTrigger,
  SelectValue,
  SelectIndicator,
  SelectPopover,
} from "@heroui/react/select";
import React, { useState, useCallback } from "react";

import { useGridTranslations } from "@/hooks/useGridTranslations";
import {
  GridFilterConfig,
  GridFilterValue,
  CustomFilterRenderer,
} from "@/types/grid";

export interface GridFilterProps {
  /** Danh sách cấu hình bộ lọc */
  filters: GridFilterConfig[];
  /** Callback kích hoạt khi bấm tìm kiếm hoặc reset */
  onSearch: (values: Record<string, GridFilterValue>) => void;
  /** Danh sách custom renderer cho từng trường lọc nếu cần override */
  customComponents?: Record<string, CustomFilterRenderer>;
  /** Class custom bổ sung */
  className?: string;
}

/**
 * GridFilter quản lý state tìm kiếm tạm thời và gửi dữ liệu khi người dùng xác nhận lọc.
 */
export const SweGridFilter = ({
  filters,
  onSearch,
  customComponents,
  className,
}: GridFilterProps): React.JSX.Element => {
  const { t } = useGridTranslations();
  const [values, setValues] = useState<Record<string, GridFilterValue>>({});

  /**
   * Xử lý khi giá trị của một trường lọc thay đổi.
   * Cập nhật vào state cục bộ mà chưa kích hoạt tìm kiếm ngay lập tức.
   *
   * @param {string} field Tên trường dữ liệu cần lọc
   * @param {GridFilterValue} val Giá trị mới của bộ lọc
   */
  const handleChange = useCallback((field: string, val: GridFilterValue) => {
    setValues((prev) => ({ ...prev, [field]: val }));
  }, []);

  /**
   * Kích hoạt tìm kiếm khi người dùng bấm nút "Tìm kiếm".
   * Gọi callback onSearch truyền từ component cha kèm theo tập giá trị hiện tại.
   */
  const handleSubmit = (): void => {
    onSearch(values);
  };

  /**
   * Xóa toàn bộ điều kiện lọc về rỗng và gọi onSearch để tải lại danh sách gốc.
   */
  const handleReset = (): void => {
    setValues({});
    onSearch({});
  };

  /**
   * Render giao diện tương ứng cho từng loại điều kiện lọc (SELECT, TEXT_INPUT hoặc Custom Component).
   *
   * @param {GridFilterConfig} filter Cấu hình chi tiết của bộ lọc
   * @returns {React.ReactNode} Node giao diện của bộ lọc
   */
  const renderFilter = (filter: GridFilterConfig): React.ReactNode => {
    const CustomComp = filter.component || customComponents?.[filter.field];

    if (CustomComp) {
      return (
        <CustomComp
          filter={filter}
          value={values[filter.field]}
          onChange={(v) => handleChange(filter.field, v)}
        />
      );
    }

    switch (filter.type) {
      case "SELECT":
        return (
          <SelectRoot
            placeholder={filter.placeholder || t("select_placeholder")}
            value={
              values[filter.field] !== undefined
                ? String(values[filter.field])
                : null
            }
            onChange={(val: any) =>
              handleChange(filter.field, val !== null ? String(val) : undefined)
            }
          >
            <Label>{filter.label}</Label>
            <SelectTrigger>
              <SelectValue />
              <SelectIndicator />
            </SelectTrigger>
            <SelectPopover>
              <ListBoxRoot>
                {(filter.options || []).map((opt) => (
                  <ListBoxItem
                    key={opt.value}
                    id={opt.value}
                    textValue={opt.label}
                  >
                    {opt.label}
                  </ListBoxItem>
                ))}
              </ListBoxRoot>
            </SelectPopover>
          </SelectRoot>
        );
      case "TEXT_INPUT":
      default:
        return (
          <div className="flex flex-col gap-1">
            <Label>{filter.label}</Label>
            <Input
              placeholder={filter.placeholder}
              value={String(values[filter.field] ?? "")}
              onChange={(e) => handleChange(filter.field, e.target.value)}
            />
          </div>
        );
    }
  };

  return (
    <div className={`flex flex-wrap items-end gap-3 ${className || ""}`}>
      {filters.map((f) => (
        <div key={f.field} className="min-w-[200px]">
          {renderFilter(f)}
        </div>
      ))}
      <Button variant="primary" onPress={handleSubmit}>
        {t("search")}
      </Button>
      <Button variant="secondary" onPress={handleReset}>
        {t("reset")}
      </Button>
    </div>
  );
}
