package com.common.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WrapperApiResponse(
        int status,
        String message,
        Object data,
        OffsetDateTime timestamp
) {
}
