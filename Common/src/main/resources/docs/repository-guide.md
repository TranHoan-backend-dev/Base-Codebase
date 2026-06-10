# Hướng Dẫn Sử Dụng Lớp BaseRepository (SQL & NoSQL)

Tài liệu này hướng dẫn chi tiết cách sử dụng các phương thức có sẵn trong `BaseRepository` của thư viện `Common`. Các phương thức này được thiết kế để giải quyết những nghiệp vụ cơ bản, giảm mã lặp (boilerplate code), tăng hiệu suất truy vấn và tích hợp sẵn đa ngôn ngữ (i18n).

---

## Danh Sách Các Phương Thức

| Tên phương thức | Kiểu trả về | Tham số | Mô tả |
| :--- | :--- | :--- | :--- |
| `findByIdOrThrow(id)` | `T` | `ID id` | Tìm entity theo ID, ném lỗi `NotFoundException` (i18n) nếu không tìm thấy. |
| `findByIdOrThrow(id, message)` | `T` | `ID id, String message` | Tìm entity theo ID, ném lỗi `NotFoundException` kèm thông báo tùy chỉnh nếu không tìm thấy. |
| `existsOrThrow(id)` | `void` | `ID id` | Kiểm tra sự tồn tại của ID, ném lỗi `NotFoundException` (i18n) nếu không tìm thấy. |
| `findAllProjectedBy(pageable, projectionType)` | `Page<P>` | `Pageable, Class<P>` | Truy vấn phân trang dữ liệu thu gọn chỉ với các cột/trường được chỉ định. |

---

## 1. Phương thức `findByIdOrThrow(ID id)`

Tìm kiếm bản ghi trong cơ sở dữ liệu dựa vào khóa chính. Nếu không tìm thấy, hệ thống sẽ tự động phân giải mã lỗi và ném ra ngoại lệ `NotFoundException`.

### Cú pháp

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

> [!NOTE]
> Thông báo lỗi của ngoại lệ ném ra đã được tích hợp sẵn đa ngôn ngữ:
>
> * Ngôn ngữ tiếng Việt: `Không tìm thấy tài nguyên với id: {userId}`
> * Ngôn ngữ tiếng Anh: `Resource not found with id: {userId}`

---

## 2. Phương thức `findByIdOrThrow(ID id, String message)`

Tương tự như phương thức trên nhưng cho phép bạn truyền vào một thông điệp lỗi tùy chỉnh (hoặc một dịch khóa i18n khác).

### Cú pháp

```java
T findByIdOrThrow(ID id, String message);
```

### Cách sử dụng trong Service

```java
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public Order getOrder(Long orderId) {
        // Ném lỗi với thông báo cụ thể cho thực thể đơn hàng
        return orderRepository.findByIdOrThrow(orderId, "Đơn hàng của bạn không tồn tại hoặc đã bị xóa!");
    }
}
```

---

## 3. Phương thức `existsOrThrow(ID id)`

Kiểm tra sự tồn tại của khóa chính trong cơ sở dữ liệu. Thường được sử dụng để thực hiện các nghiệp vụ kiểm tra nhanh trước khi thực hiện hành động liên quan (ví dụ: kiểm tra thực thể cha có tồn tại trước khi tạo thực thể con).

### Cú pháp

```java
void existsOrThrow(ID id);
```

### Cách sử dụng trong Service

```java
@Service
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public void addCommentToPost(Long postId, Comment comment) {
        // Kiểm tra nhanh xem bài viết có tồn tại hay không, nếu không tồn tại ném lỗi NotFoundException
        postRepository.existsOrThrow(postId);
        
        // Tiến hành lưu bình luận mới
        comment.setPostId(postId);
        commentRepository.save(comment);
    }
}
```

---

## 4. Phương thức `findAllProjectedBy(Pageable pageable, Class<P> projectionType)`

Đây là phương thức tối ưu hóa hiệu suất (Performance Optimization). Thay vì truy vấn toàn bộ các trường của bảng/document (sử dụng `SELECT *`), phương thức này cho phép chỉ lấy ra những trường cần thiết thông qua một interface **Projection**.

### Cú pháp

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
    // Bạn chỉ khai báo các thuộc tính muốn lấy ra từ DB
}
```

#### Bước 2: Gọi phương thức trong Service

```java
package com.client.service;

import com.client.dto.ProductSummary;
import com.client.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public Page<ProductSummary> getProductSummaries(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        
        // Chỉ select các cột: id, name, price
        return productRepository.findAllProjectedBy(pageable, ProductSummary.class);
    }
}
```

> [!TIP]
> Sử dụng **Dynamic Projection** giúp giảm tải lưu lượng băng thông giữa ứng dụng và cơ sở dữ liệu, đặc biệt hiệu quả đối với các thực thể chứa các trường dữ liệu lớn (như JSON, BLOB, TEXT) hoặc các tài liệu MongoDB có cấu trúc lồng nhau phức tạp.
