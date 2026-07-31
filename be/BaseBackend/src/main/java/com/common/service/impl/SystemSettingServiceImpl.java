package com.common.service.impl;

import com.common.model.sql.SystemSetting;
import com.common.repository.sql.SystemSettingRepository;
import com.common.service.contract.ISystemSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Lớp triển khai các nghiệp vụ quản lý cấu hình, tham số hệ thống chạy động.<br/>
 * Kế thừa BaseServiceImpl để có sẵn các hàm lọc phân trang động Criteria Query.<br/>
 * Created at 20/06/2026
 *
 * @see <a href="../../../../../resources/docs/spring_structure/system-settings-guide.md">System Settings Specification Guide</a>
 * @author txhoan
 */
@Service
@Slf4j
public class SystemSettingServiceImpl
        extends BaseServiceImpl<SystemSetting, Long, SystemSettingRepository>
        implements ISystemSettingService {

    public SystemSettingServiceImpl(SystemSettingRepository repository) {
        super(repository);
    }

    /**
     * Lấy giá trị cấu hình hệ thống theo Key, trả về giá trị mặc định nếu không tìm thấy.<br/>
     * Created at 20/06/2026
     */
    @Override
    @Transactional(readOnly = true)
    public String getSettingValue(String settingKey, String defaultValue) {
        log.info("Lấy giá trị cấu hình cho key: {}", settingKey);
        return repository.findBySettingKey(settingKey)
                .map(SystemSetting::getSettingValue)
                .orElse(defaultValue);
    }

    /**
     * Cập nhật nhanh giá trị cấu hình hệ thống theo Key (tự động tạo mới nếu chưa tồn tại).<br/>
     * Created at 20/06/2026
     */
    @Override
    @Transactional
    public void updateSettingValue(String settingKey, String settingValue) {
        log.info("Cập nhật giá trị cấu hình cho key: {} -> {}", settingKey, settingValue);
        var settingOpt = repository.findBySettingKey(settingKey);
        if (settingOpt.isPresent()) {
            var setting = settingOpt.get();
            setting.setSettingValue(settingValue);
            repository.save(setting);
        } else {
            var setting = SystemSetting.builder()
                    .settingKey(settingKey)
                    .settingValue(settingValue)
                    .description("Auto-created setting via service update")
                    .build();
            repository.save(setting);
        }
    }
}
