package com.common.repository.sql;

import com.common.model.sql.SystemSetting;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface Repository quản lý thực thể SystemSetting cho cơ sở dữ liệu SQL.<br/>
 * Kế thừa BaseRepository để có sẵn các phương thức CRUD tối ưu.<br/>
 * Created at 20/06/2026
 *
 * @see <a href="../../../../../resources/docs/spring_structure/system-settings-guide.md">System Settings Specification Guide</a>
 * @author txhoan
 */
@Repository
public interface SystemSettingRepository extends BaseRepository<SystemSetting, Long> {

    /**
     * Tìm cấu hình hệ thống bằng từ khóa cấu hình key.<br/>
     * Created at 20/06/2026
     *
     * @param settingKey từ khóa cấu hình duy nhất
     * @return Optional chứa thực thể cấu hình nếu tìm thấy
     */
    Optional<SystemSetting> findBySettingKey(String settingKey);
}
