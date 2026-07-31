# Tài liệu đặc tả UI Component: SweCombobox (NuxtJS)

## 1. Giới thiệu

`SweCombobox` là Vue/Nuxt 4 component đa năng hỗ trợ hiển thị/chọn dữ liệu với 2 chế độ (`view` và `edit`) cùng 5 kiểu dữ liệu (`text`, `date`, `number`, `tag`, `human`).

- **Author:** txhoan
- **Created At:** 28/07/2026
- **Framework:** Vue 3 / Nuxt 4 (`@nuxt/ui`)

## 2. Các chế độ hiển thị (Modes)

- **`view`:** Hiển thị giá trị dạng văn bản/badge/human card kèm đường kẻ gạch dưới (`border-b-2`, Android Material input style). Không cho tương tác sửa.
- **`edit`:** Hiển thị dưới dạng `USelectMenu` tiêu chuẩn cho phép tìm kiếm và lựa chọn tùy chọn.

## 3. Props API

| Name | Type | Default | Description |
| --- | --- | --- | --- |
| `mode` | `'view' \| 'edit'` | `'edit'` | Chế độ hiển thị của component. |
| `dataType` | `'text' \| 'date' \| 'number' \| 'tag' \| 'human'` | `'text'` | Định dạng kiểu dữ liệu hiển thị bên trong. |
| `modelValue` | `string \| number \| null` | `null` | Giá trị được chọn (hỗ trợ `v-model`). |
| `options` | `ComboboxOption[]` | `[]` | Danh sách tùy chọn cho Combobox. |
| `placeholder` | `string` | `'Chọn một tùy chọn'` | Văn bản hiển thị khi chưa có giá trị. |
| `label` | `string` | `undefined` | Tiêu đề của Combobox. |
| `disabled` | `boolean` | `false` | Trạng thái vô hiệu hóa. |
| `className` | `string` | `''` | Class CSS tùy chỉnh bổ sung. |
| `width` | `string \| number` | `undefined` | Chiều rộng tùy chỉnh (vd: `'300px'`, `'100%'`, `250`). |
| `height` | `string \| number` | `undefined` | Chiều cao tùy chỉnh (vd: `'40px'`, `38`). |

## 4. Events API

| Event Name | Signature | Description |
| --- | --- | --- |
| `update:modelValue` | `(value: string \| number \| null) => void` | Phát ra khi giá trị thay đổi (phục vụ `v-model`). |
| `change` | `(value: string \| number \| null, selectedOption?: ComboboxOption) => void` | Phát ra kèm theo đối tượng `ComboboxOption` được chọn. |

## 5. Cấu trúc Option (`ComboboxOption`)

```typescript
export interface ComboboxOption {
  value: string | number
  label: string
  description?: string
  avatarUrl?: string
  tagColor?: 'neutral' | 'primary' | 'secondary' | 'success' | 'warning' | 'error'
  raw?: any
}
```

## 6. Ví dụ sử dụng

```vue
<script setup lang="ts">
import { ref } from 'vue'

const selectedUser = ref('usr-1')
const userOptions = [
  { value: 'usr-1', label: 'Nguyễn Văn A', description: 'Senior Developer', avatarUrl: '/avatar1.png' }
]

const selectedStatus = ref('active')
const statusOptions = [
  { value: 'active', label: 'Hoạt động', tagColor: 'success' },
  { value: 'inactive', label: 'Tạm dừng', tagColor: 'error' }
]
</script>

<template>
  <!-- View Mode - Human Type -->
  <SweCombobox
    mode="view"
    data-type="human"
    label="Người phụ trách"
    v-model="selectedUser"
    :options="userOptions"
  />

  <!-- Edit Mode - Tag Type -->
  <SweCombobox
    mode="edit"
    data-type="tag"
    label="Trạng thái"
    v-model="selectedStatus"
    :options="statusOptions"
  />
</template>
```
