# Đặc tả Component: SweFormLayout

`SweFormLayout` là layout chuẩn cho các trang biểu mẫu (Form/Detail page) trong Nuxt 4, chiếm toàn bộ viewport (`100vh`), cố định Toolbar trên + Footer dưới, chỉ cuộn phần content giữa.

* **Đường dẫn**: `app/components/swe-form-layout/SweFormLayout.vue`
* **Sub-components**: `SweFormToolbar.vue`, `SweFormFooter.vue`
* **Metadata**:
  * `@created_at`: 19/07/2026

---

## 1. Props

| Prop | Kiểu | Mặc định | Mô tả |
| :--- | :--- | :--- | :--- |
| `title` | `string` | **Bắt buộc** | Tiêu đề trên Toolbar. |
| `class` | `string` | `""` | CSS class tùy biến cho wrapper. |
| `pickManyMode` | `boolean` | `false` | Bật chế độ chọn nhiều bản ghi. |
| `numberOfPickedRecords` | `number` | `0` | Số bản ghi đang được chọn. |
| `isPending` | `boolean` | `false` | Loading state cho nút Lưu. |

## 2. Emits

| Event | Mô tả |
| :--- | :--- |
| `deselect` | Nhấn nút Bỏ chọn trên Toolbar. |
| `delete` | Nhấn nút Xóa trên Toolbar. |
| `save` | Nhấn nút Lưu ở Footer. |
| `cancel` | Nhấn nút Hủy ở Footer. |

## 3. Slots

| Slot | Mô tả |
| :--- | :--- |
| `layoutBody` | Nội dung chính (tự cuộn khi tràn). |
| `right-actions` | Nút hành động bổ sung bên phải Toolbar. |

---

## 4. Ví dụ sử dụng

```vue
<template>
  <SweFormLayout
    title="Cấu hình hệ thống"
    @save="handleSave"
    @cancel="handleCancel"
  >
    <template #layoutBody>
      <p>Nội dung form tự cuộn khi vượt quá chiều cao.</p>
    </template>

    <template #right-actions>
      <UButton variant="ghost" icon="i-lucide-settings" />
    </template>
  </SweFormLayout>
</template>
```
