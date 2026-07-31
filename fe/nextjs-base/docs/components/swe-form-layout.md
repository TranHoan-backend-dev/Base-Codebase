# Đặc tả Component: SweFormLayout

`SweFormLayout` là layout chuẩn cho các trang biểu mẫu (Form/Detail page) trong Next.js (`nextjs-base`), được thiết kế để hiển thị tràn viền chiếm toàn bộ chiều cao màn hình (`100vh`), cố định Toolbar ở phía trên (`SweToolbar`) và Footer hành động ở phía dưới (`SweFormLayoutFooter`), chỉ cho phép cuộn nội dung ở phần thân chính (`swe_page_content`).

* **Đường dẫn**: `components/layout/swe-form-layout/SweFormLayout.tsx`
* **Sub-components**: `components/layout/components/toolbar/SweToolbar.tsx`, `components/layout/swe-form-layout/components/footer/SweFormLayoutFooter.tsx`
* **Metadata**:
  * `@created_at`: 18/07/2026

---

## 1. Component API (Props)

| Prop | Kiểu dữ liệu | Mặc định | Mô tả |
| :--- | :--- | :--- | :--- |
| `title` | `string` | **Bắt buộc** | Tiêu đề hiển thị trên thanh công cụ (Toolbar). |
| `className` | `string` | `""` | Lớp CSS tùy biến áp dụng cho thẻ wrapper ngoài (`swe_page_layout`). |
| `rightSideActionButtons` | `React.ReactNode` | `undefined` | Các nút chức năng bổ sung nằm bên phải thanh công cụ. |
| `pickManyMode` | `boolean` | `false` | Bật/tắt chế độ chọn nhiều bản ghi để hiển thị thông tin thao tác hàng loạt. |
| `numberOfPickedRecords` | `number` | `0` | Số lượng bản ghi hiện đang được chọn (khi `pickManyMode={true}`). |
| `onDeselect` | `() => void` | `undefined` | Callback kích hoạt khi nhấn nút "Bỏ chọn" trên thanh công cụ. |
| `onDelete` | `() => void` | `undefined` | Callback kích hoạt khi nhấn nút "Xóa" trên thanh công cụ. |
| `children` | `React.ReactNode` | `undefined` | Nội dung chính của trang (tự động kích hoạt thanh cuộn dọc khi tràn chiều cao). |
| `onSave` | `() => void` | `undefined` | Callback kích hoạt khi nhấn nút "Lưu" ở Footer. |
| `onCancel` | `() => void` | `undefined` | Callback kích hoạt khi nhấn nút "Hủy" ở Footer. |
| `isPending` | `boolean` | `false` | Trạng thái đang tải (Loading spinner) của nút "Lưu" ở Footer. |

---

## 2. Hướng dẫn sử dụng (Usage Example)

```tsx
import { useState } from "react";
import { SweFormLayout } from "@/components/layout/swe-form-layout/SweFormLayout";
import { Button } from "@heroui/react";

export default function ExampleFormPage() {
    const [isLoading, setIsLoading] = useState(false);
    const [selectedCount, setSelectedCount] = useState(0);

    const handleSave = () => {
        setIsLoading(true);
        // Xử lý lưu dữ liệu...
    };

    return (
        <SweFormLayout
            title="Cấu hình hệ thống"
            isPending={isLoading}
            pickManyMode={selectedCount > 0}
            numberOfPickedRecords={selectedCount}
            rightSideActionButtons={
                <Button variant="ghost" size="sm">Tùy chọn</Button>
            }
            onSave={handleSave}
            onCancel={() => console.log("Đã hủy!")}
            onDeselect={() => setSelectedCount(0)}
            onDelete={() => console.log("Xóa bản ghi đã chọn")}
        >
            <div className="p-6 space-y-4">
                <p>Nội dung trang tự động cuộn khi vượt quá chiều cao màn hình.</p>
            </div>
        </SweFormLayout>
    );
}
```
