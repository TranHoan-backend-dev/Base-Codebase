/**
 * Component render ô dữ liệu (cell) trong bảng Dynamic Grid dựa trên kiểu dữ liệu của cột.
 * Hỗ trợ các kiểu: BADGE (Chip), LINK (an toàn XSS), NUMBER, DATE, CURRENCY và TEXT.
 *
 * @created_at 06/07/2026
 * @author txhoan
 */

import { Chip, Link, type ChipProps } from "@heroui/react";
import React from "react";

import { GridColumnConfig, GridDataItem } from "@/types/grid";
import { sanitizeGridUrl } from "@/utils/security";

export interface GridCellProps {
  /** Cấu hình cột hiển thị */
  column: GridColumnConfig;
  /** Giá trị của ô hiện tại */
  value: unknown;
  /** Dữ liệu toàn bộ dòng hiện tại (phục vụ thay thế tham số trong URL) */
  rowData: GridDataItem;
}

/**
 * GridCell hiển thị nội dung ô dữ liệu theo cấu hình column.type.
 */
export default function GridCell({
  column,
  value,
  rowData,
}: GridCellProps): React.JSX.Element {
  const stringVal = String(value ?? "");

  switch (column.type) {
    case "BADGE": {
      const badgeMap = column.valueMap?.[stringVal];
      const chipColor = (badgeMap?.color || "default") as ChipProps["color"];

      return (
        <Chip color={chipColor} variant="soft">
          {badgeMap?.label || stringVal}
        </Chip>
      );
    }
    case "LINK": {
      const rawUrl = column.actionConfig?.target?.replace(
        "{id}",
        String(rowData.id),
      );
      const safeUrl = sanitizeGridUrl(rawUrl);

      return <Link href={safeUrl}>{stringVal}</Link>;
    }
    case "NUMBER":
      return <span>{new Intl.NumberFormat().format(Number(value || 0))}</span>;
    case "DATE":
      return (
        <span>
          {value
            ? new Intl.DateTimeFormat().format(new Date(String(value)))
            : ""}
        </span>
      );
    case "CURRENCY":
      return (
        <span>
          {new Intl.NumberFormat(undefined, {
            style: "currency",
            currency: "VND",
          }).format(Number(value || 0))}
        </span>
      );
    case "TEXT":
    default:
      return <span>{stringVal}</span>;
  }
}
