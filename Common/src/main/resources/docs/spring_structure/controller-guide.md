# Hướng Dẫn Sử Dụng Lớp BaseController

Tài liệu này hướng dẫn cách tạo và triển khai tầng giao tiếp API (Controller Layer) kế thừa từ lớp `BaseController` của thư viện `Common`.

---

## 1. Cấu Trúc Hệ Thống Controller

Hệ thống Base Controller được định nghĩa tại class abstract: [BaseController](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/controller/BaseController.java).

Lớp này hỗ trợ phân tách Entity cơ sở dữ liệu và DTO Request/Response đầu vào của Client nhằm tăng tính bảo mật thông qua việc khai báo các tham số generic:
1.  `TEntity`: Kiểu Entity của CSDL (SQL/NoSQL).
2.  `TId`: Kiểu dữ liệu của ID (Long, String, v.v.).
3.  `TRequest`: Kiểu dữ liệu DTO đầu vào dùng cho API tạo mới (create) và cập nhật (update).
4.  `TResponse`: Kiểu dữ liệu DTO đầu ra trả về cho Client.
5.  `TService`: Interface Service tương ứng (phải kế thừa từ `IBaseService`).

---

## 2. Các Endpoint Cung Cấp Sẵn

Mọi Controller con khi kế thừa `BaseController` sẽ tự động có sẵn các Endpoint sau:

### A. API Tạo Mới Bản Ghi
- **Method:** `POST`
- **Path:** `/`
- **Request Body:** `@Valid TRequest`
- **Response:** Trả về mã trạng thái `201 Created`.

### B. API Cập Nhật Bản Ghi
- **Method:** `PUT`
- **Path:** `/{id}`
- **Request Body:** `@Valid TRequest`
- **Response:** Trả về mã trạng thái `200 OK` kèm theo thông tin đối tượng đã được cập nhật dạng `TResponse`.

### C. API Xóa Bản Ghi
- **Method:** `DELETE`
- **Path:** `/{id}`
- **Response:** Trả về mã trạng thái `200 OK`. Tự động áp dụng xóa mềm (soft delete) nếu Entity có kế thừa lớp `BaseSoftDeleteModel`.

### D. API Lấy Chi Tiết Bản Ghi
- **Method:** `GET`
- **Path:** `/{id}`
- **Response:** Trả về mã trạng thái `200 OK` kèm theo thông tin chi tiết dưới dạng DTO `TResponse`. Nếu không tìm thấy, hệ thống tự động ném ra `NotFoundException`.

### E. API Lấy Phân Trang & Lọc
- **Method:** `POST`
- **Path:** `/paging`
- **Request Body:** `PagingRequest` (chứa `pageable`, `filter` JSON và tìm kiếm `keyword`).
- **Response:** Trả về đối tượng `Page<TResponse>` (đã tự động lọc các bản ghi xóa mềm).

### F. API Phân Trang & Chiếu Thuộc Tính Động (Dynamic Projection)
- **Method:** `POST`
- **Path:** `/paging/projected`
- **Request Body:** `PagingRequest` có chứa danh sách trường `fields` muốn select (ví dụ: `["id", "name", "createdAt"]`).
- **Response:** Trả về đối tượng `Page<Map<String, Object>>` chỉ chứa đúng các cột/thuộc tính được chỉ định, giúp tối ưu hóa hiệu năng truyền tải mạng.

---

## 3. Hướng Dẫn Triển Khai Tại Dự Án Client

Khi tạo mới một Controller cho một nghiệp vụ nghiệp vụ cụ thể, bạn chỉ cần kế thừa `BaseController`, khai báo các Generic Type và ghi đè (override) 3 phương thức map dữ liệu:

```java
package com.client.controller;

import com.client.model.Product;
import com.client.dto.ProductRequest;
import com.client.dto.ProductResponse;
import com.client.service.contract.IProductService;
import com.common.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController extends BaseController<Product, Long, ProductRequest, ProductResponse, IProductService> {

    public ProductController(IProductService productService) {
        super(productService);
    }

    @Override
    protected ProductResponse toResponse(Product entity) {
        // Ánh xạ từ Entity sang DTO Response (có thể gọi MapStruct)
        return new ProductResponse(entity.getId(), entity.getName(), entity.getPrice());
    }

    @Override
    protected Product toEntity(ProductRequest request) {
        // Ánh xạ từ DTO Request sang Entity mới
        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();
    }

    @Override
    protected void updateEntity(ProductRequest request, Product entity) {
        // Cập nhật thông tin Entity cũ từ Request
        entity.setName(request.getName());
        entity.setPrice(request.getPrice());
    }
}
```
