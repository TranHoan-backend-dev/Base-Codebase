package com.common.exception;

/**
 * Ngoại lệ báo lỗi 400 Bad Request cho các lỗi dữ liệu đầu vào hoặc xác thực thất bại.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
