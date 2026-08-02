# Đặc tả API Swagger/OpenAPI 3.0 - Storage API

Tài liệu này cung cấp đặc tả chi tiết về các REST API thao tác lưu trữ file (tải lên và xóa file) được định nghĩa trong `BaseStorageController`.

## OpenAPI 3.0 Specification (YAML Format)

```yaml
openapi: 3.0.3
info:
  title: Base-Codebase Common Storage API
  description: Tài liệu đặc tả các REST API tải lên và xóa tệp tin được cung cấp bởi BaseStorageController.
  version: 1.0.0
servers:
  - url: http://localhost:8080
    description: Cấu hình Local Server mặc định
paths:
  /storage/upload:
    post:
      summary: Tải tệp tin lên hệ thống (local/cloud)
      description: Nhận một tệp tin thông qua multipart/form-data và lưu trữ tệp tin đó vào hệ thống lưu trữ (local disk hoặc cloud storage tùy cấu hình). Trả về thông điệp thành công và tên định danh duy nhất của tệp tin.
      operationId: uploadFile
      requestBody:
        required: true
        content:
          multipart/form-data:
            schema:
              type: object
              properties:
                file:
                  type: string
                  format: binary
                  description: Tệp tin cần tải lên
              required:
                - file
      responses:
        '200':
          description: Tải tệp tin lên thành công
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/WrapperApiResponse'
        '400':
          description: Yêu cầu không hợp lệ hoặc tệp tin trống
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/WrapperApiResponse'
        '500':
          description: Lỗi lưu trữ hệ thống hoặc quá trình ghi file thất bại
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/WrapperApiResponse'

  /storage:
    delete:
      summary: Xóa tệp tin khỏi hệ thống
      description: Thực hiện xóa tệp tin vật lý khỏi hệ thống lưu trữ dựa trên tên file hoặc đường dẫn định danh được truyền vào.
      operationId: deleteFile
      parameters:
        - name: fileIdentifier
          in: query
          required: true
          description: Tên định danh duy nhất hoặc đường dẫn đầy đủ của tệp tin cần xóa
          schema:
            type: string
      responses:
        '200':
          description: Xóa tệp tin thành công
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/WrapperApiResponse'
        '400':
          description: Tham số định danh tệp tin bị thiếu hoặc rỗng
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/WrapperApiResponse'
        '404':
          description: Không tìm thấy tệp tin cần xóa
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/WrapperApiResponse'
        '500':
          description: Gặp lỗi khi thực hiện thao tác xóa tệp tin
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/WrapperApiResponse'

components:
  schemas:
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
          description: Dữ liệu phản hồi thực tế (ví dụ tên file sau khi upload thành công)
        timestamp:
          type: string
          format: date-time
          description: Thời điểm phản hồi được tạo ở phía máy chủ
          example: "2026-06-20T18:00:00+07:00"
```
