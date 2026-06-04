package com.common.dto.request;

import org.springframework.data.domain.Pageable;

/**
 * Lớp DTO Request dùng để nhận Request payload data
 * Created at 06/04/2026
 *
 * @param pageable
 * @author txhoan
 */
public record PagingRequest(Pageable pageable) {
}
