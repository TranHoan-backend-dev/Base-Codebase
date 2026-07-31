# Đặc tả UI Component: BasePopup (`nextjs-base`)

`BasePopup` là Smart Container Component hiển thị Modal overlay (ở giữa màn hình), được xây dựng trên nền **HeroUI v3.2.1 `<Modal>`** và tuân thủ chuẩn thiết kế chung của hệ thống.

---

## 1. Kiến trúc Layout & Padding

- **Header:** Padding `12px 16px`. Hiển thị tiêu đề (`title`), mô tả phụ (`description`), nút đóng (`✕`) và nút phóng to toàn màn hình (`⊕` / `⊖`) nếu bật `showFullscreenButton`.
- **Body:** Padding `0 16px 16px 16px`. Chứa nội dung chính hoặc form.
- **Footer:** Padding `12px 16px`. Căn lề phải với 2 action buttons:
  - Nút "Hủy" (`cancelLabel`, mặc định: Hủy)
  - Nút "Lưu" (`saveLabel`, mặc định: Lưu), có thể ẩn bằng prop `showSaveButton={false}`.

---

## 2. Props API

| Prop | Type | Default | Mô tả |
|:---|:---|:---|:---|
| `isOpen` | `boolean` | — | **(Bắt buộc)** Trạng thái mở/đóng controlled |
| `onOpenChange` | `(isOpen: boolean) => void` | — | **(Bắt buộc)** Callback khi trạng thái thay đổi |
| `title` | `string` | `undefined` | Tiêu đề Header |
| `description` | `string` | `undefined` | Mô tả phụ dưới tiêu đề |
| `size` | `PopupSize` | `"md"` | Kích thước (`xs`, `sm`, `md`, `lg`, `xl`, `full`) |
| `showFullscreenButton` | `boolean` | `false` | Hiển thị nút phóng to toàn màn hình |
| `showSaveButton` | `boolean` | `true` | Hiển thị nút Lưu trong Footer |
| `onBeforeClose` | `() => boolean \| Promise<boolean>` | `undefined` | Guard kiểm tra trước khi đóng (VD: dirty form) |
| `onSave` | `() => void` | `undefined` | Callback khi bấm Lưu |
| `onCancel` | `() => void` | `undefined` | Callback khi bấm Hủy |

---

## 3. Cách sử dụng

### 3.1. Dùng với Hook `usePopup` (Flat Props)

```tsx
import { BasePopup, usePopup } from "@/components/swe-popup";

export default function MyComponent() {
  const popup = usePopup();

  return (
    <>
      <Button onPress={popup.open}>Mở Popup</Button>
      <BasePopup
        isOpen={popup.isOpen}
        onOpenChange={popup.setOpen}
        title="Xác nhận thao tác"
        showFullscreenButton={true}
        onSave={() => alert("Đã lưu!")}
      >
        <p>Nội dung bên trong popup.</p>
      </BasePopup>
    </>
  );
}
```

### 3.2. Compound Pattern (Tùy chỉnh Header/Body/Footer)

```tsx
<BasePopup isOpen={popup.isOpen} onOpenChange={popup.setOpen}>
  <BasePopup.Header>Tiêu đề tùy chỉnh</BasePopup.Header>
  <BasePopup.Body>Nội dung form</BasePopup.Body>
  <BasePopup.Footer>
    <Button onPress={popup.close}>Đóng</Button>
  </BasePopup.Footer>
</BasePopup>
```
