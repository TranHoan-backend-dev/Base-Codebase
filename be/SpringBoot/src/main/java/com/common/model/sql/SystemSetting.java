package com.common.model.sql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Thực thể lưu trữ cấu hình, tham số hệ thống chạy động trong cơ sở dữ liệu SQL.<br/>
 * Kế thừa BaseModel để tự động quản lý các trường kiểm toán (Audit fields).<br/>
 * Created at 20/06/2026
 *
 * @see <a href="../../../../../resources/docs/spring_structure/system-settings-guide.md">System Settings Specification Guide</a>
 * @author txhoan
 */
@Entity
@Table(name = "system_settings")
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SystemSetting extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "setting_key", nullable = false, unique = true)
    String settingKey;

    @Column(name = "setting_value", nullable = false, length = 1000)
    String settingValue;

    @Column(name = "description")
    String description;
}
