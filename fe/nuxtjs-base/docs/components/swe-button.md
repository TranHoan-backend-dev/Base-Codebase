# SweButton - Đặc tả Component

`SweButton` là thành phần nút bấm giao diện tiêu chuẩn của dự án Nuxt.js (`nuxtjs-base`), được xây dựng dựa trên `UButton`, `UTooltip` và `UBadge` của **Nuxt UI**.

* **Đường dẫn**: `app/components/swe-button/SweButton.vue`
* **Metadata**:
  * `@created_at`: 23/07/2026
  * `@author`: txhoan

---

## 1. API & Props

| Prop | Kiểu dữ liệu | Mặc định | Mô tả |
| :--- | :--- | :--- | :--- |
| `btnTitle` | `string` | `""` | Tiêu đề văn bản hiển thị trong nút |
| `btnSize` | `'xs' \| 'sm' \| 'md' \| 'lg' \| 'xl'` | `'md'` | Kích thước nút bấm |
| `variant` | `'solid' \| 'outline' \| 'soft' \| 'subtle' \| 'ghost' \| 'link' \| 'danger' \| 'danger-soft'` | `'outline'` | Biến thể giao diện của nút |
| `icon` | `string` | `undefined` | Tên Iconify icon (ví dụ `'i-lucide-arrow-right'`) |
| `iconPosition` | `'left' \| 'right'` | `'left'` | Vị trí hiển thị của Icon (trước hoặc sau `btnTitle`) |
| `loadingText` | `string` | `undefined` | Văn bản hiển thị thay thế cho `btnTitle` khi `isPending={true}` |
| `tooltip` | `string` | `undefined` | Nội dung Tooltip văn bản hiển thị khi di chuột vào nút |
| `tooltipHtml` | `string` | `undefined` | Chuỗi nội dung HTML hiển thị trong Tooltip (đã làm sạch an toàn XSS) |
| `badgeCount` | `number` | `undefined` | Số lượng hiển thị trong nhãn thông báo (Badge) góc nút |
| `keepTooltipOpenOnHover` | `boolean` | `false` | Giữ Tooltip mở khi di chuyển chuột vào Tooltip |
| `tooltipCloseDelay` | `number` | `300` | Thời gian trễ đóng Tooltip (ms) |
| `isDisabled` | `boolean` | `false` | Vô hiệu hóa nút bấm |
| `isPending` | `boolean` | `false` | Trạng thái đang tải (loading spinner) |

---

## 2. Emits

| Event | Tham số | Mô tả |
| :--- | :--- | :--- |
| `click` | `(event: MouseEvent)` | Sự kiện khi người dùng click vào nút |

---

## 3. Slots

| Slot | Mô tả |
| :--- | :--- |
| `default` | Thay thế tiêu đề mặc định của nút |
| `tooltip` | Tùy biến nội dung bên trong Tooltip |

---

## 4. Ví dụ sử dụng

```vue
<template>
  <!-- Nút bấm với Icon và Loading Text tự động chuyển đổi -->
  <SweButton
    btn-title="Lưu dữ liệu"
    loading-text="Đang lưu..."
    variant="solid"
    icon="i-lucide-save"
    icon-position="right"
    :is-pending="isLoading"
    @click="handleSave"
  />

  <!-- Nút bấm chứa HTML Tooltip -->
  <SweButton
    btn-title="Trợ giúp"
    variant="soft"
    tooltip-html="<div class='p-1'><b class='text-primary'>Lưu ý:</b> Chuỗi HTML được <i>sanitized</i>.</div>"
  />
</template>
```
