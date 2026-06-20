package com.common.utilities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Lớp kiểm thử đơn vị cho các phương thức xác thực và tiện ích trong lớp Utils.
 *
 * @author txhoan
 */
class UtilsTest {

    @Test
    void testIsSafeString_WithSafeStrings() {
        assertTrue(Utils.isSafeString("Hello World"));
        assertTrue(Utils.isSafeString("This is a simple sentence."));
        assertTrue(Utils.isSafeString("email@example.com"));
        assertTrue(Utils.isSafeString("phone_number_123"));
        assertTrue(Utils.isSafeString(null));
        assertTrue(Utils.isSafeString(""));
        assertTrue(Utils.isSafeString("   "));
    }

    @Test
    void testIsSafeString_WithSqlInjection() {
        // Tautology
        assertFalse(Utils.isSafeString("1 OR 1=1"));
        assertFalse(Utils.isSafeString("1 AND 1=1"));
        
        // Comment signs
        assertFalse(Utils.isSafeString("admin'--"));
        assertFalse(Utils.isSafeString("select * /* comment */"));
        
        // SQL keywords structure
        assertFalse(Utils.isSafeString("SELECT * FROM users"));
        assertFalse(Utils.isSafeString("INSERT INTO logs VALUES (1)"));
        assertFalse(Utils.isSafeString("DELETE FROM users WHERE id = 1"));
        assertFalse(Utils.isSafeString("DROP TABLE users"));
        assertFalse(Utils.isSafeString("UNION SELECT username, password FROM users"));
    }

    @Test
    void testIsSafeString_WithXss() {
        // Script tags
        assertFalse(Utils.isSafeString("<script>alert(1)</script>"));
        assertFalse(Utils.isSafeString("</script><script>"));
        
        // Javascript URI
        assertFalse(Utils.isSafeString("javascript:alert(1)"));
        
        // Event handlers
        assertFalse(Utils.isSafeString("<img src=x onerror=alert(1)>"));
        assertFalse(Utils.isSafeString("onload=confirm(1)"));
        
        // Danger tags
        assertFalse(Utils.isSafeString("<iframe src='url'></iframe>"));
    }

    @Test
    void testContainsHtml() {
        assertTrue(Utils.containsHtml("<p>Hello</p>"));
        assertTrue(Utils.containsHtml("<br/>"));
        assertFalse(Utils.containsHtml("Hello World"));
    }

    @Test
    void testValidateSafeInput_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> 
            Utils.validateSafeInput("SELECT * FROM users", "sqlField")
        );
        
        assertThrows(IllegalArgumentException.class, () -> 
            Utils.validateSafeInput("<script>alert(1)</script>", "xssField")
        );
        
        // Should not throw exception for safe inputs
        assertDoesNotThrow(() -> 
            Utils.validateSafeInput("A safe string input", "safeField")
        );
    }

    @Test
    void testSafeStringValidator() {
        SafeStringValidator validator = new SafeStringValidator();
        assertTrue(validator.isValid("Safe Input", null));
        assertTrue(validator.isValid("", null));
        assertTrue(validator.isValid(null, null));
        assertFalse(validator.isValid("SELECT * FROM users", null));
    }

    @Test
    void testSanitizeUserInput() {
        assertNull(Utils.sanitizeUserInput(null));
        assertEquals("Hello World", Utils.sanitizeUserInput("Hello World"));
        assertEquals("&lt;script&gt;alert(1)&lt;/script&gt;", Utils.sanitizeUserInput("<script>alert(1)</script>"));
        assertEquals("hello &amp; world", Utils.sanitizeUserInput("hello & world"));
    }

    @Test
    void testStripHtmlTags() {
        assertNull(Utils.stripHtmlTags(null));
        assertEquals("Hello World", Utils.stripHtmlTags("Hello World"));
        assertEquals("alert(1)", Utils.stripHtmlTags("<script>alert(1)</script>"));
        assertEquals("Paragraph text", Utils.stripHtmlTags("<p>Paragraph text</p>"));
        assertEquals("Link text", Utils.stripHtmlTags("<a href='url'>Link text</a>"));
    }

    @Test
    void testStripDangerousXssKeywords() {
        assertNull(Utils.stripDangerousXssKeywords(null));
        assertEquals("Hello World", Utils.stripDangerousXssKeywords("Hello World"));
        assertEquals("<>(1)</>", Utils.stripDangerousXssKeywords("<script>alert(1)</script>"));
        assertEquals("=1", Utils.stripDangerousXssKeywords("onload=1"));
        assertEquals("my cookie: ", Utils.stripDangerousXssKeywords("my cookie: document.cookie"));
    }

    @Test
    void testStripDangerousPatterns() {
        assertNull(Utils.stripDangerousPatterns(null));
        assertEquals("Hello World", Utils.stripDangerousPatterns("Hello World"));
        assertEquals("Some text ", Utils.stripDangerousPatterns("Some text <script>alert(1)</script>"));
        assertEquals("Hello alert(1)", Utils.stripDangerousPatterns("Hello javascript:alert(1)"));
        assertEquals("Click alert(1) me", Utils.stripDangerousPatterns("Click onload=alert(1) me"));
        assertEquals("Link ", Utils.stripDangerousPatterns("Link <link rel='stylesheet'>"));
    }
}

