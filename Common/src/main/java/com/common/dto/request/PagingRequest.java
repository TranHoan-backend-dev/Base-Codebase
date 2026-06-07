package com.common.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

/**
 * Lớp DTO Request dùng để nhận Request payload data
 * Created at 06/04/2026
 *
 * @param pageable
 * @param filter   Giá trị bộ lọc
 * @param keyword  Từ khóa tìm kiếm
 * @author txhoan
 */
public record PagingRequest(
        @NotNull(message = "")
        Pageable pageable,
        String filter,
        String keyword
) {
}
