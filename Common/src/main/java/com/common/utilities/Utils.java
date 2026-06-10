package com.common.utilities;

import com.common.dto.response.WrapperApiResponse;
import com.common.exception.InternalServerException;
import com.common.service.MessageService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Lớp tiện ích cung cấp các phương thức validate định dạng dữ liệu,
 * xử lý file và hỗ trợ chuẩn hóa phản hồi API.
 * Ngoài ra, lớp này còn tích hợp các giải pháp phát hiện chuỗi nguy hại như SQL Injection và XSS.
 *
 * @author txhoan
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Utils {

    static String[] SQL_INJECTION_REGEXES = {
            "\\b(SELECT|INSERT|UPDATE|DELETE|DROP|UNION|TRUNCATE|ALTER)\\b[\\s\\S]*?\\b(FROM|INTO|SET|TABLE|DATABASE|INDEX|VIEW|COLUMN|SELECT)\\b",
            "--",
            "/\\*",
            "\\*/",
            "xp_cmdshell",
            "information_schema",
            "sysdatabases",
            "sysobjects",
            "\\bOR\\s+\\d+\\s*=\\s*\\d+\\b",
            "\\bAND\\s+\\d+\\s*=\\s*\\d+\\b",
            ";\\s*\\b(SELECT|INSERT|UPDATE|DELETE|DROP|TRUNCATE|ALTER|DECLARE|EXEC|EXECUTE)\\b"
    };

    static String[] XSS_REGEXES = {
            "<script.*?>",
            "</script>",
            "javascript:",
            "expression\\(",
            "onload\\s*=",
            "onerror\\s*=",
            "onclick\\s*=",
            "onmouseover\\s*=",
            "onfocus\\s*=",
            "\\bon[a-z]+\\s*=",
            "<iframe",
            "<object",
            "<embed",
            "<applet",
            "<meta",
            "<link",
            "<style"
    };

    static String[] HTML_TAG_REGEXES = {
            "<[^>]*>"
    };

    static Pattern SQL_INJECTION_PATTERN = Pattern.compile(
            "(?i)(" + String.join("|", SQL_INJECTION_REGEXES) + ")"
    );

    static Pattern XSS_PATTERN = Pattern.compile(
            "(?i)(" + String.join("|", XSS_REGEXES) + ")"
    );

    static Pattern HTML_TAG_PATTERN = Pattern.compile(
            "(?i)(" + String.join("|", HTML_TAG_REGEXES) + ")"
    );

    static String[] DANGEROUS_XSS_KEYWORDS = {
            "script", "javascript", "onload", "onerror", "onclick", "onmouseover", "onmouseout",
            "eval", "expression", "vbscript", "applet", "framesetm", "meta", "link",
            "onfocus", "onblur", "onchange", "onsubmit", "alert", "document\\.cookie",
            "window\\.location", "object", "embed", "iframe", "frame", "frameset",
            "style", "base", "form", "input", "button", "svg", "video",
            "track", "textarea", "select", "img", "audio", "source"
    };

    static Pattern DANGEROUS_XSS_KEYWORDS_PATTERN = Pattern.compile(
            "(?i)(" + String.join("|", DANGEROUS_XSS_KEYWORDS) + ")"
    );

    static String[] DANGEROUS_REGEXES = {
            "<\\s*script[^>]*>.*?</\\s*script\\s*>",
            "<\\s*iframe[^>]*>.*?</\\s*iframe\\s*>",
            "<\\s*embed[^>]*>",
            "<\\s*applet[^>]*>.*?</\\s*applet\\s*>",
            "javascript\\s*:",
            "vbscript\\s*:",
            "data\\s*:",
            "on\\w+\\s*=",
            "expression\\s*\\(",
            "url\\s*\\(",
            "@import ",
            "<\\s*meta[^>]*>",
            "<\\s*link[^>]*>",
            "<\\s*style[^>]*>.*?</\\s*style\\s*>"
    };

    static Pattern DANGEROUS_PATTERNS = Pattern.compile(
            "(?is)(" + String.join("|", DANGEROUS_REGEXES) + ")"
    );

    /**
     * Kiểm tra xem chuỗi đầu vào có chứa các mẫu SQL Injection nguy hiểm hay không.
     * Created at 07/06/2026
     *
     * @param value chuỗi cần kiểm tra
     * @return true nếu phát hiện SQL Injection, ngược lại false
     * @author txhoan
     */
    public static boolean containsSqlInjection(String value) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return SQL_INJECTION_PATTERN.matcher(value).find();
    }

    /**
     * Kiểm tra xem chuỗi đầu vào có chứa các kịch bản XSS hoặc thẻ script nguy hiểm hay không.
     * <p>
     * Created at 07/06/2026
     *
     * @param value chuỗi cần kiểm tra
     * @return true nếu phát hiện XSS, ngược lại false
     * @author txhoan
     */
    public static boolean containsXss(String value) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return XSS_PATTERN.matcher(value).find();
    }

    /**
     * Kiểm tra xem chuỗi đầu vào có chứa bất kỳ thẻ HTML nào hay không.
     * <p>
     * Created at 07/06/2026
     *
     * @param value chuỗi cần kiểm tra
     * @return true nếu phát hiện thẻ HTML, ngược lại false
     * @author txhoan
     */
    public static boolean containsHtml(String value) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return HTML_TAG_PATTERN.matcher(value).find();
    }

    /**
     * Kiểm tra xem chuỗi đầu vào có an toàn hay không (không chứa SQL Injection và XSS).
     * <p>
     * Created at 07/06/2026
     *
     * @param value chuỗi cần kiểm tra
     * @return true nếu an toàn, ngược lại false
     * @author txhoan
     */
    public static boolean isSafeString(String value) {
        return !containsSqlInjection(value) && !containsXss(value);
    }

    /**
     * Thực hiện validate chuỗi đầu vào. Nếu phát hiện chuỗi nguy hiểm sẽ ném ra IllegalArgumentException.
     * Lỗi này sẽ được bắt bởi GlobalExceptionHandler và trả về lỗi BAD_REQUEST (400) cho client.
     * <p>
     * Created at 07/06/2026
     *
     * @param value     chuỗi cần kiểm tra
     * @param fieldName tên trường kiểm tra để đưa vào thông báo lỗi
     * @throws IllegalArgumentException nếu phát hiện chuỗi không an toàn
     * @author txhoan
     */
    public static void validateSafeInput(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            return;
        }
        if (containsSqlInjection(value)) {
            throw new IllegalArgumentException(MessageService.getMessage("validation.sql_injection", fieldName));
        }
        if (containsXss(value)) {
            throw new IllegalArgumentException(MessageService.getMessage("validation.xss", fieldName));
        }
    }

    /**
     * Làm sạch chuỗi đầu vào để ngăn chặn tấn công XSS (Cross-Site Scripting).<br/>
     * Phương thức này thực hiện mã hóa toàn bộ HTML (HTML escaping) đối với các ký tự đặc biệt nguy hiểm.
     * Đây là biện pháp cuối cùng để cho phép dữ liệu HTML đi vào và lưu trữ trong Cơ sở dữ liệu
     * nhưng đảm bảo trình duyệt web sẽ hiển thị nó dưới dạng văn bản thuần túy và không thể thực thi được.
     * <p>
     * Created at 08/06/2026
     *
     * @param value chuỗi cần làm sạch/mã hóa HTML
     * @return chuỗi đã được làm sạch an toàn hoặc null nếu giá trị truyền vào là null
     * @author txhoan
     */
    public static String sanitizeUserInput(String value) {
        if (value == null) {
            return null;
        }
        return HtmlUtils.htmlEscape(value);
    }

    /**
     * Loại bỏ tất cả các thẻ HTML khỏi chuỗi đầu vào.
     * <p>
     * Created at 08/06/2026
     *
     * @param value chuỗi cần loại bỏ thẻ HTML
     * @return chuỗi đã loại bỏ hết các thẻ HTML hoặc null nếu giá trị truyền vào là null
     * @author txhoan
     */
    public static String stripHtmlTags(String value) {
        if (value == null || value.isBlank()) {
            return value;
        }
        return HTML_TAG_PATTERN.matcher(value).replaceAll("");
    }

    /**
     * Loại bỏ các từ khóa và thẻ nguy hiểm có nguy cơ gây tấn công XSS khỏi chuỗi đầu vào.
     * <p>
     * Created at 08/06/2026
     *
     * @param value chuỗi cần loại bỏ từ khóa nguy hiểm
     * @return chuỗi đã loại bỏ hết các từ khóa nguy hiểm hoặc null nếu giá trị truyền vào là null
     * @author txhoan
     */
    public static String stripDangerousXssKeywords(String value) {
        if (value == null || value.isBlank()) {
            return value;
        }
        return DANGEROUS_XSS_KEYWORDS_PATTERN.matcher(value).replaceAll("");
    }

    /**
     * Loại bỏ các đoạn mã và thuộc tính nguy hiểm dựa trên các mẫu regex được xác định trước.<br/>
     * Ngăn những kẻ tấn công sử dụng kỹ thuật biến tấu để bypass
     * <p>
     * Created at 08/06/2026
     *
     * @param value chuỗi cần loại bỏ các mẫu nguy hiểm
     * @return chuỗi đã loại bỏ hết các mẫu nguy hiểm hoặc null nếu giá trị truyền vào là null
     * @author txhoan
     */
    public static String stripDangerousPatterns(String value) {
        if (value == null || value.isBlank()) {
            return value;
        }
        return DANGEROUS_PATTERNS.matcher(value).replaceAll("");
    }

    /**
     * Kiểm tra xem chuỗi đầu vào có đúng định dạng LocalDate mong muốn hay không.
     *
     * @param value     chuỗi ngày cần kiểm tra
     * @param formatter đối tượng định dạng ngày tháng mong muốn
     * @return true nếu khớp định dạng và là ngày hợp lệ, ngược lại false
     */
    public static boolean isLocalDate(String value, DateTimeFormatter formatter) {
        try {
            LocalDate.parse(value, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Kiểm tra xem một chuỗi có tuân thủ định dạng UUID hay không.
     *
     * @param value chuỗi cần kiểm tra
     * @return true nếu đúng định dạng UUID, ngược lại false
     */
    public static boolean isUUID(String value) {
        if (value == null) return false;
        try {
            UUID.fromString(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Chuyển đổi chuỗi ngày bắt đầu sang LocalDateTime vào lúc bắt đầu ngày (00:00:00).
     *
     * @param from chuỗi ngày ở định dạng ISO_LOCAL_DATE (yyyy-MM-dd)
     * @return LocalDateTime lúc bắt đầu ngày hoặc null nếu tham số truyền vào là null
     */
    public static LocalDateTime parseFrom(String from) {
        LocalDateTime startDate = null;

        if (from != null) {
            startDate = LocalDate.parse(from, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
        }
        return startDate;
    }

    /**
     * Chuyển đổi chuỗi ngày kết thúc sang LocalDateTime vào thời điểm cuối ngày (23:59:59.999999999).
     *
     * @param to chuỗi ngày ở định dạng ISO_LOCAL_DATE (yyyy-MM-dd)
     * @return LocalDateTime lúc cuối ngày hoặc null nếu tham số truyền vào là null
     */
    public static LocalDateTime parseTo(String to) {
        LocalDateTime endDate = null;

        if (to != null) {
            endDate = LocalDate.parse(to, DateTimeFormatter.ISO_LOCAL_DATE).atTime(LocalTime.MAX);
        }
        return endDate;
    }

    /**
     * Lưu trữ tập tin tải lên vào thư mục lưu trữ cục bộ. Thư mục mặc định là uploads/images.
     *
     * @param file đối tượng tập tin MultipartFile tải lên từ request
     * @return tên của tập tin đã được lưu trữ (bao gồm tiền tố timestamp để tránh trùng lặp)
     * @throws InternalServerException nếu xảy ra lỗi vào/ra (IOException) khi lưu tập tin
     */
    public static @NonNull String saveFile(@NonNull MultipartFile file) {
        // Dùng absolute path thay vì relative để tránh sự khác biệt giữa
        // Windows (working dir = apps/) và Linux (working dir = apps/device/)
        var uploadDir = Paths.get(System.getProperty("user.dir"), "uploads", "images");
        var fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        var filePath = uploadDir.resolve(fileName);

        try {
            Files.createDirectories(uploadDir);
            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            throw new InternalServerException();
        }

        return fileName;
    }

    /**
     * Xây dựng ResponseEntity chứa dữ liệu phản hồi thành công (HttpStatus.OK - 200).
     *
     * @param message thông báo thành công
     * @param data    dữ liệu trả về kèm theo
     * @return đối tượng ResponseEntity chứa cấu trúc WrapperApiResponse
     */
    public static @NonNull ResponseEntity<WrapperApiResponse> returnOkResponse(String message, Object data) {
        return buildResponse(HttpStatus.OK.value(), message, data);
    }

    /**
     * Xây dựng ResponseEntity chứa dữ liệu phản hồi khi tài nguyên được tạo thành công (HttpStatus.CREATED - 201).
     *
     * @param message thông báo tạo thành công
     * @return đối tượng ResponseEntity chứa cấu trúc WrapperApiResponse
     */
    public static @NonNull ResponseEntity<WrapperApiResponse> returnCreatedResponse(String message) {
        return buildResponse(HttpStatus.CREATED.value(), message, null);
    }

    /**
     * Xây dựng ResponseEntity phản hồi yêu cầu không hợp lệ (HttpStatus.BAD_REQUEST - 400).
     *
     * @param message thông báo lỗi chi tiết
     * @param data    thông tin lỗi bổ sung hoặc null
     * @return đối tượng ResponseEntity chứa cấu trúc WrapperApiResponse
     */
    public static @NonNull ResponseEntity<WrapperApiResponse> returnBadRequestResponse(String message, Object data) {
        return buildResponse(HttpStatus.BAD_REQUEST.value(), message, data);
    }

    /**
     * Xây dựng ResponseEntity phản hồi lỗi hệ thống từ phía server (HttpStatus.INTERNAL_SERVER_ERROR - 500).
     *
     * @param message thông báo lỗi hệ thống
     * @param data    dữ liệu lỗi chi tiết hoặc null
     * @return đối tượng ResponseEntity chứa cấu trúc WrapperApiResponse
     */
    public static @NonNull ResponseEntity<WrapperApiResponse> returnInternalServerErrorResponse(String message, Object data) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, data);
    }

    /**
     * Xây dựng ResponseEntity phản hồi lỗi xác thực người dùng (HttpStatus.UNAUTHORIZED - 401).
     *
     * @param message thông báo lỗi xác thực
     * @param data    chi tiết nguyên nhân lỗi hoặc null
     * @return đối tượng ResponseEntity chứa cấu trúc WrapperApiResponse
     */
    public static @NonNull ResponseEntity<WrapperApiResponse> returnUnAuthorizedResponse(String message, Object data) {
        return buildResponse(HttpStatus.UNAUTHORIZED.value(), message, data);
    }

    /**
     * Xây dựng ResponseEntity phản hồi lỗi từ chối quyền truy cập (HttpStatus.FORBIDDEN - 403).
     *
     * @param message thông báo từ chối truy cập
     * @param data    chi tiết bổ sung hoặc null
     * @return đối tượng ResponseEntity chứa cấu trúc WrapperApiResponse
     */
    public static @NonNull ResponseEntity<WrapperApiResponse> returnForbiddenResponse(String message, Object data) {
        return buildResponse(HttpStatus.FORBIDDEN.value(), message, data);
    }

    /**
     * Xây dựng ResponseEntity phản hồi xung đột dữ liệu (HttpStatus.CONFLICT - 409).
     *
     * @param message thông báo về sự xung đột dữ liệu
     * @param data    chi tiết dữ liệu bị xung đột hoặc null
     * @return đối tượng ResponseEntity chứa cấu trúc WrapperApiResponse
     */
    public static @NonNull ResponseEntity<WrapperApiResponse> returnConflictResponse(String message, Object data) {
        return buildResponse(HttpStatus.CONFLICT.value(), message, data);
    }

    /**
     * Xây dựng ResponseEntity phản hồi không có nội dung trả về (HttpStatus.NO_CONTENT - 204).
     *
     * @param message thông báo kèm theo
     * @return đối tượng ResponseEntity chứa cấu trúc WrapperApiResponse
     */
    public static @NonNull ResponseEntity<WrapperApiResponse> returnNoContentResponse(String message) {
        return buildResponse(HttpStatus.NO_CONTENT.value(), message, null);
    }

    /**
     * Phương thức nội bộ dùng để đóng gói dữ liệu phản hồi theo khuôn mẫu WrapperApiResponse.
     *
     * @param statusCode mã trạng thái HTTP
     * @param message    thông báo phản hồi
     * @param data       dữ liệu gửi về client
     * @return đối tượng ResponseEntity chứa WrapperApiResponse cấu trúc đồng nhất
     */
    private static @NonNull ResponseEntity<WrapperApiResponse> buildResponse(int statusCode, String message, Object data) {
        return ResponseEntity.status(statusCode).body(new WrapperApiResponse(
                statusCode,
                message,
                data,
                OffsetDateTime.now()
        ));
    }
}
