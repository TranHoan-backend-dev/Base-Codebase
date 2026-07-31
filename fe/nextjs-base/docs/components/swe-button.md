# SweButton - Đặc tả Component

`SweButton` là thành phần nút bấm giao diện tiêu chuẩn của dự án Next.js (`nextjs-base`), được xây dựng dựa trên `Button`, `Tooltip` và `Badge` của **HeroUI v3.2.1**.

## 1. API & Props

| Prop | Kiểu dữ liệu | Mặc định | Mô tả |
| :--- | :--- | :--- | :--- |
| `btnTitle` | `string` | `""` | Tiêu đề văn bản hiển thị trong nút |
| `btnSize` | `'sm' \| 'md' \| 'lg'` | `'md'` | Kích thước nút bấm |
| `variant` | `'primary' \| 'secondary' \| 'tertiary' \| 'outline' \| 'ghost' \| 'danger' \| 'danger-soft'` | `'outline'` | Biến thể giao diện của nút |
| `icon` | `React.ReactNode` | `undefined` | Icon hiển thị kèm theo trong nút |
| `iconPosition` | `'left' \| 'right'` | `'left'` | Vị trí hiển thị của Icon (trước hoặc sau `btnTitle`) |
| `loadingText` | `string` | `undefined` | Văn bản hiển thị thay thế cho `btnTitle` khi `isPending={true}` |
| `tooltip` | `React.ReactNode` | `undefined` | Nội dung Tooltip hiển thị khi di chuột hoặc focus vào nút |
| `tooltipHtml` | `string` | `undefined` | Chuỗi nội dung HTML hiển thị trong Tooltip (được tự động làm sạch an toàn XSS) |
| `badgeCount` | `number` | `undefined` | Số lượng hiển thị trong nhãn thông báo (Badge) đính kèm góc nút |
| `keepTooltipOpenOnHover` | `boolean` | `false` | Cho phép giữ Tooltip luôn mở khi người dùng di chuyển chuột xuống vùng nội dung Tooltip |
| `tooltipCloseDelay` | `number` | `300` | Thời gian trễ đóng Tooltip (ms) khi `keepTooltipOpenOnHover` được bật |
| `isDisabled` | `boolean` | `false` | Vô hiệu hóa nút bấm |
| `isPending` | `boolean` | `false` | Hiển thị trạng thái đang tải (loading spinner) |
| `fullWidth` | `boolean` | `false` | Hiển thị chiều rộng tối đa (`100%`) |
| `className` | `string` | `undefined` | Lớp CSS bổ sung |

## 2. Sự kiện (Events)

`SweButton` hỗ trợ đầy đủ các sự kiện tiêu chuẩn từ HeroUI và React DOM thông qua `...restProps`:

| Sự kiện | Kiểu dữ liệu | Mô tả |
| :--- | :--- | :--- |
| `onPress` | `(e: PressEvent) => void` | Sự kiện khi nhấn nút (được khuyến nghị cho HeroUI / Touch / Keyboard) |
| `onClick` | `React.MouseEventHandler<HTMLButtonElement>` | Sự kiện click chuột truyền thống |
| `onKeyDown` | `React.KeyboardEventHandler<HTMLButtonElement>` | Sự kiện khi nhấn phím trên bàn phím |
| `onKeyUp` | `React.KeyboardEventHandler<HTMLButtonElement>` | Sự kiện khi nhả phím |
| `onFocus` | `React.FocusEventHandler<HTMLButtonElement>` | Sự kiện khi nút nhận focus |
| `onBlur` | `React.FocusEventHandler<HTMLButtonElement>` | Sự kiện khi nút mất focus |
| `onMouseEnter` / `onMouseLeave` | `React.MouseEventHandler<HTMLButtonElement>` | Sự kiện khi di chuột vào/ra (được tích hợp mượt mà với state Tooltip) |

## 3. Ví dụ sử dụng

```tsx
import SweButton from "@/components/swe-button/SweButton";

// Ví dụ nút bấm với Icon bên phải và Loading Text tự động chuyển đổi
<SweButton
  btnTitle="Lưu cấu hình"
  loadingText="Đang lưu..."
  variant="primary"
  icon={<SaveIcon />}
  iconPosition="right"
  isPending={isLoading}
  onPress={() => handleSave()}
/>

// Ví dụ nút bấm sử dụng chuỗi HTML cho Tooltip
<SweButton
  btnTitle="Trợ giúp HTML"
  variant="secondary"
  tooltipHtml="<div class='p-1'><b class='text-blue-500'>Lưu ý:</b> Chuỗi HTML được <i>sanitized</i> an toàn.</div>"
/>

// Ví dụ nút bấm có Tooltip tương tác (giữ mở khi di chuột xuống tooltip) và Badge thông báo
<SweButton
  btnTitle="Thông báo"
  variant="outline"
  badgeCount={5}
  tooltip={
    <div className="p-2">
      <p className="font-semibold">Chi tiết thông báo mới</p>
      <a href="/notifications" className="text-blue-500 underline">Xem tất cả</a>
    </div>
  }
  keepTooltipOpenOnHover={true}
  tooltipCloseDelay={400}
/>
```
