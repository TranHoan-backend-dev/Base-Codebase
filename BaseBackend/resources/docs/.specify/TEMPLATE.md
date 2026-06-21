# Đặc tả Tính năng (Feature Specification): [Tên Tính Năng]

**Trạng thái:** Draft / Ready for Dev / Done
**Ngày:** YYYY-MM-DD
**Tác giả:** [Tên]

## 1. Mục đích kinh doanh (Business Context)

[Tại sao chúng ta xây dựng tính năng này? Giá trị mang lại là gì?]

## 2. Luồng Nghiệp vụ (Business Flow)

- Bước 1: User thực hiện...
- Bước 2: BFF gọi đến API...
- Bước 3: Backend xử lý...

## 3. Kiến trúc API (API Design)

- **Endpoint:** `POST /api/v1/...`
- **Tầng Boundary:** `...Controller.java`
- **Tầng Service:** `...Service.java`
- **Tầng Repository:** `...Repository.java`

## 4. Đặc tả Dữ liệu (DTOs)

### Input (Request)

```json
{
  "field": "type"
}
```

### Output (Response)

```json
{
  "status": 200,
  "data": {}
}
```

## 5. Rào chắn Bảo mật (Zero Trust & GBAC Rules)

- **Validation:** [Ví dụ: field A không được trống, field B phải là số dương]
- **Access Control:** Yêu cầu quyền nào? (Ví dụ: `hasPermission('USER', 'CREATE')`)
- **Tenant Isolation:** Có cần thiết phải filter data theo TenantID/GroupID không? (Có/Không - Giải thích).

## 6. Sơ đồ cơ sở dữ liệu (Database Impact)

- Các Table bị ảnh hưởng: `table_name`
- Cột mới: `column_name`
