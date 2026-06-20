# Đặc tả API Swagger/OpenAPI 3.0 - Base Controller API

Tài liệu này cung cấp đặc tả chi tiết về các API dùng chung của lớp `BaseController` trong thư viện `Base-Codebase`. Tất cả các controller nghiệp vụ con kế thừa từ `BaseController` đều thừa hưởng toàn bộ các endpoint này.

## OpenAPI 3.0 Specification (YAML Format)

```yaml
openapi: 3.0.3
info:
  title: Base-Codebase Common Base API
  description: Tài liệu đặc tả các REST API CRUD dùng chung, phân trang, và chiếu thuộc tính động (Dynamic Projection) được cung cấp bởi BaseController.
  version: 1.0.0
servers:
  - url: http://localhost:8080
    description: Cấu hình Local Server mặc định
paths:
  /:
    post:
      summary: Tạo mới thực thể
      description: Nhận DTO Request, chuyển đổi thành thực thể database và tiến hành lưu trữ. Trả về mã HTTP 201 nếu thành công.
      operationId: createEntity
      requestBody:
        required: true
        content:
          application/json:
            schema:
              type: object
              description: Payload DTO chứa dữ liệu tạo mới
      responses:
        '201':
          description: Tạo mới thành công
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/WrapperApiResponse'
        '400':
          description: Dữ liệu đầu vào không hợp lệ hoặc không an sau (XSS/SQL Injection)
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/WrapperApiResponse'

  /{id}:
    get:
      summary: Lấy chi tiết thực thể theo ID
      description: Tìm kiếm bản ghi trong cơ sở dữ liệu dựa trên ID. Tự động bỏ qua các bản ghi đã xóa mềm (deleted = true).
      operationId: getEntityById
      parameters:
        - name: id
          in: path
          required: true
          description: Khóa chính cần tìm kiếm
          schema:
            type: string
      responses:
        '200':
          description: Thành công
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/WrapperApiResponse'
        '404':
          description: Không tìm thấy thực thể hoặc thực thể đã bị xóa mềm
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/WrapperApiResponse'

    put:
      summary: Cập nhật thực thể theo ID
      description: Tìm thực thể hiện tại theo ID, cập nhật các thuộc tính được gửi từ DTO Request và lưu đè.
      operationId: updateEntity
      parameters:
        - name: id
          in: path
          required: true
          description: Khóa chính cần cập nhật
          schema:
            type: string
      requestBody:
        required: true
        content:
          application/json:
            schema:
              type: object
              description: Payload DTO chứa thông tin cần cập nhật
      responses:
        '200':
          description: Cập nhật thành công
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/WrapperApiResponse'
        '400':
          description: Dữ liệu đầu vào không hợp lệ
        '404':
          description: Không tìm thấy thực thể
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/WrapperApiResponse'

    delete:
      summary: Xóa thực thể theo ID
      description: Tìm kiếm thực thể hiện tại. Nếu thực thể kế thừa từ BaseSoftDeleteModel, thực hiện cập nhật trạng thái xóa mềm (deleted = true). Ngược lại thực hiện xóa cứng vật lý khỏi cơ sở dữ liệu.
      operationId: deleteEntity
      parameters:
        - name: id
          in: path
          required: true
          description: Khóa chính cần xóa
          schema:
            type: string
      responses:
        '200':
          description: Xóa thành công
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/WrapperApiResponse'
        '404':
          description: Không tìm thấy thực thể

  /paging:
    post:
      summary: Lấy danh sách thực thể phân trang, lọc và tìm kiếm mờ
      description: Tìm kiếm danh sách thực thể với các điều kiện phân trang, sắp xếp, lọc bằng JSON và tìm kiếm keyword case-insensitive trên tất cả thuộc tính String.
      operationId: getPagedEntities
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/PagingRequest'
      responses:
        '200':
          description: Lấy dữ liệu phân trang thành công
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/WrapperApiResponse'

  /paging/projected:
    post:
      summary: Lấy danh sách phân trang và chỉ select các trường cột được chỉ định (Dynamic Projection)
      description: Tối ưu hiệu năng truy vấn bằng cách chỉ SELECT các cột được gửi lên trong mảng `fields`. Trả về trang dữ liệu dạng danh sách Map Key-Value.
      operationId: getPagedProjectedEntities
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/PagingRequest'
      responses:
        '200':
          description: Truy vấn chiếu phân trang thành công
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/WrapperApiResponse'
components:
  schemas:
    PagingRequest:
      type: object
      required:
        - page
        - size
      properties:
        page:
          type: integer
          format: int32
          description: Chỉ số trang (bắt đầu từ 0)
          example: 0
        size:
          type: integer
          format: int32
          description: Số lượng bản ghi trên một trang
          example: 10
        sort:
          type: string
          description: Chuỗi cấu hình sắp xếp (Định dạng "field,asc" hoặc "field,desc")
          example: "createdAt,desc"
        keyword:
          type: string
          description: Từ khóa dùng cho tìm kiếm mờ case-insensitive trên các cột String
          example: "txhoan"
        filter:
          type: string
          description: Chuỗi JSON chứa bộ lọc so sánh bằng chính xác (Equal) cho các cột dữ liệu
          example: "{\"status\":\"ACTIVE\"}"
        fields:
          type: array
          items:
            type: string
          description: Danh sách tên cột thuộc tính cần lấy (Dùng riêng cho endpoint projected)
          example: ["id", "username", "email", "createdAt"]

    SystemSettingRequest:
      type: object
      required:
        - settingKey
        - settingValue
      properties:
        settingKey:
          type: string
          maxLength: 100
          description: Từ khóa cấu hình hệ thống chạy động
          example: "max_payment_limit"
        settingValue:
          type: string
          maxLength: 1000
          description: Giá trị cấu hình tương ứng
          example: "50000000"
        description:
          type: string
          maxLength: 255
          description: Mô tả chi tiết của tham số cấu hình
          example: "Hạn mức thanh toán tối đa cho phép trong một giao dịch"

    SystemSettingResponse:
      type: object
      properties:
        id:
          type: integer
          format: int64
          description: ID của cấu hình trong DB
          example: 1
        settingKey:
          type: string
          description: Từ khóa cấu hình hệ thống chạy động
          example: "max_payment_limit"
        settingValue:
          type: string
          description: Giá trị cấu hình tương ứng
          example: "50000000"
        description:
          type: string
          description: Mô tả chi tiết của tham số cấu hình
          example: "Hạn mức thanh toán tối đa cho phép trong một giao dịch"

    WrapperApiResponse:
      type: object
      properties:
        status:
          type: integer
          format: int32
          description: Mã trạng thái HTTP phản hồi
          example: 200
        message:
          type: string
          description: Thông điệp thông báo kết quả trả về
          example: "Yêu cầu xử lý thành công"
        data:
          type: object
          description: Dữ liệu thực tế phản hồi (Entity DTO, danh sách hoặc Page dữ liệu)
        timestamp:
          type: string
          format: date-time
          description: Thời điểm sinh phản hồi ở server
          example: "2026-06-19T21:45:00+07:00"
```
