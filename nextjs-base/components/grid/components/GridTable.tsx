/**
 * Component GridTable cho hệ thống Dynamic Grid (HeroUI v3.2.1).
 * Hiển thị bảng dữ liệu với tính năng chọn dòng, sticky header, phân trang và hỗ trợ cột STT.
 *
 * @created_at 06/07/2026
 * @author txhoan
 */

"use client";

import type { Selection } from "@heroui/react";

import { Table, Checkbox } from "@heroui/react";

import { useGridTranslations } from "@/hooks/useGridTranslations";
import {
  GridColumnConfig,
  LayoutOptions,
  GridDataItem,
  ColumnAlign,
} from "@/types/grid";

import GridPagination from "../../pagination/GridPagination";

import GridCell from "./GridCell";

export interface GridTableProps {
  columns: GridColumnConfig[];
  data: GridDataItem[];
  layout: LayoutOptions;
  selectedKeys: Set<string>;
  onSelectionChange: (keys: Set<string>) => void;
  onRowClick?: (rowData: GridDataItem) => void;
  page: number;
  onPageChange: (page: number) => void;
  total: number;
  hideHorizontalScroll?: boolean;
  hideVerticalScroll?: boolean;
  /** Cho phép bật/tắt render checkbox chọn dòng (mặc định ưu tiên theo layout.rowSelection) */
  showSelectionCheckbox?: boolean;
  /** Cho phép bật/tắt hiển thị cột số thứ tự (STT) (mặc định ưu tiên theo layout.showIndex) */
  showIndex?: boolean;
  className?: string;
}

export default function GridTable({
  columns,
  data,
  layout,
  selectedKeys,
  onSelectionChange,
  onRowClick,
  page,
  onPageChange,
  total,
  hideHorizontalScroll = false,
  hideVerticalScroll = false,
  showSelectionCheckbox,
  showIndex: propShowIndex,
  className = "",
}: GridTableProps) {
  const { t } = useGridTranslations();
  const visibleColumns = columns.filter((c) => !c.hidden);
  const showCheckbox = showSelectionCheckbox ?? layout.rowSelection ?? false;
  const showIndex = propShowIndex ?? layout.showIndex ?? false;
  const pageSize = layout.defaultPageSize || 10;
  const totalPages = Math.max(1, Math.ceil(total / pageSize));

  const handleSelection = (keys: Selection) => {
    if (keys === "all") {
      onSelectionChange(new Set(data.map((d) => String(d.id))));
    } else {
      onSelectionChange(new Set(Array.from(keys).map(String)));
    }
  };

  return (
    <div className="flex flex-col gap-4 w-full">
      <Table className={className}>
        <Table.ScrollContainer
          className={[
            hideHorizontalScroll ? "overflow-x-hidden" : "",
            hideVerticalScroll ? "overflow-y-hidden" : "",
          ].join(" ")}
        >
          <Table.Content
            aria-label="Dynamic Grid Table"
            selectedKeys={selectedKeys}
            selectionMode={showCheckbox ? "multiple" : "none"}
            onSelectionChange={handleSelection}
          >
            <Table.Header className="bg-default-100 text-default-600 font-semibold">
              {showCheckbox ? (
                <Table.Column
                  key="selection_checkbox"
                  className="w-10 text-center"
                  id="selection_checkbox"
                >
                  <Checkbox slot="selection" />
                </Table.Column>
              ) : null}
              {showIndex ? (
                <Table.Column key="stt_index" isRowHeader id="stt_index">
                  {t("index")}
                </Table.Column>
              ) : null}
              {visibleColumns.map((col, idx) => (
                <Table.Column
                  key={col.field}
                  id={col.field}
                  isRowHeader={!showIndex && idx === 0}
                >
                  {col.header}
                </Table.Column>
              ))}
            </Table.Header>
            <Table.Body
              items={data}
              renderEmptyState={() => (
                <div className="p-6 text-center text-default-500">
                  {t("no_data")}
                </div>
              )}
            >
              {(item: GridDataItem) => {
                const itemIndex = data.indexOf(item);
                const sttNumber = (page - 1) * pageSize + itemIndex + 1;

                return (
                  <Table.Row
                    key={String(item.id)}
                    className={
                      onRowClick
                        ? "cursor-pointer hover:bg-default-50 transition-colors py-3"
                        : "hover:bg-default-50 transition-colors py-3"
                    }
                    id={String(item.id)}
                  >
                    {showCheckbox ? (
                      <Table.Cell className="text-center">
                        <Checkbox slot="selection" />
                      </Table.Cell>
                    ) : null}
                    {showIndex ? (
                      <Table.Cell className="text-center font-medium text-default-500">
                        {sttNumber}
                      </Table.Cell>
                    ) : null}
                    {visibleColumns.map((col) => (
                      <Table.Cell key={col.field}>
                        <GridCell
                          column={col}
                          rowData={item}
                          value={item[col.field]}
                        />
                      </Table.Cell>
                    ))}
                  </Table.Row>
                );
              }}
            </Table.Body>
          </Table.Content>
        </Table.ScrollContainer>
      </Table>

      {layout.pagination && total > 0 && (
        <GridPagination
          currentCount={data.length}
          page={page}
          total={total}
          totalPages={totalPages}
          onPageChange={onPageChange}
        />
      )}
    </div>
  );
}
