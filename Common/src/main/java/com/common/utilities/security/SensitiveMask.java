package com.common.utilities.security;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation dùng để đánh dấu một trường thông tin nhạy cảm cần được che giấu dữ liệu.<br/>
 * Tích hợp trực tiếp bộ tuần tự hóa tùy chỉnh {@link SensitiveMaskSerializer}
 * để tự động áp dụng che giấu khi đối tượng được chuyển sang chuỗi JSON (Jackson Serialization).<br/>
 * Created at 19/06/2026
 *
 * @see <a href="../../../../../resources/docs/security_filters/security-filter-guide.md">Security and Web Filter Guide</a>
 * @see "Common/src/main/resources/docs/security_filters/security-filter-guide.md"
 * @author txhoan
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveMaskSerializer.class)
public @interface SensitiveMask {
    
    /**
     * Loại che giấu thông tin tương ứng.
     *
     * @return kiểu MaskType chỉ định
     */
    MaskType value();
}
