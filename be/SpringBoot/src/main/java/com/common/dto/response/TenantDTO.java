package com.common.dto.response;

import com.common.model.enumerate.EntityStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * DTO đại diện cho dữ liệu Tenant.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TenantDTO {

    Long id;

    String name;

    String code;

    String domain;

    EntityStatus status;

    LocalDateTime createdAt;

    LocalDateTime modifiedAt;
}
