package com.common.service.contract;

import com.common.model.sql.SystemSetting;

/**
 * Interface cung cấp các nghiệp vụ quản lý cấu hình, tham số hệ thống chạy động.<br/>
 * Kế thừa IBaseService để có sẵn các chức năng CRUD và phân trang nâng cao.<br/>
 * Created at 20/06/2026
 *
 * @see <a href="../../../../../resources/docs/spring_structure/system-settings-guide.md">System Settings Specification Guide</a>
 * @author txhoan
 */
public interface ISystemSettingService extends IBaseService<SystemSetting, Long> {

    /**
     * Lấy giá trị cấu hình hệ thống theo Key, trả về giá trị mặc định nếu không tồn tại.<br/>
     * Created at 20/06/2026
     *
     * @param settingKey   từ khóa cấu hình
     * @param defaultValue giá trị mặc định nếu key chưa được định nghĩa
     * @return giá trị cấu hình tương ứng
     */
    String getSettingValue(String settingKey, String defaultValue);

    /**
     * Cập nhật nhanh giá trị cấu hình hệ thống theo Key (tự động tạo mới nếu chưa tồn tại).<br/>
     * Created at 20/06/2026
     *
     * @param settingKey   từ khóa cấu hình
     * @param settingValue giá trị cấu hình mới
     */
    void updateSettingValue(String settingKey, String settingValue);
}
