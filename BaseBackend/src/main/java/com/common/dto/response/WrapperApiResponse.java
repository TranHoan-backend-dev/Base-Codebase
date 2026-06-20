package com.common.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.OffsetDateTime;

/**
 * Lớp wrapper dùng để đóng gói 1 response trả về client
 * Created at 06/06/2026
 *
 * @param status
 * @param message
 * @param data
 * @param timestamp
 * @author txhoan
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WrapperApiResponse(
        int status,
        String message,
        Object data,
        OffsetDateTime timestamp
) {
}
