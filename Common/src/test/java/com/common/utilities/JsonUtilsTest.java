package com.common.utilities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Kiểm thử lớp tiện ích JsonUtils.
 *
 * @author txhoan
 */
class JsonUtilsTest {

    record SampleObject(String name, int value) {}

    @Test
    void testToJsonAndFromJson() {
        var obj = new SampleObject("Antigravity", 42);
        var json = JsonUtils.toJson(obj);
        assertNotNull(json);
        assertTrue(json.contains("Antigravity"));
        assertTrue(json.contains("42"));

        var decoded = JsonUtils.fromJson(json, SampleObject.class);
        assertNotNull(decoded);
        assertEquals("Antigravity", decoded.name());
        assertEquals(42, decoded.value());
    }

    @Test
    void testFromJson_WithUnknownProperties() {
        // Đảm bảo không bị throw exception khi gặp trường lạ (FAIL_ON_UNKNOWN_PROPERTIES = false)
        var json = "{\"name\":\"Test\",\"value\":10,\"extraField\":\"IgnoredValue\"}";
        var decoded = JsonUtils.fromJson(json, SampleObject.class);
        assertNotNull(decoded);
        assertEquals("Test", decoded.name());
        assertEquals(10, decoded.value());
    }

    @Test
    void testToJson_WithNull() {
        assertNull(JsonUtils.toJson(null));
        assertNull(JsonUtils.fromJson(null, SampleObject.class));
        assertNull(JsonUtils.fromJson("  ", SampleObject.class));
    }
}
