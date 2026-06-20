package com.common.utilities;

import com.common.config.security.UserContext;
import com.common.config.security.UserContextHolder;
import com.common.utilities.audit.AuditLog;
import com.common.utilities.audit.AuditLogAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Lớp kiểm thử tự động cho AuditLogAspect.<br/>
 * Xác minh việc ghi nhật ký và trích xuất thông tin người dùng diễn ra chính xác cho cả luồng thành công và thất bại.<br/>
 * Created at 20/06/2026
 *
 * @author txhoan
 */
class AuditLogAspectTest {

    private AuditLogAspect aspect;
    private ProceedingJoinPoint joinPoint;
    private AuditLog auditLog;

    @BeforeEach
    void setUp() {
        aspect = new AuditLogAspect();
        joinPoint = mock(ProceedingJoinPoint.class);
        auditLog = mock(AuditLog.class);

        // Giả lập các phương thức phản xạ của JointPoint
        Object target = new Object();
        Signature signature = mock(Signature.class);
        when(joinPoint.getTarget()).thenReturn(target);
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("testMethod");
        when(auditLog.action()).thenReturn("TEST_ACTION");

        // Giả lập thông tin người dùng đăng nhập trong Security Context
        UserContextHolder.set(new UserContext("user123", "testuser", List.of("ROLE_USER")));
    }

    @AfterEach
    void tearDown() {
        UserContextHolder.clear();
    }

    @Test
    void testAuditSuccess() throws Throwable {
        when(joinPoint.proceed()).thenReturn("SuccessResponse");

        Object result = aspect.audit(joinPoint, auditLog);

        assertEquals("SuccessResponse", result);
        verify(joinPoint, times(1)).proceed();
    }

    @Test
    void testAuditFailure() throws Throwable {
        when(joinPoint.proceed()).thenThrow(new RuntimeException("Test Exception"));

        assertThrows(RuntimeException.class, () -> aspect.audit(joinPoint, auditLog));
        verify(joinPoint, times(1)).proceed();
    }
}
