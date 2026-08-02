package com.common.utilities;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Lớp xử lý logic kiểm tra chuỗi an toàn cho annotation @SafeString.
 * Created at 19/6/2026
 *
 * @see <a href="../../../../resources/docs/security_filters/security-filter-guide.md">Security and Web Filter Guide</a>
 * @see "Common/src/main/resources/docs/security_filters/security-filter-guide.md"
 * @author txhoan
 */
public class SafeStringValidator implements ConstraintValidator<SafeString, String> {

    @Override
    public void initialize(SafeString constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Thực hiện kiểm tra tính hợp lệ của chuỗi đầu vào.
     * <p>
     * Created at 07/06/2026
     *
     * @param value   giá trị chuỗi cần kiểm tra
     * @param context ngữ cảnh kiểm tra của bộ validate
     * @return true nếu chuỗi an toàn, ngược lại false
     * @author txhoan
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // Cho phép null hoặc trống. Muốn bắt buộc hãy dùng kèm với @NotNull hoặc @NotBlank.
        }
        return Utils.isSafeString(value);
    }
}
