package com.common.utilities.security;

import com.common.utilities.Utils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Bộ kiểm tra hợp lệ dữ liệu chống tấn công XSS (Cross-Site Scripting) thực tế.<br/>
 * Sử dụng tiện ích {@link Utils#containsXss(String)} để thực hiện kiểm tra so khớp.<br/>
 * Created at 19/06/2026
 *
 * @author txhoan
 */
public class XssSafeValidator implements ConstraintValidator<XssSafe, String> {

    @Override
    public void initialize(XssSafe constraintAnnotation) {
        // Không cần khởi tạo gì thêm
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            // Cho phép chuỗi rỗng/null, việc check NotNull/NotBlank do annotation khác đảm nhiệm
            return true;
        }
        // Trả về true nếu chuỗi an toàn (không chứa mã Script/HTML độc hại)
        return !Utils.containsXss(value);
    }
}
