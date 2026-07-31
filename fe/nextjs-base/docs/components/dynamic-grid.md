# Đặc tả UI Component: Dynamic Grid (HeroUI v3.2.1)

Hệ thống lưới dữ liệu động sử dụng HeroUI v3.2.1, hỗ trợ SSR fallback, phân trang, lọc, chọn dòng và hành động hàng loạt.

## 1. `<DynamicGrid />` (Smart Container)

| Prop | Kiểu | Bắt buộc | Mô tả |
| :--- | :--- | :---: | :--- |
| `gridCode` | `string` | Có | Mã cấu hình grid gọi xuống API |
| `fallbackConfig` | `GridConfigResponse` | Không | Dữ liệu cấu hình SSR |
| `fallbackData` | `GridDataResponse` | Không | Dữ liệu danh sách SSR |
| `showIndex` | `boolean` | Không | Bật/tắt hiển thị cột số thứ tự (STT) (Ưu tiên hơn `layout.showIndex`) |
| `showSelectionCheckbox` | `boolean` | Không | Bật/tắt hiển thị cột checkbox chọn dòng (Ưu tiên hơn `layout.rowSelection`) |
| `hideHorizontalScroll` | `boolean` | Không | Ẩn cuộn ngang (Mặc định: `false`) |
| `hideVerticalScroll` | `boolean` | Không | Ẩn cuộn dọc (Mặc định: `false`) |
| `className` | `string` | Không | Class tùy chỉnh ngoài cùng |
| `classNames` | `object` | Không | Class tùy chỉnh cho từng vùng (`wrapper`, `toolbar`, `filter`, `table`) |
| `onActionSuccess` | `(code, res) => void` | Không | Callback khi hành động API thành công |
| `onRowClick` | `(row) => void` | Không | Callback khi click dòng |
| `onSelectionChange`| `(keys: Set<string>) => void` | Không | Callback trả về danh sách ID đang chọn |

## 2. `<GridTable />` (Core Table)

| Prop | Kiểu | Mô tả |
| :--- | :--- | :--- |
| `columns` | `GridColumnConfig[]` | Danh sách cột dữ liệu |
| `data` | `GridDataItem[]` | Danh sách bản ghi trang hiện tại |
| `layout` | `LayoutOptions` | Cấu hình bố cục (`showIndex`, `rowSelection`, `stickyHeader`...) |
| `showIndex` | `boolean` | Ghi đè bật/tắt hiển thị cột STT |
| `showSelectionCheckbox` | `boolean` | Ghi đè bật/tắt hiển thị cột checkbox chọn dòng |
| `selectedKeys` | `Set<string>` | Danh sách các khóa hàng đang chọn |
| `onSelectionChange` | `(keys: Set<string>) => void` | Sự kiện khi thay đổi danh sách chọn |
| `page` / `onPageChange` | `number` / `(p: number) => void` | Điều khiển phân trang |

## 3. `<GridFilter />`

| Prop | Kiểu | Mô tả |
| :--- | :--- | :--- |
| `filters` | `GridFilterConfig[]` | Danh sách bộ lọc |
| `onSearch` | `(values) => void` | Callback kích hoạt tìm kiếm/reset |
| `customComponents` | `Record<string, CustomFilterRenderer>` | Component lọc tùy chỉnh |

## 4. `<GridToolbar />`, `<GridCell />`, `<GridAction />` & `<GridPagination />`

- **GridToolbar**: Hiển thị tiêu đề, các nút hành động chung (Action) và hành động hàng loạt (Bulk Action). Khi có hàng được chọn, tự động hiển thị số lượng bản ghi đang chọn và kích hoạt các nút Bulk Action tương ứng.
- **GridCell**: Render tự động theo `column.type` (`TEXT`, `NUMBER`, `BADGE`, `LINK`, `DATE`, `CURRENCY`). Hỗ trợ kiểm tra bảo mật URL XSS.
- **GridAction**: Hiển thị nút hành động (`ButtonVariant`), tự động mở Modal xác nhận khi có `confirmMessage`, gọi API qua `baseService`.
- **GridPagination**: Xem chi tiết đặc tả tại [grid-pagination.md](./grid-pagination.md).

