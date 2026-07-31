package com.common.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * DTO đại diện cho dữ liệu Hồ sơ người dùng.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileDTO {

    Long id;

    String firstName;

    String lastName;

    String phoneNumber;

    String avatarUrl;

    String position;

    String department;

    Long userId;

    LocalDateTime createdAt;

    LocalDateTime modifiedAt;
}
