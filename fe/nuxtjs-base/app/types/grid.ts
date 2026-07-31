/**
 * Định nghĩa hệ thống kiểu dữ liệu TypeScript cho Dynamic Grid (Nuxt 3 - Nuxt UI).
 * Đồng bộ cấu trúc DTO giữa Backend và Frontend, hỗ trợ type-safe đầy đủ.
 *
 * @created_at 08/07/2026
 * @author txhoan
 */

import type { Component } from 'vue'

export type ColumnType = 'TEXT' | 'NUMBER' | 'BADGE' | 'LINK' | 'DATE' | 'CURRENCY'
export type ActionType = 'ROUTE' | 'EXTERNAL_LINK' | 'API'
export type HttpMethod = 'GET' | 'POST' | 'PUT' | 'DELETE'
export type ActionPosition = 'TOOLBAR' | 'ROW' | 'BULK'

export type ButtonVariant = 'primary' | 'secondary' | 'success' | 'error' | 'warning' | 'info' | 'neutral'
export type BadgeColor = 'primary' | 'secondary' | 'success' | 'error' | 'warning' | 'info' | 'neutral' | string
export type FilterType = 'TEXT_INPUT' | 'SELECT' | 'ASYNC_SELECT'
export type ColumnAlign = 'left' | 'center' | 'right'

export interface LayoutOptions {
  rowSelection: boolean
  pagination: boolean
  defaultPageSize: number
  pageSizeOptions: number[]
  stickyHeader: boolean
  showIndex?: boolean
}

export interface ValueMapEntry {
  label: string
  color: BadgeColor
}

export interface ActionConfig {
  type: ActionType
  target?: string
  method?: HttpMethod
  endpoint?: string
  confirmMessage?: string
}

export interface GridActionConfig {
  code: string
  label: string
  icon: string
  position: ActionPosition
  buttonType: ButtonVariant
  actionConfig: ActionConfig
}

export interface GridColumnConfig {
  field: string
  header: string
  type: ColumnType
  sortable: boolean
  hidden: boolean
  width?: string
  align?: ColumnAlign
  actionConfig?: ActionConfig
  customStyles?: Record<string, string>
  valueMap?: Record<string, ValueMapEntry>
}

export interface GridFilterOption {
  label: string
  value: string
}

export type GridFilterValue = string | number | boolean | undefined

export type CustomFilterRenderer = Component

export interface GridFilterConfig {
  field: string
  label: string
  type: FilterType | string
  placeholder?: string
  defaultValue?: string
  options?: GridFilterOption[]
  apiEndpoint?: string
  component?: CustomFilterRenderer
}

export type GridDataItem = Record<string, unknown> & { id: string | number }

export interface GridConfigResponse {
  gridCode: string
  title: string
  layoutOptions: LayoutOptions
  columns: GridColumnConfig[]
  filters: GridFilterConfig[]
  actions: GridActionConfig[]
}

export interface GridDataResponse {
  items: GridDataItem[]
  total: number
}
