# Hướng Dẫn Cấu Hợp và Sử Dụng Datasource (JPA & MongoDB)

Tài liệu này hướng dẫn cách cấu hình và sử dụng hai loại datasource (SQL và NoSQL) được cung cấp sẵn bởi thư viện `Common`.

## 1. Cấu Trúc Model

Thư viện cung cấp hai lớp base model tương ứng với hai loại cơ sở dữ liệu:

* **JPA (SQL):** [BaseModel](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/model/sql/BaseModel.java)
* **MongoDB (NoSQL):** [BaseModel](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/model/nosql/BaseModel.java)

Cả hai lớp này đều tự động quản lý các trường thông tin lịch sử (Auditing):

* `createdAt`: Thời điểm tạo bản ghi.
* `modifiedAt`: Thời điểm chỉnh sửa gần nhất.
* `createdBy`: Người tạo bản ghi (lấy tự động từ Security Context).
* `modifiedBy`: Người chỉnh sửa gần nhất (lấy tự động từ Security Context).

> [!NOTE]
> MongoDB `BaseModel` còn tích hợp sẵn trường định danh `@Id String id` và cơ chế chống xung đột dữ liệu ghi đè `@Version Long version`.

---

## 2. Cách Kích Hoạt Datasource Tại Client App

Mặc định, các cấu hình datasource sẽ không tự động kích hoạt để tránh xung đột khi chạy. Để sử dụng, dự án Client cần thêm annotation tương ứng vào Class chạy chính của ứng dụng (`@SpringBootApplication`).

### A. Kích Hoạt JPA (SQL)

Dành cho các dự án sử dụng hệ quản trị cơ sở dữ liệu quan hệ (PostgreSQL, MySQL, v.v.).

#### 1. Kích hoạt trong Code

Annotate class khởi chạy bằng [@EnableCommonJpa](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/config/jpa/EnableCommonJpa.java):

```java
package com.client;

import com.common.config.jpa.EnableCommonJpa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCommonJpa // <-- Kích hoạt JPA Datasource & Auditing
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
```

#### 2. Cấu hình file `application.yaml`

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

### B. Kích Hoạt MongoDB (NoSQL)

Dành cho các dự án sử dụng MongoDB để lưu trữ dữ liệu phi cấu trúc.

#### 1. Kích hoạt trong Code

Annotate class khởi chạy bằng [@EnableCommonMongo](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/config/mongo/EnableCommonMongo.java):

```java
package com.client;

import com.common.config.mongo.EnableCommonMongo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCommonMongo // <-- Kích hoạt MongoDB Datasource & Auditing
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
```

#### 2. Cấu hình file `application.yaml`

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/your_database
```

---

### C. Sử Dụng Song Song Cả Hai (Dual-Datasource)

Dành cho các dự án microservice cần dùng cả SQL cho dữ liệu giao dịch và NoSQL cho dữ liệu log/chất lượng cao.

```java
package com.client;

import com.common.config.jpa.EnableCommonJpa;
import com.common.config.mongo.EnableCommonMongo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCommonJpa     // Kích hoạt JPA
@EnableCommonMongo   // Kích hoạt MongoDB
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
```

---

## 3. Tạo Entity Kế Thừa BaseModel

### Ví dụ JPA Entity (SQL)

```java
package com.client.model;

import com.common.model.sql.BaseModel;
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
public class Product extends BaseModel {
    @Id
    private Long id; // Định nghĩa ID theo kiểu dữ liệu mong muốn của SQL
    
    private String name;
    private Double price;
    
    protected Product() {} // Constructor no-args bắt buộc cho JPA
}
```

### Ví dụ Mongo Document (NoSQL)

```java
package com.client.model;

import com.common.model.nosql.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
@Getter
@Setter
@SuperBuilder
public class Customer extends BaseModel {
    // Không cần định nghĩa trường id vì đã được kế thừa từ BaseModel (MongoDB)
    
    private String fullName;
    private String email;
    
    protected Customer() {} // Constructor no-args bắt buộc
}
```

> [!WARNING]
> Khi sử dụng Lombok `@SuperBuilder` ở Entity con, lớp cha `BaseModel` cũng phải sử dụng `@SuperBuilder` và có constructor no-args (đã được định nghĩa sẵn bằng `@NoArgsConstructor(access = AccessLevel.PROTECTED)`). Hãy luôn đảm bảo các class con kế thừa cũng tuân thủ cấu trúc này để tránh lỗi biên dịch của Lombok builder.

---

## 4. Tạo Repository Kế Thừa BaseRepository

Các repository con kế thừa sẽ có sẵn các hàm hỗ trợ tìm kiếm nhanh và kiểm tra tồn tại (ví dụ `findByIdOrThrow`, `existsOrThrow`).

### Ví dụ SQL Repository (JPA)
```java
package com.client.repository;

import com.client.model.Product;
import com.common.repository.sql.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
    // Không cần khai báo lại các hàm CRUD cơ bản hay các hàm helper
}
```

### Ví dụ NoSQL Repository (MongoDB)
```java
package com.client.repository;

import com.client.model.Customer;
import com.common.repository.mongo.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends BaseRepository<Customer, String> {
    // Kế thừa sẵn từ MongoRepository và có các hàm helper
}
```
