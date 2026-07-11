# Đặc tả UI Component: BasePopupSidebar (`nextjs-base`)

`BasePopupSidebar` là Smart Container Component hiển thị Drawer / Slide-over trượt từ cạnh màn hình (trái hoặc phải), được xây dựng trên nền **HeroUI v3.2.1 `<Drawer>`**.

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
| `placement` | `"left" \| "right" \| "top" \| "bottom"` | `"right"` | Cạnh trượt vào |
| `size` | `PopupSize` | `"md"` | Kích thước độ rộng (`xs`: 320px, `sm`: 380px, `md`: 420px, `lg`: 520px, `xl`: 640px) |
| `width` | `string` | `undefined` | Độ rộng tùy chỉnh (ghi đè `size`) |
| `showFullscreenButton` | `boolean` | `false` | Hiển thị nút phóng to toàn màn hình |
| `showSaveButton` | `boolean` | `true` | Hiển thị nút Lưu trong Footer |
| `onBeforeClose` | `() => boolean \| Promise<boolean>` | `undefined` | Guard kiểm tra trước khi đóng |

---

## 3. Cách sử dụng

```tsx
import { BasePopupSidebar, usePopup } from "@/components/swe-popup";

export default function DemoSidebar() {
  const sidebar = usePopup();

  return (
    <>
      <Button onPress={sidebar.open}>Mở Sidebar</Button>
      <BasePopupSidebar
        isOpen={sidebar.isOpen}
        onOpenChange={sidebar.setOpen}
        title="Cài đặt hệ thống"
        placement="right"
        size="md"
        showFullscreenButton={true}
        onSave={() => alert("Đã lưu")}
      >
        <p>Nội dung cài đặt.</p>
      </BasePopupSidebar>
    </>
  );
}
```
