# Đặc tả UI Component: BasePopupSidebar (Nuxt 4 / NuxtUI v4)

- **Thư mục:** `app/components/swe-popup/BasePopupSidebar.vue`
- **Mục đích:** Khung trượt bên chuẩn hóa trên nền Nuxt UI v4 (`USlideover`), hỗ trợ trượt từ `left`, `right`, `top`, `bottom`, tích hợp nút phóng to toàn màn hình (Resize Fullscreen), nút Lưu/Hủy có thể ẩn và bảo vệ đóng form (`onBeforeClose`).

---

## 1. API & Props

| Prop | Type | Default | Mô tả |
| :--- | :--- | :--- | :--- |
| `modelValue` (`v-model`) | `boolean` | `false` | Trạng thái hiển thị sidebar |
| `side` | `'left' \| 'right' \| 'top' \| 'bottom'` | `'right'` | Vị trí trượt ra của sidebar |
| `title` / `description` | `string` | `''` | Tiêu đề và mô tả của sidebar |
| `size` | `'xs' \| 'sm' \| 'md' \| 'lg' \| 'xl' \| 'full'` | `'md'` | Kích thước chiều rộng của sidebar |
| `showFullscreenButton` | `boolean` | `true` | Cho phép hiển thị nút chuyển chế độ toàn màn hình |
| `showSaveButton` | `boolean` | `true` | Cho phép ẩn/hiện nút Lưu ở footer |
| `showCancelButton` | `boolean` | `true` | Cho phép ẩn/hiện nút Hủy ở footer |
| `saveLabel` / `cancelLabel` | `string` | i18n key | Nhãn tùy chỉnh cho nút Lưu/Hủy |
| `saveLoading` / `saveDisabled` | `boolean` | `false` | Trạng thái loading/disabled của nút Lưu |
| `onBeforeClose` | `() => boolean \| Promise<boolean>` | `undefined` | Hook kiểm tra trước khi đóng (trả về `false` để chặn đóng) |

---

## 2. Events & Slots

- **Events:**
  - `update:modelValue(value: boolean)`: Phát ra khi sidebar mở/đóng.
  - `save()`: Phát ra khi bấm nút Lưu.
  - `cancel()`: Phát ra khi bấm nút Hủy.
- **Slots:**
  - `#default`: Nội dung chính của sidebar.
  - `#footer`: Nội dung tùy chỉnh vùng footer.

---

## 3. Kiến trúc Layout & Padding chuẩn

- **Header:** Padding `12px 16px` (`py-3 px-4`). Chứa Tiêu đề + Mô tả + nút Fullscreen Resize + nút Đóng.
- **Body:** Padding `0 16px 16px 16px` (`px-4 pb-4 pt-4`), cuộn tự động (`overflow-y-auto`).
- **Footer:** Padding `12px 16px` (`py-3 px-4`). Căn phải, chứa nút Hủy và Lưu (có thể ẩn).
