# DynamicGrid (Nuxt 3 - Nuxt UI)

`DynamicGrid` là Smart Container chịu trách nhiệm hiển thị và quản lý bảng dữ liệu động dựa theo metadata từ Backend. Sử dụng `useAsyncData` hỗ trợ SSR Hydration, tự động tìm kiếm, phân trang và thao tác hàng loạt.

- **@created_at**: 08/07/2026
- **@author**: txhoan

## 1. Import

```vue
<script setup lang="ts">
import DynamicGrid from '@/components/swe-grid/DynamicGrid.vue'
</script>
```

## 2. Props

| Prop | Type | Default | Mô tả |
| :--- | :--- | :--- | :--- |
| `gridCode` **(*)** | `string` | — | Mã định danh cấu hình Grid (ví dụ: `USERS`, `ORDERS`) |
| `fallbackConfig` | `GridConfigResponse` | `undefined` | Dữ liệu cấu hình SSR fallback |
| `fallbackData` | `GridDataResponse` | `undefined` | Dữ liệu danh sách SSR fallback |
| `showIndex` | `boolean` | `false` | Hiển thị cột số thứ tự (STT) |
| `showSelectionCheckbox` | `boolean` | `false` | Hiển thị cột checkbox chọn dòng |
| `hideHorizontalScroll` | `boolean` | `false` | Ẩn cuộn ngang |
| `hideVerticalScroll` | `boolean` | `false` | Ẩn cuộn dọc |

## 3. Events

| Event | Payload | Mô tả |
| :--- | :--- | :--- |
| `@actionSuccess` | `(actionCode: string, result: unknown)` | Bắn ra khi một hành động gọi API trên Grid thành công |
| `@rowClick` | `(rowData: GridDataItem)` | Bắn ra khi người dùng bấm chọn một dòng |
| `@selectionChange` | `(selectedKeys: Set<string>)` | Bắn ra tập ID các dòng đang được chọn |

## 4. Ví dụ sử dụng

```vue
<template>
  <DynamicGrid
    grid-code="USERS"
    show-index
    show-selection-checkbox
    @action-success="onGridActionSuccess"
  />
</template>
```
