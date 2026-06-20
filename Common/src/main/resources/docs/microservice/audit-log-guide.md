# Hướng Dẫn Sử Dụng AOP Audit Logging (Nhật Ký Kiểm Toán)

Tài liệu này hướng dẫn cách sử dụng công cụ Aspect-Oriented Programming (AOP) tự động ghi nhận nhật ký hoạt động của người dùng (`@AuditLog`) trong thư viện `Common`.

---

## 1. Cơ Chế Hoạt Động

`AuditLogAspect` tự động bắt các cuộc gọi đến phương thức Controller được đánh dấu bằng `@AuditLog`. Cơ chế hoạt động như sau:

1. **Trích xuất thông tin Client:** Lấy địa chỉ IP của Client thực hiện Request.
2. **Trích xuất thông tin người dùng:** Lấy mã định danh (`userId`), tên tài khoản (`username`) từ ThreadLocal `UserContextHolder` (được phân giải từ token JWT của Keycloak).
3. **Đo lường hiệu năng:** Tính toán độ trễ xử lý (Latency) tính bằng mili giây.
4. **Ghi nhận trạng thái:** Theo dõi xem phương thức kết thúc thành công hay ném ngoại lệ lỗi.
5. **Ghi log JSON đồng nhất:** Toàn bộ thông tin được format sang dạng JSON và ghi vào log hệ thống để phục vụ việc phân tích log tập trung (ELK Stack / Grafana Loki).

---

## 2. Hướng Dẫn Sử Dụng

### Bước 1: Khai báo Annotation trên REST API

Bạn chỉ cần thêm annotation `@AuditLog` trên các endpoint nghiệp vụ của Controller và chỉ định tên hành động tương ứng:

```java
package com.client.controller;

import com.common.utilities.audit.AuditLog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @PostMapping
    @AuditLog(action = "CREATE_PRODUCT") // <-- Đánh dấu kiểm toán hành động tạo mới
    public ResponseEntity<?> createProduct(@RequestBody ProductDto request) {
        // Xử lý logic...
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/{id}")
    @AuditLog(action = "UPDATE_PRODUCT") // <-- Đánh dấu kiểm toán hành động cập nhật
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDto request) {
        // Xử lý logic...
        return ResponseEntity.ok("Updated");
    }
}
```

---

## 3. Cấu Trúc Log Đầu Ra (AUDIT_LOG JSON)

Mỗi khi API được gọi, một dòng nhật ký kiểm toán sẽ được tự động ghi nhận dưới định dạng:

```json
AUDIT_LOG: {"action":"CREATE_PRODUCT","className":"ProductController","methodName":"createProduct","clientIp":"127.0.0.1","userId":"9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d","username":"txhoan","status":"SUCCESS","latencyMs":14,"timestamp":"2026-06-20T17:50:33.456"}
```

Nếu xảy ra lỗi/ngoại lệ:

```json
AUDIT_LOG: {"action":"CREATE_PRODUCT","className":"ProductController","methodName":"createProduct","clientIp":"127.0.0.1","userId":"9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d","username":"txhoan","status":"FAILED","latencyMs":5,"timestamp":"2026-06-20T17:51:12.123","error":"Tên sản phẩm đã tồn tại"}
```
