package com.common.eventbus;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation kích hoạt cơ chế Event Bus dùng chung của Common library.<br/>
 * Cho phép chuyển đổi linh hoạt giữa các Message Broker bằng cấu hình properties.<br/>
 * Created at 20/06/2026
 *
 * @see <a href="../../../../../resources/docs/event_bus/event-bus-guide.md">Event Bus Specification Guide</a>
 * @author txhoan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(EventBusConfiguration.class)
public @interface EnableCommonEventBus {
}
