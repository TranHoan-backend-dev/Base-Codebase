package com.common.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Bộ lọc ghi lại nhật ký chi tiết về các yêu cầu API nhận vào và phản hồi ra (Auditing).<br/>
 * Đo lường thời gian xử lý (latency) và ghi lại IP, URL, Status Code, và Request Body (nếu được cached).<br/>
 * Created at 19/06/2026
 *
 * @see <a href="../../../../resources/docs/security_filters/security-filter-guide.md">Security and Web Filter Guide</a>
 * @author txhoan
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10) // Chạy ngay sau XssSqlFilter để nhận HttpServletRequest đã được cache body
public class RequestResponseLoggingFilter implements Filter {

    /**
     * Chặn các HTTP Request và Response để thực hiện đo lường thời gian phản hồi và ghi nhật ký kiểm toán.<br/>
     * Created at 19/06/2026
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Chỉ xử lý các yêu cầu qua giao thức HTTP
        if (!(request instanceof HttpServletRequest httpServletRequest) || !(response instanceof HttpServletResponse httpServletResponse)) {
            chain.doFilter(request, response);
            return;
        }

        // 1. Ghi nhận thời điểm bắt đầu xử lý request
        var startTime = System.currentTimeMillis();

        // 2. Chuyển tiếp request đến các Filter tiếp theo hoặc Controller xử lý nghiệp vụ
        chain.doFilter(request, response);

        // 3. Tính toán tổng thời gian xử lý yêu cầu (độ trễ - Latency)
        var duration = System.currentTimeMillis() - startTime;

        // Thu thập thông tin kiểm toán của Request
        var method = httpServletRequest.getMethod();
        var uri = httpServletRequest.getRequestURI();
        var queryString = httpServletRequest.getQueryString();
        var clientIp = httpServletRequest.getRemoteAddr();
        var status = httpServletResponse.getStatus();

        // 4. Nếu request được wrap bởi CachedBodyHttpServletRequest (do XssSqlFilter thực hiện trước đó)
        var body = "";
        if (request instanceof CachedBodyHttpServletRequest cachedRequest) {
            body = cachedRequest.getBody();
            if (body != null) {
                // Rút gọn khoảng trắng và xuống dòng để ghi nhật ký gọn trên 1 dòng duy nhất
                body = body.replaceAll("\\s+", " ");
                // Tránh ghi log quá dài làm đầy ổ đĩa (giới hạn 500 ký tự)
                if (body.length() > 500) {
                    body = body.substring(0, 500) + "... [truncated]";
                }
            }
        }

        // 5. Ghi nhận thông tin kiểm toán chi tiết vào hệ thống Log
        log.info("API Audit: IP={}, Method={}, URI={}, Query={}, Status={}, Latency={}ms, Body={}",
                clientIp, method, uri, queryString != null ? queryString : "", status, duration, body);
    }
}
