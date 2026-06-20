# Hướng Dẫn Sử Dụng API Tham Số Hệ Thống (System Settings)

Tài liệu này hướng dẫn cách cấu hình và sử dụng hệ thống tham số chạy động `SystemSetting` trong thư viện `Common`.

---

## 1. Cơ Chế Hoạt Động

Để thay đổi các giá trị cấu hình vận hành (như hạn mức giao dịch, cờ bật tắt tính năng, cấu hình mail server, vv.) mà không cần phải compile lại code hoặc restart server, chúng ta lưu trữ các cấu hình này dạng Key-Value trong Database.

Hệ thống bao gồm:

1. **Entity:** [SystemSetting](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/model/sql/SystemSetting.java) quản lý key, value và mô tả cấu hình.
2. **Service:** [ISystemSettingService](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/service/contract/ISystemSettingService.java) cung cấp các phương thức CRUD và tìm kiếm tối ưu.
3. **Controller:** [BaseSystemSettingController](file:///d:/Du_an_ca_nhan/Base-Codebase/Common/src/main/java/com/common/controller/BaseSystemSettingController.java) expose các API REST chuẩn cho giao diện Admin quản lý.

---

## 2. Hướng Dẫn Sử Dụng

### A. Cách truy vấn giá trị cấu hình trong Code nghiệp vụ

Nhà phát triển chỉ cần inject `ISystemSettingService` và gọi hàm `getSettingValue`:

```java
package com.client.service;

import com.common.service.contract.ISystemSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ISystemSettingService settingService;

    public void processPayment(double amount) {
        // Lấy hạn mức thanh toán tối đa, nếu chưa cấu hình thì mặc định là 50000000
        String limitStr = settingService.getSettingValue("max_payment_limit", "50000000");
        double limit = Double.parseDouble(limitStr);

        if (amount > limit) {
            throw new IllegalArgumentException("Vượt quá hạn mức thanh toán cho phép");
        }
        // Xử lý thanh toán...
    }
}
```

### B. Cách cập nhật hoặc tạo mới cấu hình nhanh

Sử dụng hàm `updateSettingValue` để ghi đè hoặc khởi tạo cấu hình:

```java
settingService.updateSettingValue("max_payment_limit", "100000000");
```

---

## 3. Quản Lý Tham Số Qua REST API

Client Application chỉ cần tạo một Controller kế thừa `BaseSystemSettingController`:

```java
package com.client.controller;

import com.common.controller.BaseSystemSettingController;
import com.common.service.contract.ISystemSettingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/settings")
public class SystemSettingController extends BaseSystemSettingController {

    public SystemSettingController(ISystemSettingService service) {
        super(service);
    }
}
```

### Danh sách API được mở tự động

- `POST /` : Tạo mới tham số hệ thống.
- `PUT /{id}` : Cập nhật tham số hệ thống theo ID.
- `DELETE /{id}` : Xóa tham số hệ thống.
- `GET /{id}` : Lấy chi tiết tham số hệ thống.
- `POST /paging` : Phân trang, tìm kiếm và lọc tham số hệ thống động.
- `POST /paging/projected` : Phân trang và chỉ chiếu các cột thuộc tính mong muốn.
