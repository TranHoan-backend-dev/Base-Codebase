package com.common.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Lớp DTO Request dùng để nhận Request payload data
 * Created at 06/04/2026
 *
 * @param pageable
 * @param filter   Giá trị bộ lọc
 * @param keyword  Từ khóa tìm kiếm
 * @param fields   Danh sách các trường cần lấy (Dynamic Projection)
 * @author txhoan
 */
public record PagingRequest(
        @NotNull(message = "")
        Pageable pageable,
        String filter,
        String keyword,
        List<String> fields
) {
}
