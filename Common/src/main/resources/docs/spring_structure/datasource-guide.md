# Hướng Dẫn Cấu Hình và Sử Dụng Datasource (JPA & MongoDB)

Tài liệu này hướng dẫn cách cấu hình và sử dụng hai loại datasource (SQL và NoSQL) được cung cấp sẵn bởi thư viện `Common`.

---

## 1. Cấu Trúc Model

Thư viện cung cấp hai lớp base model tương ứng với hai loại cơ sở dữ liệu:

*   **JPA (SQL):** [BaseModel](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/model/sql/BaseModel.java)
*   **MongoDB (NoSQL):** [BaseModel](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/model/nosql/BaseModel.java)

Cả hai lớp này đều tự động quản lý các trường thông tin lịch sử (Auditing):
*   `createdAt`: Thời điểm tạo bản ghi.
*   `modifiedAt`: Thời điểm chỉnh sửa gần nhất.
*   `createdBy`: Người tạo bản ghi (lấy tự động từ Security Context).
*   `modifiedBy`: Người chỉnh sửa gần nhất (lấy tự động từ Security Context).

### Tùy chọn Xóa mềm (Soft Delete)
Ngoài ra, thư viện cung cấp hai lớp hỗ trợ Xóa Mềm nếu nghiệp vụ của bạn yêu cầu không xóa vật lý bản ghi:
*   **JPA (SQL):** [BaseSoftDeleteModel](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/model/sql/BaseSoftDeleteModel.java)
*   **MongoDB (NoSQL):** [BaseSoftDeleteModel](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/model/nosql/BaseSoftDeleteModel.java)

Hai lớp này bổ sung thêm các trường:
*   `deleted`: Flag đánh dấu đã xóa (`true`/`false`).
*   `deletedAt`: Thời điểm xóa bản ghi.
*   `deletedBy`: Người thực hiện xóa bản ghi.

---

## 2. Cách Kích Hoạt Datasource Tại Client App

Để sử dụng, dự án Client cần thêm annotation tương ứng vào Class chạy chính của ứng dụng (`@SpringBootApplication`).

### A. Kích Hoạt JPA (SQL)
Annotate class khởi chạy bằng `@EnableCommonJpa`:
```java
package com.client;

import com.common.config.jpa.EnableCommonJpa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCommonJpa // Kích hoạt JPA Datasource & Auditing
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
```

### B. Cấu hình file `application.yaml`
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/your_database
    username: your_username
    password: your_password
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

---

## 3. Tạo Entity Kế Thừa BaseModel hoặc BaseSoftDeleteModel

### Ví dụ JPA Entity với Xóa Mềm (SQL)
```java
package com.client.model;

import com.common.model.sql.BaseSoftDeleteModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "products")
@Getter
@Setter
@SuperBuilder
public class Product extends BaseSoftDeleteModel {
    @Id
    private Long id;
    
    private String name;
    private Double price;
    
    protected Product() {} // Constructor no-args bắt buộc cho JPA
}
```

---

## 4. Tạo Repository Kế Thừa BaseRepository

Các repository con kế thừa sẽ có sẵn các hàm hỗ trợ tìm kiếm nhanh và kiểm tra tồn tại.

### Ví dụ SQL Repository (JPA)
```java
package com.client.repository;

import com.client.model.Product;
import com.common.repository.sql.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
}
```
