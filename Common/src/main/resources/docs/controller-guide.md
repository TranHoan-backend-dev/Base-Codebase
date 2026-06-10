# Hướng Dẫn Sử Dụng Lớp BaseController

Tài liệu này hướng dẫn cách tạo và triển khai tầng giao tiếp API (Controller Layer) kế thừa từ lớp `BaseController` của thư viện `Common`.

---

## 1. Cấu Trúc Hệ Thống Controller

Hệ thống Base Controller được định nghĩa tại class abstract: [BaseController](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/controller/BaseController.java).

Lớp này không có annotation `@RestController` hay `@RequestMapping` ở mức class. Điều này nhằm giúp các Controller con khi kế thừa có toàn quyền quyết định Request path (ví dụ: `/products`, `/customers`) và các cấu hình bảo mật riêng biệt.

---

## 2. Các Endpoint Cung Cấp Sẵn

Mọi Controller con khi kế thừa `BaseController` sẽ tự động có sẵn 2 Endpoint sau:

### A. API Lấy Chi Tiết Bản Ghi Theo ID
*   **Method:** `GET`
*   **Path:** `/{id}`
*   **Response:** `ResponseEntity<WrapperApiResponse>` chứa Entity/Document tìm được. Nếu không tìm thấy, hệ thống tự động ném ra `NotFoundException` (đã được cấu hình i18n đa ngôn ngữ).

### B. API Lấy Danh Sách Phân Trang & Lọc
*   **Method:** `POST`
*   **Path:** `/paging`
*   **Request Body:** `PagingRequest` (chứa thông tin phân trang `pageable`, bộ lọc `filter`, từ khóa `keyword`).
*   **Response:** `ResponseEntity<WrapperApiResponse>` chứa đối tượng `Page<TEntity>`.

---

## 3. Hướng Dẫn Triển Khai Tại Dự Án Client

Khi tạo mới một Controller cho một nghiệp vụ nghiệp vụ cụ thể, bạn chỉ cần kế thừa `BaseController` và khai báo các thông số Generic:
1.  `TEntity`: Kiểu Entity của CSDL (SQL/NoSQL).
2.  `TId`: Kiểu dữ liệu của ID (Long, String, v.v.).
3.  `TService`: Interface Service tương ứng (phải kế thừa từ `IBaseService`).

### Ví dụ ProductController (SQL - JPA)
```java
package com.client.controller;

import com.client.model.Product;
import com.client.service.contract.IProductService;
import com.common.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products") // Định nghĩa Request path prefix cho Product
public class ProductController extends BaseController<Product, Long, IProductService> {

    // Tiêm Product Service qua Constructor và truyền lên Class cha
    public ProductController(IProductService productService) {
        super(productService);
    }

    // Bạn có thể viết thêm các API nghiệp vụ đặc thù của Product tại đây
}
```

Khi đó, Client Application sẽ tự động có các URL:
*   `GET /products/{id}`: Lấy thông tin sản phẩm theo ID.
*   `POST /products/paging`: Lấy danh sách sản phẩm phân trang.

---

## 4. Cấu Trúc Dữ Liệu Phản Hồi (WrapperApiResponse)

Tất cả các dữ liệu phản hồi trả về từ `BaseController` đều được chuẩn hóa thống nhất theo cấu trúc:
```json
{
  "status": 200,
  "message": "Lấy thông tin thành công.",
  "data": { ... },
  "timestamp": "2026-06-10T21:35:00+07:00"
}
```
*   `status`: Mã HTTP Status Code.
*   `message`: Thông điệp phản hồi (đã được việt hóa/quốc tế hóa).
*   `data`: Dữ liệu trả về thực tế (đối tượng chi tiết hoặc cấu trúc phân trang).
*   `timestamp`: Thời gian phản hồi của hệ thống.
