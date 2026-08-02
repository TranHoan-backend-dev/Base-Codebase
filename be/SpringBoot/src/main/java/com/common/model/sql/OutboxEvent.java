package com.common.model.sql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Thực thể lưu trữ sự kiện outbox để gửi sự kiện tin cậy theo mẫu thiết kế Transactional Outbox.<br/>
 * Kế thừa BaseModel để tự động quản lý các trường kiểm toán (Audit fields).<br/>
 * Created at 20/06/2026
 *
 * @author txhoan
 */
@Entity
@Table(name = "outbox_events")
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OutboxEvent extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // Loại aggregate liên quan (ví dụ: Order, Account, SystemSetting)
    @Column(name = "aggregate_type", nullable = false)
    String aggregateType;

    // Định danh của aggregate liên quan
    @Column(name = "aggregate_id", nullable = false)
    String aggregateId;

    // Loại sự kiện (ví dụ: OrderCreated, SystemSettingUpdated)
    @Column(name = "event_type", nullable = false)
    String eventType;

    // Nội dung sự kiện (thường là định dạng JSON)
    @Column(name = "payload", nullable = false, columnDefinition = "TEXT")
    String payload;

    // Trạng thái xử lý: PENDING, PROCESSED, FAILED
    @Column(name = "status", nullable = false)
    String status;

    // Số lần thử lại gửi sự kiện
    @Column(name = "retry_count", nullable = false)
    Integer retryCount;

    // Thông tin lỗi (nếu có) khi gửi sự kiện thất bại
    @Column(name = "error_message", length = 1000)
    String errorMessage;
}
