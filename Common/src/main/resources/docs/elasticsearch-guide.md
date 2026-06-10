# Hướng Dẫn Cấu Hình và Sử Dụng Elasticsearch với MapStruct

Tài liệu này hướng dẫn cách cấu hình Elasticsearch trong thư viện `Common`, cách sử dụng MapStruct để ánh xạ (mapping) dữ liệu từ Entity (SQL/NoSQL) sang Search Document của Elasticsearch, và cách đồng bộ dữ liệu phục vụ tìm kiếm toàn văn (Full-text Search).

---

## 1. Cấu Hình Elasticsearch

### Bước 1: Kích hoạt tại Client Application

Dự án Client muốn sử dụng Elasticsearch cần thêm annotation [@EnableCommonElasticsearch](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/config/elasticsearch/EnableCommonElasticsearch.java) vào class khởi chạy chính:

```java
package com.client;

import com.common.config.elasticsearch.EnableCommonElasticsearch;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCommonElasticsearch // <-- Kích hoạt cấu hình Elasticsearch & Auditing
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
```

### Bước 2: Cấu hình kết nối (Tùy chọn)

Mặc định, thư viện `Common` đã tự động cấu hình sẵn các giá trị kết nối mặc định của Spring Boot Elasticsearch trong file cấu hình nội bộ:

* `spring.elasticsearch.uris`: `http://localhost:9200`
* `spring.elasticsearch.username`: `elastic`
* `spring.elasticsearch.password`: `password`

Dự án Client **không cần phải cấu hình gì thêm** nếu sử dụng môi trường mặc định này. Nếu muốn ghi đè các cấu hình kết nối (ví dụ khi kết nối đến cụm server Production), Client chỉ cần khai báo lại các thuộc tính chuẩn của Spring Boot trong file `application.yaml` như sau:

```yaml
spring:
  elasticsearch:
    uris: http://production-server:9200
    username: my_username
    password: my_secure_password
```

> [!NOTE]
> Thư viện tích hợp sẵn Auditing cho Elasticsearch thông qua `@EnableElasticsearchAuditing`. Các trường `@CreatedDate`, `@LastModifiedDate` trên Document sẽ tự động được điền giá trị giống như JPA và MongoDB.

---

## 2. Tạo Search Document cho Elasticsearch

Tạo một class Java đại diện cho Index trong Elasticsearch. Các trường dữ liệu phục vụ tìm kiếm nên được cấu hình kiểu dữ liệu (`FieldType`) phù hợp (ví dụ: `Text` kèm analyzer cho tìm kiếm toàn văn, `Keyword` cho tìm kiếm chính xác/filter).

```java
package com.client.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "products")
@Getter
@Setter
@Builder
public class ProductSearchDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String name;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String description;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Keyword)
    private String category;
}
```

---

## 3. Tạo Mapper MapStruct chuyển đổi Dữ liệu

Để đẩy dữ liệu từ Entity (SQL JPA hoặc MongoDB) sang Elasticsearch Document, chúng ta sử dụng **MapStruct** để tự động sinh code chuyển đổi một cách nhanh nhất và tránh gõ sai tên trường.

### Định nghĩa Mapper Interface

```java
package com.client.mapper;

import com.client.document.ProductSearchDocument;
import com.client.model.Product; // Giả sử là JPA Entity hoặc Mongo Document
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // Đã cấu hình mặc định là spring trong build.gradle.kts
public interface ProductSearchMapper {

    // Ánh xạ tự động các trường trùng tên.
    // Nếu có trường khác tên, dùng @Mapping(source = "entityField", target = "documentField")
    @Mapping(target = "id", expression = "java(String.valueOf(product.getId()))") // Ép kiểu ID sang String cho ES
    ProductSearchDocument toSearchDocument(Product product);
}
```

---

## 4. Đồng Bộ Dữ Liệu và Tìm Kiếm (Service Layer)

### Bước 1: Tạo Elasticsearch Repository

```java
package com.client.repository.search;

import com.client.document.ProductSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductSearchDocument, String> {
    
    // Spring Data tự động sinh query tìm kiếm toàn văn theo tên
    List<ProductSearchDocument> findByNameContaining(String name);
}
```

### Bước 2: Viết Service đồng bộ và tìm kiếm

```java
package com.client.service;

import com.client.document.ProductSearchDocument;
import com.client.mapper.ProductSearchMapper;
import com.client.model.Product;
import com.client.repository.ProductRepository; // JPA Repository
import com.client.repository.search.ProductSearchRepository; // ES Repository
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductSearchRepository productSearchRepository;
    private final ProductSearchMapper productSearchMapper;

    @Transactional
    public Product createProduct(Product product) {
        // 1. Lưu vào SQL Database
        Product savedProduct = productRepository.save(product);

        // 2. Map sang Search Document bằng MapStruct
        ProductSearchDocument searchDoc = productSearchMapper.toSearchDocument(savedProduct);

        // 3. Đẩy vào Elasticsearch Index
        productSearchRepository.save(searchDoc);

        return savedProduct;
    }

    public List<ProductSearchDocument> searchProductsByName(String keyword) {
        // Tìm kiếm trên Elasticsearch
        return productSearchRepository.findByNameContaining(keyword);
    }
}
```

> [!TIP]
> **Đồng bộ bất đồng bộ:** Trong các dự án thực tế quy mô lớn, để tránh ảnh hưởng đến thời gian phản hồi (latency) của API lưu dữ liệu, bạn nên xử lý đồng bộ sang Elasticsearch bất đồng bộ thông qua Message Queue (như RabbitMQ đã được cấu hình sẵn trong project) hoặc Spring `@Async` / Application Events.
