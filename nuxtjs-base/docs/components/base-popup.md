# Đặc tả UI Component: BasePopup (Nuxt 4 / NuxtUI v4)

- **Thư mục:** `app/components/swe-popup/BasePopup.vue`
- **Mục đích:** Hộp thoại trung tâm chuẩn hóa trên nền Nuxt UI v4 (`UModal`), tích hợp padding chuẩn, nút phóng to toàn màn hình (Resize Fullscreen), nút Lưu/Hủy và bảo vệ đóng form (`onBeforeClose`).

---

## 1. API & Props

| Prop | Type | Default | Mô tả |
| :--- | :--- | :--- | :--- |
| `modelValue` (`v-model`) | `boolean` | `false` | Trạng thái hiển thị popup |
| `title` | `string` | `''` | Tiêu đề hiển thị ở header |
| `description` | `string` | `''` | Mô tả ngắn dưới tiêu đề |
| `size` | `'xs' \| 'sm' \| 'md' \| 'lg' \| 'xl' \| 'full'` | `'md'` | Kích thước của popup |
| `showFullscreenButton` | `boolean` | `true` | Cho phép hiển thị nút chuyển chế độ toàn màn hình |
| `showSaveButton` | `boolean` | `true` | Cho phép ẩn/hiện nút Lưu ở footer |
| `showCancelButton` | `boolean` | `true` | Cho phép ẩn/hiện nút Hủy ở footer |
| `saveLabel` / `cancelLabel` | `string` | i18n key | Nhãn tùy chỉnh cho nút Lưu/Hủy |
| `saveLoading` / `saveDisabled` | `boolean` | `false` | Trạng thái loading/disabled của nút Lưu |
| `onBeforeClose` | `() => boolean \| Promise<boolean>` | `undefined` | Hook kiểm tra trước khi đóng (trả về `false` để chặn đóng) |

---

## 2. Events & Slots

- **Events:**
  - `update:modelValue(value: boolean)`: Phát ra khi popup mở/đóng.
  - `save()`: Phát ra khi bấm nút Lưu.
  - `cancel()`: Phát ra khi bấm nút Hủy.
- **Slots:**
  - `#default`: Nội dung chính của popup (thân modal).
  - `#footer`: Nội dung tùy chỉnh vùng footer (thay thế nút mặc định).

---

## 3. Kiến trúc Layout & Padding chuẩn (Quản lý bằng SCSS Mixin)

- **Cấu hình style:** Quản lý tập trung tại `app/assets/scss/_popup.scss` (`@mixin popup-body`, `@mixin popup-footer`) kết hợp các biến hệ thống (`_space.scss`, `_color.scss`, `_font.scss`, `_style.scss`).
- **Header:** Padding chuẩn theo thiết kế, bao gồm Tiêu đề (16px, weight 500) + Mô tả + nút Fullscreen Resize + nút Đóng.
- **Body:** Sử dụng `@include popup-body` (`padding: 0 16px 12px 16px`, hỗ trợ tự động giãn nở theo nội dung).
- **Footer:** Sử dụng `@include popup-footer` (`padding: 8px 16px 8px 12px`), canh phải, khoảng cách nút 8px.
- **Bo góc (Border Radius):** Popup chính bo góc `8px` (`borderRadius: '8px'`), khi fullscreen tự động về `0`. Sidebar (`BasePopupSidebar`) bo góc `0`.
