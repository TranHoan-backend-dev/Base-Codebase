package com.common.utilities;

import com.common.utilities.security.MaskType;
import com.common.utilities.security.SensitiveMask;
import com.common.utilities.security.SqlSafe;
import com.common.utilities.security.XssSafe;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.Builder;
import lombok.Getter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Kiểm thử cho các lớp và annotation bảo mật vừa bổ sung.<br/>
 * Created at 19/06/2026
 *
 * @author txhoan
 */
class SecurityUtilitiesTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        java.util.Locale.setDefault(new java.util.Locale("vi", "VN"));
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Getter
    @Builder
    static class SensitiveDataDto {
        @SensitiveMask(MaskType.EMAIL)
        private String email;

        @SensitiveMask(MaskType.PHONE)
        private String phone;

        @SensitiveMask(MaskType.PASSWORD)
        private String password;

        @SensitiveMask(MaskType.CREDIT_CARD)
        private String creditCard;

        @SensitiveMask(MaskType.ID_CARD)
        private String idCard;
    }

    @Getter
    @Builder
    static class InputValidationDto {
        @SqlSafe
        private String sqlSafeField;

        @XssSafe
        private String xssSafeField;
    }

    @Test
    void testSensitiveMasking() {
        var dto = SensitiveDataDto.builder()
                .email("txhoan.dev@gmail.com")
                .phone("0987654321")
                .password("supersecret123")
                .creditCard("1234567890123456")
                .idCard("001095123456")
                .build();

        var json = JsonUtils.toJson(dto);
        assertNotNull(json);

        // Kiểm định dữ liệu nhạy cảm đã bị che
        assertTrue(json.contains("txh****@gmail.com"), "Email phải được che: " + json);
        assertTrue(json.contains("098****321"), "Số điện thoại phải được che: " + json);
        assertTrue(json.contains("******"), "Mật khẩu phải được che: " + json);
        assertTrue(json.contains("****-****-****-3456"), "Thẻ tín dụng phải được che: " + json);
        assertTrue(json.contains("001******456"), "CMND/CCCD phải được che: " + json);
    }

    @Test
    void testInputValidation_WithSafeInputs() {
        var dto = InputValidationDto.builder()
                .sqlSafeField("Safe Input string")
                .xssSafeField("Another safe string")
                .build();

        Set<ConstraintViolation<InputValidationDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Các đầu vào an toàn không được có lỗi validation");
    }

    @Test
    void testInputValidation_WithUnsafeInputs() {
        // 1. Kiểm thử phát hiện SQL Injection
        var unsafeSqlDto = InputValidationDto.builder()
                .sqlSafeField("SELECT * FROM users")
                .xssSafeField("Safe string")
                .build();

        Set<ConstraintViolation<InputValidationDto>> sqlViolations = validator.validate(unsafeSqlDto);
        assertEquals(1, sqlViolations.size());
        assertEquals("Dữ liệu đầu vào chứa từ khóa hoặc ký tự SQL Injection nguy hiểm", sqlViolations.iterator().next().getMessage());

        // 2. Kiểm thử phát hiện XSS
        var unsafeXssDto = InputValidationDto.builder()
                .sqlSafeField("Safe string")
                .xssSafeField("<script>alert(1)</script>")
                .build();

        Set<ConstraintViolation<InputValidationDto>> xssViolations = validator.validate(unsafeXssDto);
        assertEquals(1, xssViolations.size());
        assertEquals("Dữ liệu đầu vào chứa các thẻ HTML hoặc mã kịch bản XSS không an toàn", xssViolations.iterator().next().getMessage());
    }
}
