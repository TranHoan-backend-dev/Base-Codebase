# GridPagination (Nuxt 3 - Nuxt UI)

`GridPagination` là component phân trang độc lập, hiển thị thông tin số lượng bản ghi và bộ điều khiển trang bằng `<UPagination>`. Được tách biệt để dễ tái sử dụng hoặc thay thế UI/UX mà không ảnh hưởng tới bảng dữ liệu.

- **@created_at**: 08/07/2026
- **@author**: txhoan

## 1. Import

```vue
<script setup lang="ts">
import GridPagination from '@/components/swe-pagination/GridPagination.vue'
</script>
```

## 2. Props & v-model

| Prop | Type | Default | Mô tả |
| :--- | :--- | :--- | :--- |
| `v-model:page` | `number` | `1` | Trang hiện tại (binding hai chiều) |
| `total` **(*)** | `number` | — | Tổng số bản ghi toàn hệ thống |
| `currentCount` **(*)** | `number` | — | Số bản ghi đang hiển thị trên trang hiện tại |
| `pageSize` | `number` | `10` | Số bản ghi mỗi trang (`page-count`) |

## 3. Ví dụ sử dụng

```vue
<template>
  <GridPagination
    v-model:page="currentPage"
    :total="100"
    :current-count="10"
    :page-size="10"
  />
</template>
```
