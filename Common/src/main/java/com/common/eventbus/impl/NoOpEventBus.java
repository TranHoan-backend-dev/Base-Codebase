package com.common.eventbus.impl;

import com.common.eventbus.IEventBus;
import lombok.extern.slf4j.Slf4j;

/**
 * Triển khai Event Bus giả lập (No-Op), không gửi đi đâu cả.<br/>
 * Bean này được tự động cấu hình khi `common.event-bus.type=none` hoặc khi thiếu cấu hình.<br/>
 * Created at 20/06/2026
 *
 * @see <a href="../../../../../../resources/docs/event_bus/event-bus-guide.md">Event Bus Specification Guide</a>
 * @author txhoan
 */
@Slf4j
public class NoOpEventBus implements IEventBus {

    /**
     * Chỉ log ra sự kiện nhận được và không làm gì thêm.<br/>
     * Created at 20/06/2026
     */
    @Override
    public void publish(String topic, Object event) {
        log.info("Event Bus is disabled. Event to [{}] ignored: {}", topic, event);
    }
}
