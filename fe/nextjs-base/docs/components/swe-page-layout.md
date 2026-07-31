# Đặc tả Component: SwePageLayout

`SwePageLayout` là layout mẫu chuẩn cho các trang biểu mẫu (Form/Detail page), được thiết kế để hiển thị tràn viền chiếm toàn bộ chiều cao màn hình (`100vh`), cố định Toolbar ở phía trên và Footer hành động ở phía dưới, chỉ cho phép cuộn nội dung ở phần thân chính (`swe_page_content`).

* **Đường dẫn**: `components/layout/swe-form-layout/SwePageLayout.tsx`
* **Metadata**:
  * `@created_at`: 18/07/2026
  * `@author`: Antigravity
  * `@references`: Base-Codebase UI Standards

---

## 1. Component API (Props)

| Prop | Kiểu dữ liệu | Mặc định | Mô tả |
| :--- | :--- | :--- | :--- |
| `title` | `string` | **Bắt buộc** | Tiêu đề hiển thị trên thanh công cụ (Toolbar). |
| `className` | `string` | `""` | Lớp CSS tùy biến áp dụng cho thẻ bao ngoài. |
| `rightSideActionButtons` | `ReactNode` | `undefined` | Các nút chức năng bổ sung nằm bên phải thanh công cụ. |
| `pickManyMode` | `boolean` | `false` | Bật/tắt trạng thái chọn nhiều bản ghi để thực hiện thao tác hàng loạt. |
| `numberOfPickedRecords` | `number` | `0` | Số lượng bản ghi hiện đang được chọn. |
| `onDeselect` | `() => void` | `undefined` | Callback kích hoạt khi nhấn nút "Bỏ chọn" trên thanh công cụ. |
| `onDelete` | `() => void` | `undefined` | Callback kích hoạt khi nhấn nút "Xóa" trên thanh công cụ. |
| `onSave` | `() => void` | `undefined` | Callback kích hoạt khi nhấn nút "Lưu" ở Footer. |
| `onCancel` | `() => void` | `undefined` | Callback kích hoạt khi nhấn nút "Hủy" ở Footer. |
| `isPending` | `boolean` | `false` | Trạng thái đang tải (Loading) của nút "Lưu" ở Footer. |
| `children` | `ReactNode` | `undefined` | Nội dung chính của trang (được tự động kích hoạt thanh cuộn dọc khi tràn). |

---

## 2. Hướng dẫn sử dụng (Usage Example)

```tsx
import { SwePageLayout } from "@/components/layout/swe-form-layout/SwePageLayout";
import { Button } from "@heroui/react";

export default function ExamplePage() {
    return (
        <SwePageLayout
            title="Cấu hình hệ thống"
            pickManyMode={false}
            onSave={() => console.log("Lưu thành công!")}
            onCancel={() => console.log("Đã hủy!")}
        >
            <div>
                {/* Nội dung form ở đây */}
                <p>Nội dung trang web tự động cuộn khi vượt quá chiều cao màn hình.</p>
            </div>
        </SwePageLayout>
    );
}
```
