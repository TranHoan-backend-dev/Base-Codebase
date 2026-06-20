package com.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Lớp ngoại lệ cơ sở dùng chung cho các lỗi nghiệp vụ (Business Logic Exceptions) trong hệ thống.<br/>
 * Cho phép chỉ định HTTP Status Code đi kèm thông điệp báo lỗi cụ thể.<br/>
 *
 * @see <a href="../../../../../resources/docs/microservice/global-exception-handling.md">Exception Handling Guide</a>
 * @author txhoan
 */
@Getter
public class BaseException extends RuntimeException {

    private final HttpStatus status;

    public BaseException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST; // Mặc định là 400 Bad Request
    }

    public BaseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public BaseException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
