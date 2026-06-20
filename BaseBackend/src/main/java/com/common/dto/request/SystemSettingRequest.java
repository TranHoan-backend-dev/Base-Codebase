package com.common.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * DTO yêu cầu gửi lên từ Client để tạo mới hoặc cập nhật cấu hình hệ thống.<br/>
 * Created at 20/06/2026
 *
 * @author txhoan
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SystemSettingRequest {

    @NotBlank(message = "{system_setting.key.blank}")
    @Size(max = 100, message = "{system_setting.key.size}")
    String settingKey;

    @NotBlank(message = "{system_setting.value.blank}")
    @Size(max = 1000, message = "{system_setting.value.size}")
    String settingValue;

    @Size(max = 255, message = "{system_setting.description.size}")
    String description;
}
