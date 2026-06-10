# Hướng Dẫn Sử Dụng Lớp Base Service (SQL & NoSQL)

Tài liệu này hướng dẫn cách tạo và triển khai tầng nghiệp vụ (Service Layer) kế thừa từ hệ thống Base Service của thư viện `Common`.

---

## 1. Cấu Trúc Hệ Thống Service

Hệ thống Base Service bao gồm:

* **Interface chung:** [IBaseService](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/service/contract/IBaseService.java) định nghĩa các thao tác CRUD cơ bản nhất.
* **Implementation cho JPA (SQL):** [BaseServiceImpl](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/service/impl/BaseServiceImpl.java).
* **Implementation cho MongoDB (NoSQL):** [BaseMongoServiceImpl](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/service/impl/BaseMongoServiceImpl.java).

---

## 2. Danh Sách Phương Thức Cung Cấp Sẵn

Mọi Service con khi kế thừa sẽ có sẵn các phương thức sau:

| Phương thức | Kiểu trả về | Mô tả |
| :--- | :--- | :--- |
| `create(entity)` | `TEntity` | Lưu một đối tượng mới vào CSDL. |
| `update(entity)` | `void` | Cập nhật thông tin đối tượng đã tồn tại. |
| `delete(entity)` | `void` | Xóa đối tượng khỏi CSDL. |
| `findById(id)` | `Optional<TEntity>` | Tìm kiếm đối tượng theo ID (trả về Optional). |
| `findByIdOrThrow(id)` | `TEntity` | Tìm kiếm đối tượng theo ID, ném ngay lỗi `NotFoundException` (i18n) nếu không thấy. |
| `getPaginated(request)` | `Page<TEntity>` | Truy vấn phân trang dữ liệu theo `PagingRequest`. |

---

## 3. Hướng Dẫn Triển Khai Tại Dự Án Client

### A. Triển khai với JPA (SQL)

#### Bước 1: Định nghĩa Interface Service con

Kế thừa từ `IBaseService`:

```java
package com.client.service.contract;

import com.client.model.Product;
import com.common.service.contract.IBaseService;

public interface IProductService extends IBaseService<Product, Long> {
    // Định nghĩa thêm các phương thức nghiệp vụ đặc thù của Product tại đây
}
```

#### Bước 2: Triển khai Class Service

Kế thừa từ `BaseServiceImpl`, truyền vào: Entity, ID Type, và Repository class.
Đồng thời, gọi constructor của cha thông qua `super(repository)`:

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

    // Tiêm repository qua constructor và truyền lên class cha
    public ProductServiceImpl(ProductRepository repository) {
        super(repository);
    }
}
```

---

### B. Triển khai với MongoDB (NoSQL)

#### Bước 1: Định nghĩa Interface Service con

```java
package com.client.service.contract;

import com.client.model.Customer;
import com.common.service.contract.IBaseService;

public interface ICustomerService extends IBaseService<Customer, String> {
}
```

#### Bước 2: Triển khai Class Service

Kế thừa từ `BaseMongoServiceImpl`:

```java
package com.client.service.impl;

import com.client.model.Customer;
import com.client.repository.CustomerRepository;
import com.client.service.contract.ICustomerService;
import com.common.service.impl.BaseMongoServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl 
        extends BaseMongoServiceImpl<Customer, String, CustomerRepository> 
        implements ICustomerService {

    public CustomerServiceImpl(CustomerRepository repository) {
        super(repository);
    }
}
```

---

## 4. Quản Lý Giao Dịch (Transaction Management)

Hệ thống Base Service đã được cấu hình sẵn các chỉ thị quản lý giao dịch `@Transactional` tối ưu:

* Các thao tác ghi dữ liệu (`create`, `update`, `delete`) được đánh dấu `@Transactional` để đảm bảo tính toàn vẹn dữ liệu (Rollback khi xảy ra lỗi).
* Các thao tác đọc dữ liệu (`findById`, `getPaginated`) được đánh dấu `@Transactional(readOnly = true)` để tối ưu hóa hiệu năng truy vấn của database.

> [!TIP]
> Nếu các Service con cần thực hiện các chuỗi thao tác nghiệp vụ phức tạp hơn (ví dụ lưu bảng A rồi cập nhật bảng B), bạn chỉ cần override lại method hoặc đánh dấu `@Transactional` ở mức method đặc thù của class con.
