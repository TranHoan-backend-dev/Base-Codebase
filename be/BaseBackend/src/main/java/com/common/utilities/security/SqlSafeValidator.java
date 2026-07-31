package com.common.utilities.security;

import com.common.utilities.Utils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Bộ kiểm tra hợp lệ dữ liệu chống tấn công SQL Injection thực tế.<br/>
 * Sử dụng tiện ích {@link Utils#containsSqlInjection(String)} để thực hiện kiểm tra so khớp.<br/>
 * Created at 19/06/2026
 *
 * @see <a href="../../../../../resources/docs/security_filters/security-filter-guide.md">Security and Web Filter Guide</a>
 * @see "Common/src/main/resources/docs/security_filters/security-filter-guide.md"
 * @author txhoan
 */
public class SqlSafeValidator implements ConstraintValidator<SqlSafe, String> {

    @Override
    public void initialize(SqlSafe constraintAnnotation) {
        // Không cần khởi tạo gì thêm
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            // Cho phép chuỗi rỗng/null, việc check NotNull/NotBlank do annotation khác đảm nhiệm
            return true;
        }
        // Trả về true nếu chuỗi an toàn (không chứa ký tự SQL Injection nguy hiểm)
        return !Utils.containsSqlInjection(value);
    }
}
