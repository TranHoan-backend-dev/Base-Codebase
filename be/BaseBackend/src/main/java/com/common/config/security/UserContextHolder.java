package com.common.config.security;

/**
 * Lớp tiện ích bọc ThreadLocal để lưu trữ và truy cập thông tin người dùng hiện tại ở mọi tầng nghiệp vụ.<br/>
 * Tránh việc phải phụ thuộc trực tiếp vào Spring Security SecurityContextHolder.<br/>
 * Created at 19/06/2026
 *
 * @see <a href="../../../../../resources/docs/security_filters/security-filter-guide.md">Security and Web Filter Guide</a>
 * @author txhoan
 */
public class UserContextHolder {
    
    private static final ThreadLocal<UserContext> CONTEXT = new ThreadLocal<>();

    /**
     * Ghi nhận đối tượng UserContext vào ThreadLocal của thread hiện tại.<br/>
     * Created at 19/06/2026
     *
     * @param context Thông tin người dùng cần lưu trữ
     */
    public static void set(UserContext context) {
        CONTEXT.set(context);
    }

    /**
     * Lấy thông tin người dùng hiện tại từ ThreadLocal.<br/>
     * Created at 19/06/2026
     *
     * @return Đối tượng UserContext của thread hiện tại
     */
    public static UserContext get() {
        return CONTEXT.get();
    }

    /**
     * Giải phóng tài nguyên ThreadLocal (dọn dẹp context).<br/>
     * Rất quan trọng để tránh memory leak khi tái sử dụng thread trong ThreadPool.<br/>
     * Created at 19/06/2026
     */
    public static void clear() {
        CONTEXT.remove();
    }
}
