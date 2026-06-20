# Hướng Dẫn Sử Dụng Lớp BaseRepository (SQL & NoSQL)

Tài liệu này hướng dẫn chi tiết cách sử dụng các phương thức có sẵn trong `BaseRepository` của thư viện `Common`. Các phương thức này được thiết kế để giải quyết những nghiệp vụ cơ bản, giảm mã lặp (boilerplate code), tăng hiệu suất truy vấn và tích hợp sẵn đa ngôn ngữ (i18n).

---

## Danh Sách Các Phương Thức & Interface

| Tên phương thức | Kiểu trả về | Tham số | Mô tả |
| :--- | :--- | :--- | :--- |
| `findByIdOrThrow(id)` | `T` | `ID id` | Tìm entity theo ID, ném lỗi `NotFoundException` (i18n) nếu không tìm thấy. |
| `findByIdOrThrow(id, message)` | `T` | `ID id, String message` | Tìm entity theo ID, ném lỗi `NotFoundException` kèm thông báo tùy chỉnh nếu không tìm thấy. |
| `existsOrThrow(id)` | `void` | `ID id` | Kiểm tra sự tồn tại của ID, ném lỗi `NotFoundException` (i18n) nếu không tìm thấy. |
| `findAllProjectedBy(pageable, projectionType)` | `Page<P>` | `Pageable, Class<P>` | Truy vấn phân trang dữ liệu thu gọn chỉ với các cột/trường được chỉ định. |

> [!IMPORTANT]
> Đối với SQL (JPA), [BaseRepository](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/repository/sql/BaseRepository.java) hiện đã kế thừa thêm interface `JpaSpecificationExecutor<T>`. Điều này cho phép bạn gọi trực tiếp tất cả các phương thức tìm kiếm nâng cao sử dụng JPA Specification, ví dụ: `repository.findAll(Specification, Pageable)`.

---

## 1. Phương thức `findByIdOrThrow(ID id)`

Tìm kiếm bản ghi trong cơ sở dữ liệu dựa vào khóa chính. Nếu không tìm thấy, hệ thống sẽ tự động phân giải mã lỗi và ném ra ngoại lệ `NotFoundException`.

```java
T findByIdOrThrow(ID id);
```

### Cách sử dụng trong Service
```java
@Service
public class UserService {
    private final UserRepository userRepository; // Kế thừa từ BaseRepository

    public User getUser(Long userId) {
        // Trả về trực tiếp User entity mà không cần viết .orElseThrow(...)
        return userRepository.findByIdOrThrow(userId);
    }
}
```

---

## 2. Phương thức `findByIdOrThrow(ID id, String message)`

Tương tự như phương thức trên nhưng cho phép bạn truyền vào một thông điệp lỗi tùy chỉnh (hoặc một dịch khóa i18n khác).

```java
T findByIdOrThrow(ID id, String message);
```

---

## 3. Phương thức `existsOrThrow(ID id)`

Kiểm tra sự tồn tại của khóa chính trong cơ sở dữ liệu. Thường được sử dụng để thực hiện các nghiệp vụ kiểm tra nhanh trước khi thực hiện hành động liên quan.

```java
void existsOrThrow(ID id);
```

---

## 4. Phương thức `findAllProjectedBy(Pageable pageable, Class<P> projectionType)`

Đây là phương thức tối ưu hóa hiệu suất (Performance Optimization). Thay vì truy vấn toàn bộ các trường của bảng/document, phương thức này cho phép chỉ lấy ra những trường cần thiết thông qua một interface **Projection**.

```java
<P> Page<P> findAllProjectedBy(Pageable pageable, Class<P> projectionType);
```

### Quy trình sử dụng

#### Bước 1: Định nghĩa Interface chứa các Getter cần lấy
```java
package com.client.dto;

public interface ProductSummary {
    Long getId();
    String getName();
    Double getPrice();
}
```

#### Bước 2: Gọi phương thức trong Service
```java
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public Page<ProductSummary> getProductSummaries(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        // Chỉ select các cột: id, name, price từ DB
        return productRepository.findAllProjectedBy(pageable, ProductSummary.class);
    }
}
```
