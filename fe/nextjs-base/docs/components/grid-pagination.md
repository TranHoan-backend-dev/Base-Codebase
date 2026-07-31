# Đặc tả UI Component: GridPagination (HeroUI v3.2.1)

Component hiển thị thông tin tổng số bản ghi và điều khiển chuyển trang, được xây dựng trên nền tảng `Pagination` của HeroUI v3.2.1 theo mẫu compound component.

## 1. API & Props (`GridPaginationProps`)

| Prop | Kiểu dữ liệu | Bắt buộc | Mặc định | Mô tả |
| :--- | :--- | :---: | :---: | :--- |
| `page` | `number` | Có | - | Trang hiện tại (1-indexed) |
| `total` | `number` | Có | - | Tổng số bản ghi toàn hệ thống |
| `currentCount` | `number` | Có | - | Số lượng bản ghi hiển thị trên trang hiện tại |
| `totalPages` | `number` | Không | `Math.ceil(total / pageSize)` | Tổng số trang |
| `pageSize` | `number` | Không | `10` | Số lượng bản ghi trên một trang (dùng để tính `totalPages` khi không truyền) |
| `onPageChange` | `(page: number) => void` | Có | - | Callback được gọi khi người dùng chuyển trang |
| `className` | `string` | Không | `""` | Class CSS tùy chỉnh cho container ngoài cùng |

## 2. Đặc điểm hiển thị & UX

- **Thông tin bản ghi**: Hiển thị chuỗi thông tin số lượng bản ghi đang hiển thị trên tổng số bản ghi (hỗ trợ đa ngôn ngữ thông qua `useGridTranslations`).
- **Điều khiển trang**: Tự động ẩn thanh phân trang khi tổng số trang (`totalPages`) bằng `1` hoặc ít hơn.
- **HeroUI v3.2.1 Compound Pattern**: Sử dụng `Pagination`, `Pagination.Content`, `Pagination.Item`, `Pagination.Previous`, `Pagination.Next`, và `Pagination.Link`.
