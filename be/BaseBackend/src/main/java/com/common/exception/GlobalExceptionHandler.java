package com.common.exception;

import com.common.dto.response.WrapperApiResponse;
import com.common.utilities.Utils;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.HttpStatus;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Bộ xử lý ngoại lệ toàn cục cho ứng dụng (Global Exception Handler).<br/>
 * Đóng vai trò bắt tất cả các Exception ném ra từ Controller/Service
 * để chuyển đổi thành cấu hình WrapperApiResponse chuẩn với mã lỗi HTTP chính xác.<br/>
 * Created at 10/06/2026, Updated at 19/06/2026
 *
 * @see <a href="../../../../resources/docs/utilities/utilities-guide.md">Utilities Specification Guide</a>
 * @author txhoan
 */
@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleInvalidFormat(@NonNull HttpMessageNotReadableException ex) {
        log.error(ex.getMessage());
        return Utils.returnBadRequestResponse(
                "Ngày tháng năm phải đúng định dạng yyyy-MM-dd và là ngày hợp lệ",
                null
        );
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<WrapperApiResponse> handleDateTimeParseException(@NonNull DateTimeParseException ex) {
        return Utils.returnBadRequestResponse(ex.getMessage(), null);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<WrapperApiResponse> handleForbiddenException(@NonNull ForbiddenException ex) {
        return Utils.returnForbiddenResponse(ex.getMessage(), null);
    }

    @ExceptionHandler(ExistingException.class)
    public ResponseEntity<WrapperApiResponse> handleExistingException(@NonNull ExistingException ex) {
        return Utils.returnBadRequestResponse(ex.getMessage(), null);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<WrapperApiResponse> handleBadCredentialsException(@NonNull BadCredentialsException ex) {
        return Utils.returnUnAuthorizedResponse("Invalid email or password", ex.getMessage());
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<WrapperApiResponse> handleDisabledException(@NonNull DisabledException ex) {
        return Utils.returnUnAuthorizedResponse(ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WrapperApiResponse> handleValidationExceptions(@NonNull MethodArgumentNotValidException ex) {
        var errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return Utils.returnBadRequestResponse("Validation failed", errors);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<WrapperApiResponse> handleInternalServerException(@NonNull InternalServerException ex) {
        return Utils.returnInternalServerErrorResponse(ex.getMessage(), null);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<WrapperApiResponse> handleNotExistingException(@NonNull NotFoundException ex) {
        return Utils.returnNotFoundResponse(ex.getMessage(), null);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<WrapperApiResponse> handleFeignException(@NonNull FeignException ex) {
        return Utils.returnInternalServerErrorResponse(ex.getMessage(), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<WrapperApiResponse> handleIllegalArgumentException(@NonNull IllegalArgumentException ex) {
        return Utils.returnBadRequestResponse(ex.getMessage(), null);
    }

    /**
     * Bắt ngoại lệ xung đột phiên bản dữ liệu khi cập nhật đồng thời (Optimistic Locking).<br/>
     * Trả về phản hồi lỗi HTTP 409 Conflict chuẩn.<br/>
     * Created at 19/06/2026
     *
     * @param ex Ngoại lệ bắt được
     * @return Phản hồi mã 409 Conflict
     * @see <a href="../../../../resources/docs/utilities/utilities-guide.md">Utilities Specification Guide (Optimistic Locking Section)</a>
     */
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<WrapperApiResponse> handleOptimisticLockingFailureException(@NonNull ObjectOptimisticLockingFailureException ex) {
        log.error("Optimistic locking failure: {}", ex.getMessage());
        return Utils.returnConflictResponse("Dữ liệu đã bị sửa đổi bởi người dùng khác. Vui lòng tải lại trang và thực hiện lại.", null);
    }

    @ExceptionHandler({InterruptedException.class, ExecutionException.class})
    public ResponseEntity<WrapperApiResponse> handleInterruptedAndExecutionException(@NonNull Exception ex) {
        return Utils.returnInternalServerErrorResponse(ex.getMessage(), null);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<WrapperApiResponse> handleBaseException(@NonNull BaseException ex) {
        log.error("Business exception occurred: {}", ex.getMessage());
        return Utils.returnResponse(ex.getStatus(), ex.getMessage(), null);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<WrapperApiResponse> handleAccessDeniedException(@NonNull AccessDeniedException ex) {
        log.error("Access denied: {}", ex.getMessage());
        return Utils.returnForbiddenResponse("Bạn không có quyền thực hiện hành động này", ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<WrapperApiResponse> handleEntityNotFoundException(@NonNull EntityNotFoundException ex) {
        log.error("Entity not found: {}", ex.getMessage());
        return Utils.returnNotFoundResponse(ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WrapperApiResponse> handleGenericException(@NonNull Exception ex) {
        log.error("Unhandled exception occurred: {}", ex.getMessage(), ex);
        return Utils.returnInternalServerErrorResponse("Đã xảy ra lỗi hệ thống. Vui lòng thử lại sau.", ex.getMessage());
    }
}
