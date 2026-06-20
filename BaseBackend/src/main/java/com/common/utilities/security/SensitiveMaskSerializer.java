package com.common.utilities.security;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Bộ tuần tự hóa Jackson tùy chỉnh dùng để che giấu dữ liệu nhạy cảm.<br/>
 * Triển khai {@link ContextualSerializer} giúp lấy thông số từ annotation {@link SensitiveMask} động.<br/>
 * Created at 19/06/2026
 *
 * @see <a href="../../../../../resources/docs/security_filters/security-filter-guide.md">Security and Web Filter Guide</a>
 * @see "Common/src/main/resources/docs/security_filters/security-filter-guide.md"
 * @author txhoan
 */
public class SensitiveMaskSerializer extends StdSerializer<String> implements ContextualSerializer {
    
    private MaskType maskType;

    public SensitiveMaskSerializer() {
        super(String.class);
    }

    public SensitiveMaskSerializer(MaskType maskType) {
        super(String.class);
        this.maskType = maskType;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        if (maskType == null) {
            gen.writeString(value);
            return;
        }

        // Thực hiện che giấu dữ liệu tùy thuộc vào kiểu chỉ định
        switch (maskType) {
            case EMAIL -> gen.writeString(maskEmail(value));
            case PHONE -> gen.writeString(maskPhone(value));
            case PASSWORD -> gen.writeString("******");
            case CREDIT_CARD -> gen.writeString(maskCreditCard(value));
            case ID_CARD -> gen.writeString(maskIdCard(value));
            default -> gen.writeString(value);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        if (property != null) {
            SensitiveMask ann = property.getAnnotation(SensitiveMask.class);
            if (ann != null) {
                // Trả về một đối tượng serializer mới được cấu hình loại che dữ liệu tương ứng
                return new SensitiveMaskSerializer(ann.value());
            }
        }
        return this;
    }

    private String maskEmail(String email) {
        if (email.contains("@")) {
            String[] parts = email.split("@");
            if (parts[0].length() > 3) {
                return parts[0].substring(0, 3) + "****@" + parts[1];
            }
            return parts[0].charAt(0) + "****@" + parts[1];
        }
        return "****";
    }

    private String maskPhone(String phone) {
        if (phone.length() >= 7) {
            return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 3);
        }
        return "****";
    }

    private String maskCreditCard(String card) {
        if (card.length() >= 4) {
            return "****-****-****-" + card.substring(card.length() - 4);
        }
        return "****-****-****-****";
    }

    private String maskIdCard(String idCard) {
        if (idCard.length() >= 6) {
            return idCard.substring(0, 3) + "******" + idCard.substring(idCard.length() - 3);
        }
        return "******";
    }
}
