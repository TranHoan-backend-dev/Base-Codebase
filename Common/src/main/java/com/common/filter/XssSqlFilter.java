package com.common.filter;

import com.common.dto.response.WrapperApiResponse;
import com.common.service.MessageService;
import com.common.utilities.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import java.time.OffsetDateTime;

/**
 * Bộ lọc an ninh ngăn chặn các cuộc tấn công SQL Injection và XSS (Cross-Site Scripting).<br/>
 * Chạy ở thứ tự ưu tiên cao nhất, quét toàn bộ Parameters, Query String, và Request Body JSON.
 * Created at 10/06/2026
 *
 * @author txhoan
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class XssSqlFilter implements Filter {

    private final ObjectMapper objectMapper;

    public XssSqlFilter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (!(request instanceof HttpServletRequest httpServletRequest) || !(response instanceof HttpServletResponse httpServletResponse)) {
            chain.doFilter(request, response);
            return;
        }

        // 1. Kiểm tra Query String
        var queryString = httpServletRequest.getQueryString();
        if (queryString != null && (Utils.containsSqlInjection(queryString) || Utils.containsXss(queryString))) {
            log.warn("Phát hiện SQL Injection hoặc XSS trong Query String: {}", queryString);
            sendBadRequest(httpServletResponse, MessageService.getMessage("security.unsafe_url"));
            return;
        }

        // 2. Kiểm tra Parameters (Query Params và Form Data)
        var parameterMap = httpServletRequest.getParameterMap();
        for (var entry : parameterMap.entrySet()) {
            for (var val : entry.getValue()) {
                if (Utils.containsSqlInjection(val) || Utils.containsXss(val)) {
                    log.warn("Phát hiện SQL Injection hoặc XSS trong tham số {}: {}", entry.getKey(), val);
                    sendBadRequest(httpServletResponse, MessageService.getMessage("security.unsafe_parameter", entry.getKey()));
                    return;
                }
            }
        }

        // 3. Kiểm tra Request Body (Chỉ wrap và đọc đối với JSON Request)
        String contentType = httpServletRequest.getContentType();
        boolean isJsonRequest = contentType != null && contentType.toLowerCase().contains("application/json");

        if (isJsonRequest) {
            try {
                var cachedRequest = new CachedBodyHttpServletRequest(httpServletRequest);
                var body = cachedRequest.getBody();
                
                if (Utils.containsSqlInjection(body) || Utils.containsXss(body)) {
                    log.warn("Phát hiện SQL Injection hoặc XSS trong Request Body: {}", body);
                    sendBadRequest(httpServletResponse, MessageService.getMessage("security.unsafe_body"));
                    return;
                }
                
                // Tiếp tục chain với request đã được wrap để có thể đọc lại body ở Controller
                chain.doFilter(cachedRequest, response);
            } catch (Exception e) {
                log.error("Lỗi khi đọc hoặc xử lý Request Body trong XssSqlFilter", e);
                sendBadRequest(httpServletResponse, MessageService.getMessage("security.invalid_request"));
            }
        } else {
            // Không phải JSON, tiếp tục chain bình thường
            chain.doFilter(request, response);
        }
    }

    private void sendBadRequest(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        var apiResponse = new WrapperApiResponse(
                HttpServletResponse.SC_BAD_REQUEST,
                message,
                null,
                OffsetDateTime.now()
        );

        var json = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(json);
    }
}
