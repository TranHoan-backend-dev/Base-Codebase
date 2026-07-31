# Đặc tả Tính năng (Feature Specification): DynamicGridConfig

**Trạng thái:** Draft
**Ngày:** 2026-06-21
**Tác giả:** AI Architect

## 1. Mục đích nghiệp vụ (Business Context)

Trong hệ thống Admin / Quản trị, việc hiển thị dữ liệu dưới dạng bảng (Data Grid) là nhu cầu phổ biến nhất. Tuy nhiên, việc code thủ công từng bảng trên giao diện Frontend gây tốn thời gian và khó bảo trì.
**DynamicGridConfig** cung cấp giải pháp **Server-Driven UI**: Backend sẽ trả về cấu hình bảng (gồm các cột, kiểu dữ liệu, các filter có sẵn, các action được phép). Frontend đọc cấu hình này để tự động render thành bảng dữ liệu hoàn chỉnh.

## 2. Luồng Nghiệp vụ (Business Flow)

- Bước 1: UI Frontend mount một component `<DynamicGrid gridCode="USER_MANAGEMENT" />`.
- Bước 2: Component gọi lên BFF (`GET /api/grid/USER_MANAGEMENT/config`). BFF gọi xuống Backend.
- Bước 3: Backend trả về JSON cấu hình (danh sách cột, tên cột, kiểu dữ liệu, các filter).
- Bước 4: UI render bộ khung Bảng và Thanh bộ lọc (Filter).
- Bước 5: UI gửi request lấy dữ liệu (`POST /api/grid/USER_MANAGEMENT/data` kèm theo phân trang và các filter mặc định).
- Bước 6: Backend fetch dữ liệu từ Database, áp dụng phân quyền GBAC (chỉ lấy data mà user được phép xem), và trả về cho Frontend.

## 3. Kiến trúc API (API Design)

- **Tầng Boundary:** `DynamicGridController.java`
- **Tầng Service:** `DynamicGridConfigService.java` (lấy cấu hình) & `DynamicGridDataService.java` (query dữ liệu, xử lý hàng loạt).
- **Endpoints:**
  - `GET /api/v1/dynamic-grid/{gridCode}/config`: Lấy cấu hình grid.
  - `POST /api/v1/dynamic-grid/{gridCode}/data`: Lấy dữ liệu bảng (kèm phân trang, bộ lọc).
  - `POST /api/v1/dynamic-grid/{gridCode}/bulk-delete` (hoặc endpoint tương ứng): Xử lý xóa nhiều bản ghi từ danh sách ID.

## 4. Đặc tả Dữ liệu (DTOs)

### Response Cấu hình (GridConfigResponse)

```json
{
  "gridCode": "USER_MANAGEMENT",
  "title": "Quản lý Người dùng",
  "layoutOptions": {
    "rowSelection": true,
    "pagination": true,
    "defaultPageSize": 20,
    "pageSizeOptions": [10, 20, 50, 100],
    "stickyHeader": true
  },
  "columns": [
    { "field": "id", "header": "ID", "type": "NUMBER", "sortable": true, "hidden": true },
    { 
      "field": "username", 
      "header": "Tên đăng nhập", 
      "type": "LINK", 
      "sortable": true,
      "width": "200px",
      "align": "left",
      "actionConfig": { "type": "ROUTE", "target": "/users/{id}" },
      "customStyles": { "textColor": "primary", "fontWeight": "bold" }
    },
    { 
      "field": "status", 
      "header": "Trạng thái", 
      "type": "BADGE", 
      "sortable": false,
      "valueMap": {
        "ACTIVE": { "label": "Hoạt động", "color": "success" },
        "INACTIVE": { "label": "Khóa", "color": "danger" }
      }
    }
  ],
  "filters": [
    { "field": "username", "label": "Tìm tên đăng nhập", "type": "TEXT_INPUT", "placeholder": "Nhập tên..." },
    { "field": "status", "label": "Trạng thái", "type": "SELECT", "options": [{ "label": "Hoạt động", "value": "ACTIVE" }, { "label": "Khóa", "value": "INACTIVE" }] },
    { "field": "roleId", "label": "Vai trò", "type": "ASYNC_SELECT", "apiEndpoint": "/api/v1/roles/lookup" }
  ],
  "actions": [
    {
      "code": "CREATE", "label": "Tạo mới", "icon": "PlusIcon", "position": "TOOLBAR", "buttonType": "primary",
      "actionConfig": { "type": "ROUTE", "target": "/users/create" }
    },
    {
      "code": "DELETE", "label": "Xóa", "icon": "TrashIcon", "position": "ROW", "buttonType": "danger",
      "actionConfig": { "type": "API", "method": "DELETE", "endpoint": "/api/v1/users/{id}", "confirmMessage": "Xác nhận xóa?" }
    },
    {
      "code": "BULK_DELETE", "label": "Xóa nhiều", "icon": "TrashIcon", "position": "BULK", "buttonType": "danger",
      "actionConfig": { "type": "API", "method": "POST", "endpoint": "/api/v1/users/bulk-delete", "confirmMessage": "Xóa các mục đã chọn?" }
    }
  ]
}
```

*Lưu ý các trường cấu hình mở rộng:*
- **`layoutOptions`**: Bật/tắt row selection (checkbox), phân trang, sticky header.
- **`columns[].actionConfig`**: Định nghĩa hành vi chuyển trang (`ROUTE`, `EXTERNAL_LINK`) hoặc gọi `API` tự động. Hỗ trợ biến nội suy `{id}`.
- **`columns[].valueMap`**: Map Enum từ DB sang text và color để FE hiển thị Badge linh hoạt mà không cần hardcode.
- **`actions[].position`**: Xác định vị trí nút (`TOOLBAR`, `ROW`, `BULK`). Nút `BULK` tự động kích hoạt khi có row được select.
- **`filters[].apiEndpoint`**: Phục vụ dropdown/autocomplete lấy dữ liệu động.

### Request Lấy dữ liệu (GridDataRequest)

```json
{
  "page": 0,
  "size": 20,
  "sorts": [
    { "field": "username", "direction": "ASC" }
  ],
  "filters": [
    { "field": "status", "operator": "EQUALS", "value": "ACTIVE" }
  ]
}
```

## 5. Rào chắn Bảo mật (Zero Trust & GBAC Rules)

- **Validation:**
  - Request lấy data phải validate: `page >= 0`, `size <= 100` để tránh overload DB.
  - Các `field` truyền vào `sorts` và `filters` phải được validate xem có tồn tại trong danh sách cột được phép hay không, để **chống SQL Injection**.
- **Access Control:**
  - `@PreAuthorize("hasPermission('DYNAMIC_GRID', #gridCode)")` hoặc dựa trên quy tắc Mapping riêng của dự án.
  - User không có quyền sẽ nhận HTTP 403.
- **Tenant Isolation:** Dữ liệu trả về ở hàm Data Query phải tự động mix (gắn thêm) điều kiện WHERE để filter theo GroupID của User hiện tại (GBAC).

## 6. Sơ đồ cơ sở dữ liệu (Database Impact)

*Tùy thuộc vào thiết kế (Code hay DB), nếu lưu Database:*

- `sys_grid_config` (id, grid_code, title, created_at, ...)
- `sys_grid_column` (id, grid_code, field, header, type, sortable, hidden, ...)
- `sys_grid_filter` (id, grid_code, field, label, type, options, ...)
