package com.common.config.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Bộ lọc giải nén JWT claims từ SecurityContext của Keycloak và điền thông tin vào UserContextHolder ThreadLocal.<br/>
 * Giúp tăng tính bảo mật và thuận tiện cho việc truy xuất thông tin người dùng ở mọi tầng.<br/>
 * Created at 19/06/2026
 *
 * @see <a href="../../../../../resources/docs/security_filters/security-filter-guide.md">Security and Web Filter Guide</a>
 * @author txhoan
 */
@Component
public class UserContextFilter implements Filter {

    /**
     * Thực hiện chặn và giải nén JWT claims ghi nhận thông tin user vào ThreadLocal.<br/>
     * Created at 19/06/2026
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            // Lấy thông tin đối tượng xác thực hiện tại từ SecurityContext của Spring Security
            var auth = SecurityContextHolder.getContext().getAuthentication();
            
            // Nếu người dùng đã đăng nhập thành công và token là JWT
            if (auth instanceof JwtAuthenticationToken jwtAuth) {
                var jwt = jwtAuth.getToken();
                var userId = jwt.getSubject(); // Lấy mã ID người dùng (UUID từ Keycloak)
                
                // Giải nén tên tài khoản theo thứ tự ưu tiên các claim trong JWT
                var username = jwt.getClaimAsString("preferred_username");
                if (username == null) {
                    username = jwt.getClaimAsString("email");
                }
                if (username == null) {
                    username = jwt.getClaimAsString("user_name");
                }
                
                // Lấy ra danh sách quyền/vai trò của người dùng
                var roles = jwtAuth.getAuthorities().stream()
                        .map(org.springframework.security.core.GrantedAuthority::getAuthority)
                        .toList();
                
                // Điền thông tin vào ThreadLocal để chia sẻ cho thread xử lý request hiện tại
                UserContextHolder.set(new UserContext(userId, username, roles));
            }
            
            // Tiếp tục chuỗi bộ lọc Web Filter
            chain.doFilter(request, response);
        } finally {
            // Luôn luôn giải phóng ThreadLocal khi kết thúc vòng đời HTTP request
            // Tránh memory leak do tái sử dụng luồng (Thread) trong Tomcat ThreadPool
            UserContextHolder.clear();
        }
    }
}
