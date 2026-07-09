/**
 * Định nghĩa các kiểu dữ liệu và cấu hình cho hệ thống Dynamic Grid (HeroUI v3.2.1).
 * Hỗ trợ type-safety tuyệt đối, loại bỏ any, phục vụ SSR/CSR trong Next.js 16.
 *
 * @created_at 05/07/2026
 * @author txhoan
 * @references plan/nextjs-grid-layout.md
 */

import type { ComponentType, ReactNode } from "react";

/** Union types — loại bỏ magic strings, hỗ trợ exhaustive switch checking */
export type ColumnType =
  | "TEXT"
  | "NUMBER"
  | "BADGE"
  | "LINK"
  | "DATE"
  | "CURRENCY";
export type ActionType = "ROUTE" | "EXTERNAL_LINK" | "API";
export type HttpMethod = "GET" | "POST" | "PUT" | "DELETE";
export type ActionPosition = "TOOLBAR" | "ROW" | "BULK";
export type ButtonVariant =
  | "primary"
  | "secondary"
  | "tertiary"
  | "outline"
  | "ghost"
  | "danger";
export type FilterType = "TEXT_INPUT" | "SELECT" | "ASYNC_SELECT";
export type ColumnAlign = "left" | "center" | "right";
export type ChipColor =
  | "default"
  | "primary"
  | "secondary"
  | "accent"
  | "success"
  | "warning"
  | "danger";

export interface LayoutOptions {
  rowSelection: boolean;
  pagination: boolean;
  defaultPageSize: number;
  pageSizeOptions: number[];
  stickyHeader: boolean;
  showIndex?: boolean;
}

export interface ValueMapEntry {
  label: string;
  color: ChipColor;
}

export interface ActionConfig {
  type: ActionType;
  target?: string;
  method?: HttpMethod;
  endpoint?: string;
  confirmMessage?: string;
}

export interface GridActionConfig {
  code: string;
  label: string;
  icon: string;
  position: ActionPosition;
  buttonType: ButtonVariant;
  actionConfig: ActionConfig;
}

export interface GridColumnConfig {
  field: string;
  header: string;
  type: ColumnType;
  sortable: boolean;
  hidden: boolean;
  width?: string;
  align?: ColumnAlign;
  actionConfig?: ActionConfig;
  customStyles?: Record<string, string>;
  valueMap?: Record<string, ValueMapEntry>;
}

export interface GridFilterOption {
  label: string;
  value: string;
}

export type GridFilterValue = string | number | boolean | undefined;

export type CustomFilterRenderer =
  | ComponentType<{
      value: GridFilterValue;
      onChange: (val: GridFilterValue) => void;
      filter: GridFilterConfig;
    }>
  | ((props: {
      value: GridFilterValue;
      onChange: (val: GridFilterValue) => void;
      filter: GridFilterConfig;
    }) => ReactNode);

export interface GridFilterConfig {
  field: string;
  label: string;
  type: FilterType | string;
  placeholder?: string;
  defaultValue?: string;
  options?: GridFilterOption[];
  apiEndpoint?: string;
  component?: CustomFilterRenderer;
}

export type GridDataItem = Record<string, unknown> & { id: string | number };

export interface GridConfigResponse {
  gridCode: string;
  title: string;
  layoutOptions: LayoutOptions;
  columns: GridColumnConfig[];
  filters: GridFilterConfig[];
  actions: GridActionConfig[];
}

export interface GridDataResponse {
  items: GridDataItem[];
  total: number;
}
