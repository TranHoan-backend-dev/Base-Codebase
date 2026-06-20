# Hướng Dẫn Sử Dụng Lớp Base Service (SQL & NoSQL)

Tài liệu này hướng dẫn cách tạo và triển khai tầng nghiệp vụ (Service Layer) kế thừa từ hệ thống Base Service của thư viện `Common`.

---

## 1. Cấu Trúc Hệ Thống Service

Hệ thống Base Service bao gồm:

*   **Interface chung:** [IBaseService](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/service/contract/IBaseService.java) định nghĩa các thao tác CRUD cơ bản và phân trang.
*   **Implementation cho JPA (SQL):** [BaseServiceImpl](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/service/impl/BaseServiceImpl.java).
*   **Implementation cho MongoDB (NoSQL):** [BaseMongoServiceImpl](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/service/impl/BaseMongoServiceImpl.java).

---

## 2. Danh Sách Phương Thức Cung Cấp Sẵn

Mọi Service con khi kế thừa sẽ có sẵn các phương thức sau:

| Phương thức | Kiểu trả về | Mô tả |
| :--- | :--- | :--- |
| `create(entity)` | `TEntity` | Lưu một đối tượng mới vào CSDL. |
| `update(entity)` | `void` | Cập nhật thông tin đối tượng đã tồn tại. |
| `delete(entity)` | `void` | Xóa đối tượng. Hỗ trợ tự động xóa mềm (soft delete) nếu entity kế thừa `BaseSoftDeleteModel`. |
| `findById(id)` | `Optional<TEntity>` | Tìm kiếm đối tượng theo ID (Tự động bỏ qua các bản ghi đã bị xóa mềm). |
| `findByIdOrThrow(id)` | `TEntity` | Tìm kiếm đối tượng theo ID, ném lỗi `NotFoundException` nếu không tìm thấy hoặc đã bị xóa mềm. |
| `getPaginated(request)` | `Page<TEntity>` | Truy vấn phân trang, sắp xếp và lọc dữ liệu động (Tự động bỏ qua các bản ghi xóa mềm). |
| `getPaginatedProjected(request)` | `Page<Map<String, Object>>` | Phân trang động chỉ trả về các trường được chỉ định (Dynamic Projection). |

---

## 3. Các Tính Năng Nổi Bật Tích Hợp Sẵn

### A. Tự động Phân tích Kiểu Lớp (Class Reflection)
Cả `BaseServiceImpl` và `BaseMongoServiceImpl` đều tích hợp cơ chế tự động tìm và xác định class của `TEntity` thông qua generic type (`getEntityClass()`). Nhà phát triển không cần chỉ định class thủ công.

### B. Bộ Lọc Động (Dynamic Filtering)
- **JSON Filter**: Tự động parse trường `filter` của `PagingRequest` (dạng JSON String) thành các so khớp bằng (`equal`).
- **Keyword Search**: Tự động quét qua tất cả thuộc tính có kiểu dữ liệu là `String` của Entity hiện tại và các lớp cha để thực hiện tìm kiếm mờ (SQL: `like` case-insensitive, MongoDB: `regex` case-insensitive).

### C. Cơ Chế Xóa Mềm (Soft Delete)
Nếu Entity kế thừa `BaseSoftDeleteModel` (SQL/NoSQL):
- Khi gọi `delete(entity)`: Không xóa vật lý khỏi cơ sở dữ liệu mà cập nhật trường `deleted = true`, ghi nhận thời điểm và người xóa.
- Khi gọi các hàm tìm kiếm (`findById`, `getPaginated`, `getPaginatedProjected`): Tự động thêm điều kiện `deleted = false` vào câu lệnh truy vấn.

### D. Tối Ưu Hóa Chiếu Dữ Liệu (Projection)
- Đối với **SQL**: Sử dụng Tuple multiselect dùng Criteria API giúp sinh câu lệnh SQL chuẩn chỉ SELECT các cột cần lấy, tối ưu tài nguyên database IO.
- Đối với **MongoDB**: Sử dụng `query.fields().include()` của MongoTemplate giúp tối ưu lượng dữ liệu truyền qua mạng giữa Mongo Server và Application Server.

---

## 4. Hướng Dẫn Triển Khai Tại Dự Án Client

### Triển khai với JPA (SQL)

#### Bước 1: Định nghĩa Interface Service con
```java
package com.client.service.contract;

import com.client.model.Product;
import com.common.service.contract.IBaseService;

public interface IProductService extends IBaseService<Product, Long> {
}
```

#### Bước 2: Triển khai Class Service
Kế thừa từ `BaseServiceImpl`:
```java
package com.client.service.impl;

import com.client.model.Product;
import com.client.repository.ProductRepository;
import com.client.service.contract.IProductService;
import com.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl 
        extends BaseServiceImpl<Product, Long, ProductRepository> 
        implements IProductService {

    public ProductServiceImpl(ProductRepository repository) {
        super(repository);
    }
}
```
