package com.common.utilities.audit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation dùng để đánh dấu phương thức Controller cần ghi nhận nhật ký kiểm toán hành động (Audit Log).<br/>
 * Aspect `AuditLogAspect` sẽ tự động quét và thu thập các thông tin chạy của luồng.<br/>
 * Created at 20/06/2026
 *
 * @see <a href="../../../../../../resources/docs/microservice/audit-log-guide.md">Audit Log Specification Guide</a>
 * @author txhoan
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {

    /**
     * Tên hành động thực hiện (Ví dụ: "CREATE_USER", "UPDATE_PRODUCT", vv.).
     *
     * @return tên hành động
     */
    String action();
}
