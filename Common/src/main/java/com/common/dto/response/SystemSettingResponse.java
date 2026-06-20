package com.common.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * DTO phản hồi trả về thông tin cấu hình hệ thống cho Client.<br/>
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
public class SystemSettingResponse {

    Long id;

    String settingKey;

    String settingValue;

    String description;
}
