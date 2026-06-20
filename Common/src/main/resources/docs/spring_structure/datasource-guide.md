# Hướng Dẫn Cấu Hình và Sử Dụng Datasource (JPA & MongoDB)

Tài liệu này hướng dẫn cách cấu hình và sử dụng hai loại datasource (SQL và NoSQL) được cung cấp sẵn bởi thư viện
`Common`.

---

## 1. Kiến Trúc Dual Datasource (SQL)

Thư viện `Common` tổ chức SQL thành **2 datasource tách biệt** để phân tách trách nhiệm rõ ràng:

| Datasource   | Bean qualifier                   | Mục đích                                                                   | Package scan entity    |
|--------------|----------------------------------|----------------------------------------------------------------------------|------------------------|
| **business** | `businessDataSource` *(primary)* | Entity nghiệp vụ của từng service                                          | `com.*.model.business` |
| **system**   | `systemDataSource`               | Entity kỹ thuật dùng chung (OutboxEvent, SystemSetting, DynamicGridConfig) | `com.common.model.sql` |

> **Lưu ý:** Mỗi datasource có `EntityManagerFactory` và `TransactionManager` riêng biệt.

---

## 2. Cấu Trúc Model

### Model nghiệp vụ (Business Datasource)

Các entity nghiệp vụ của service con kế thừa từ:

- **JPA (
  SQL):** [BaseModel](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/model/sql/BaseModel.java) —
  audit fields cơ bản
- **MongoDB (
  NoSQL):** [BaseModel](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/model/nosql/BaseModel.java)

#### Tùy chọn Xóa Mềm (Soft Delete)

- **JPA (
  SQL):** [BaseSoftDeleteModel](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/model/sql/BaseSoftDeleteModel.java)
- **MongoDB (
  NoSQL):** [BaseSoftDeleteModel](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/model/nosql/BaseSoftDeleteModel.java)

Cả hai lớp base tự động quản lý các trường audit:

| Field                      | Mô tả                               |
|----------------------------|-------------------------------------|
| `createdAt`                | Thời điểm tạo bản ghi               |
| `modifiedAt`               | Thời điểm chỉnh sửa gần nhất        |
| `createdBy`                | Người tạo (lấy từ Security Context) |
| `modifiedBy`               | Người chỉnh sửa gần nhất            |
| `deleted` *(SoftDelete)*   | Flag đánh dấu đã xóa                |
| `deletedAt` *(SoftDelete)* | Thời điểm xóa                       |
| `deletedBy` *(SoftDelete)* | Người thực hiện xóa                 |

### Model kỹ thuật (System Datasource)

Các entity kỹ thuật nằm sẵn trong `com.common.model.sql`:

| Entity                                                                                                                       | Mô tả                              |
|------------------------------------------------------------------------------------------------------------------------------|------------------------------------|
| [OutboxEvent](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/model/sql/OutboxEvent.java)             | Transactional Outbox Pattern       |
| [SystemSetting](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/model/sql/SystemSetting.java)         | Cấu hình hệ thống động             |
| [DynamicGridConfig](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/model/sql/DynamicGridConfig.java) | Cấu hình bảng động render trên web |
| [DynamicGridColumn](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/model/sql/DynamicGridColumn.java) | Cấu hình từng cột của bảng động    |

---

## 3. Cách Kích Hoạt Tại Client App

### Bước 1 — Annotate class khởi chạy

```java

@SpringBootApplication
@EnableCommonJpa  // Kích hoạt cả 2 datasource (business + system) & Auditing
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
```

### Bước 2 — Cấu hình `application.yaml`

```yaml
spring:
  datasource:
    business:
      url: jdbc:postgresql://localhost:5432/db_business
      username: your_username
      password: your_password
      driver-class-name: org.postgresql.Driver
      hikari:
        pool-name: BusinessHikariPool
        maximum-pool-size: 10
    system:
      url: jdbc:postgresql://localhost:5432/db_system
      username: your_username
      password: your_password
      driver-class-name: org.postgresql.Driver
      hikari:
        pool-name: SystemHikariPool
        maximum-pool-size: 5
```

### Bước 3 — Đặt entity nghiệp vụ đúng package

Entity nghiệp vụ của service con **phải đặt trong package `model.business`** để được scan bởi `BusinessJpaConfig`:

```
com.your-service.model.business.Product      ✅ Business datasource
com.your-service.model.business.Order        ✅ Business datasource
com.your-service.model.sql.SomeEntity        ❌ Sẽ không được scan
```

---

## 4. Tạo Entity Nghiệp Vụ

```java
package com.client.model.business;

import com.common.model.sql.BaseSoftDeleteModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "products")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseSoftDeleteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
}
```

---

## 5. Tạo Repository Nghiệp Vụ

Repository con phải nằm trong package `repository.business` để được scan bởi `BusinessJpaConfig`:

```java
package com.client.repository.business;

import com.client.model.business.Product;
import com.common.repository.sql.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
}
```

---

## 6. Sử Dụng @Transactional Đúng Datasource

```java
// Nghiệp vụ → dùng businessTransactionManager (mặc định @Primary)
@Transactional
public void createProduct(Product product) { ...}

// Kỹ thuật → phải khai báo qualifier rõ ràng
@Transactional("systemTransactionManager")
public void saveOutboxEvent(OutboxEvent event) { ...}
```

---

## 7. DynamicGridConfig — Cấu Hình Bảng Động

`DynamicGridConfig` cho phép lưu cấu hình render bảng lên web mà không cần deploy lại code.

### Cấu trúc dữ liệu

```
DynamicGridConfig (1 bảng)
└── DynamicGridColumn[] (n cột)
```

### Thuộc tính cột (`DynamicGridColumn`)

| Thuộc tính     | Kiểu           | Mặc định    | Mô tả                                                                           |
|----------------|----------------|-------------|---------------------------------------------------------------------------------|
| `fieldName`    | String         | —           | Tên field trong API response                                                    |
| `headerLabel`  | String         | —           | Tiêu đề cột                                                                     |
| `dataType`     | ColumnDataType | —           | Kiểu dữ liệu (TEXT, NUMBER, DATE, DATETIME, BOOLEAN, ENUM, BADGE, LINK, ACTION) |
| `width`        | Integer        | null (auto) | Độ rộng cột (px)                                                                |
| `minWidth`     | Integer        | null        | Độ rộng tối thiểu (px)                                                          |
| `pinned`       | PinnedPosition | null        | Ghim cột: LEFT / RIGHT / null                                                   |
| `resizable`    | boolean        | `true`      | Cho phép kéo thay đổi width                                                     |
| `sortable`     | boolean        | `true`      | Cho phép sắp xếp                                                                |
| `filterable`   | boolean        | `false`     | Cho phép lọc                                                                    |
| `visible`      | boolean        | `true`      | Ẩn/hiện cột                                                                     |
| `displayOrder` | int            | `0`         | Thứ tự hiển thị (ascending)                                                     |

### Ví dụ tra cứu config

```java

@Service
@RequiredArgsConstructor
public class GridService {

    private final DynamicGridConfigRepository gridConfigRepo;

    @Transactional(value = "systemTransactionManager", readOnly = true)
    public DynamicGridConfig getGridConfig(String gridKey) {
        return gridConfigRepo.findByGridKey(gridKey)
                .orElseThrow(() -> new NotFoundException("Grid config not found: " + gridKey));
    }
}
```
