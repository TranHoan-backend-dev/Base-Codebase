package com.common.utilities.audit;

import com.common.config.security.UserContextHolder;
import com.common.utilities.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Aspect tự động xử lý và ghi nhận nhật ký kiểm toán hành động người dùng (Audit Log).<br/>
 * Chặn các phương thức được gán nhãn `@AuditLog`, trích xuất thông tin người dùng, IP, tham số và kết quả.<br/>
 * Created at 20/06/2026
 *
 * @see <a href="../../../../../../resources/docs/microservice/audit-log-guide.md">Audit Log Specification Guide</a>
 * @author txhoan
 */
@Aspect
@Component
@Slf4j
public class AuditLogAspect {

    /**
     * Chặn và xử lý hành động kiểm toán quanh phương thức Controller được đánh dấu `@AuditLog`.<br/>
     * Created at 20/06/2026
     *
     * @param joinPoint đối tượng joinPoint đại diện phương thức đang thực thi
     * @param auditLog  annotation cấu hình hành động
     * @return kết quả trả về của phương thức gốc
     * @throws Throwable nếu phương thức gốc ném lỗi
     */
    @Around("@annotation(auditLog)")
    public Object audit(ProceedingJoinPoint joinPoint, AuditLog auditLog) throws Throwable {
        long start = System.currentTimeMillis();
        var action = auditLog.action();
        var className = joinPoint.getTarget().getClass().getSimpleName();
        var methodName = joinPoint.getSignature().getName();

        // 1. Lấy thông tin địa chỉ IP Client
        var clientIp = "UNKNOWN";
        var attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            clientIp = request.getRemoteAddr();
        }

        // 2. Lấy thông tin người dùng từ ThreadLocal UserContextHolder
        var username = "ANONYMOUS";
        var userId = "SYSTEM";
        var userContext = UserContextHolder.get();
        if (userContext != null) {
            username = userContext.getUsername();
            userId = userContext.getUserId();
        }

        Object result;
        var status = "SUCCESS";
        String errorMessage = null;

        try {
            // Thực thi phương thức gốc
            result = joinPoint.proceed();
            return result;
        } catch (Throwable t) {
            status = "FAILED";
            errorMessage = t.getMessage();
            throw t;
        } finally {
            long executionTime = System.currentTimeMillis() - start;

            // 3. Xây dựng cấu trúc dữ liệu log
            Map<String, Object> logMap = new HashMap<>();
            logMap.put("action", action);
            logMap.put("className", className);
            logMap.put("methodName", methodName);
            logMap.put("clientIp", clientIp);
            logMap.put("userId", userId);
            logMap.put("username", username);
            logMap.put("status", status);
            logMap.put("latencyMs", executionTime);
            logMap.put("timestamp", LocalDateTime.now().toString());

            if (errorMessage != null) {
                logMap.put("error", errorMessage);
            }

            // 4. Ghi log kiểm toán dạng JSON đồng nhất
            log.info("AUDIT_LOG: {}", JsonUtils.toJson(logMap));
        }
    }
}
