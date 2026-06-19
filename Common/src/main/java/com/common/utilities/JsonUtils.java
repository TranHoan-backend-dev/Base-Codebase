package com.common.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

/**
 * Lớp tiện ích tĩnh giúp serialization và deserialization đối tượng JSON.<br/>
 * Sử dụng ObjectMapper được cấu hình sẵn múi giờ và định dạng Java 8 Date Time.<br/>
 * Created at 19/06/2026
 *
 * @see <a href="../../../../resources/docs/utilities/utilities-guide.md">Utilities Specification Guide</a>
 * @author txhoan
 */
public class JsonUtils {
    
    // Cấu hình ObjectMapper duy nhất dùng chung (Thread-safe)
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule()) // Hỗ trợ định dạng thời gian Java 8 (LocalDateTime, etc.)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // Bỏ qua thuộc tính lạ khi decode

    /**
     * Chuyển đổi một đối tượng Java bất kỳ sang chuỗi JSON String.<br/>
     * Created at 19/06/2026
     *
     * @param obj Đối tượng cần chuyển đổi
     * @return Chuỗi JSON String hoặc null nếu đối tượng truyền vào là null
     * @throws IllegalArgumentException nếu xảy ra lỗi chuyển đổi JSON
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            // Thực hiện tuần tự hóa đối tượng
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Lỗi chuyển đổi đối tượng sang chuỗi JSON", e);
        }
    }

    /**
     * Phân tích cú pháp chuỗi JSON String ngược lại thành đối tượng Java thuộc Class chỉ định.<br/>
     * Created at 19/06/2026
     *
     * @param json  Chuỗi JSON String đầu vào
     * @param clazz Kiểu lớp Java đích muốn ánh xạ sang
     * @param <T>   Kiểu Generic tương ứng
     * @return Đối tượng Java sau khi được giải nén hoặc null nếu chuỗi JSON đầu vào là null/rỗng
     * @throws IllegalArgumentException nếu xảy ra lỗi phân tích cú pháp JSON
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || json.isBlank()) {
            return null;
        }
        try {
            // Thực hiện giải tuần tự hóa chuỗi JSON
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Lỗi phân tích cú pháp chuỗi JSON sang đối tượng", e);
        }
    }
}
