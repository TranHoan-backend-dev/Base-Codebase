# Tài liệu đặc tả UI Component: SweCombobox

## 1. Giới thiệu

`SweCombobox` là component lựa chọn/hiển thị dữ liệu đa năng, hỗ trợ 2 chế độ `View` và `Edit` cùng 5 kiểu dữ liệu (`text`, `date`, `number`, `tag`, `human`).

- **Author:** txhoan
- **Created At:** 24/07/2026

## 2. Các chế độ hiển thị (Modes)

- **`view`:** Hiển thị giá trị dạng văn bản/badge/human card kèm đường kẻ gạch dưới (`border-b-2`, Android Material input style). Không tương tác chỉnh sửa.
- **`edit`:** Hiển thị dưới dạng Combobox/Select tiêu chuẩn cho phép tìm kiếm và lựa chọn tùy chọn.

## 3. Props API

| Name | Type | Default | Description |
| --- | --- | --- | --- |
| `mode` | `'view' \| 'edit'` | `'edit'` | Chế độ hiển thị của component. |
| `dataType` | `'text' \| 'date' \| 'number' \| 'tag' \| 'human'` | `'text'` | Định dạng kiểu dữ liệu hiển thị bên trong. |
| `value` | `string \| number \| null` | `undefined` | Giá trị được chọn hiện tại. |
| `options` | `ComboboxOption[]` | `[]` | Danh sách tùy chọn cho Combobox. |
| `onChange` | `(value, option) => void` | `undefined` | Callback phát ra khi thay đổi tùy chọn ở Edit mode. |
| `placeholder` | `string` | `'Chọn một tùy chọn'` | Văn bản hiển thị khi chưa có giá trị. |
| `label` | `string` | `undefined` | Tiêu đề của Combobox. |
| `isDisabled` | `boolean` | `false` | Trạng thái vô hiệu hóa. |
| `width` | `string \| number` | `undefined` | Chiều rộng tùy chỉnh (vd: `'300px'`, `'100%'`, `250`). |
| `height` | `string \| number` | `undefined` | Chiều cao tùy chỉnh (vd: `'40px'`, `38`). |

## 4. Cấu trúc Cấu hình Option (`ComboboxOption`)

```typescript
export interface ComboboxOption {
  value: string | number;
  label: string;
  description?: string;
  avatarUrl?: string;
  tagColor?: "default" | "primary" | "secondary" | "success" | "warning" | "danger";
  raw?: any;
}
```

## 5. Ví dụ sử dụng

```tsx
import { SweCombobox } from "@/components/swe-combobox";

// View mode - Human Type
<SweCombobox
  mode="view"
  dataType="human"
  label="Người phụ trách"
  value="usr-1"
  options={[
    { value: "usr-1", label: "Nguyễn Văn A", description: "Senior Developer", avatarUrl: "/avatar1.png" }
  ]}
/>

// Edit mode - Tag Type
<SweCombobox
  mode="edit"
  dataType="tag"
  label="Trạng thái"
  value="active"
  options={[
    { value: "active", label: "Hoạt động", tagColor: "success" },
    { value: "inactive", label: "Tạm dừng", tagColor: "danger" }
  ]}
  onChange={(val) => console.log(val)}
/>
```
