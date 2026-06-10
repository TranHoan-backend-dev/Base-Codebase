package com.common.model.sql;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Lớp model chung cho tất cả các model khác.<br/>
 * Created at 04/06/2026
 *
 * @author txhoan
 */
@Getter
@Setter
@ToString
@MappedSuperclass
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseModel {
    // Thời điểm tạo bản ghi
    @CreatedDate
    @Column(nullable = false)
    LocalDateTime createdAt;

    // Thời điểm chỉnh sửa bản ghi
    @Column(nullable = false)
    LocalDateTime modifiedAt;

    // Người tạo
    @CreatedBy
    @Column(nullable = false)
    String createdBy;

    // Người chỉnh sửa
    @LastModifiedDate
    @Column(nullable = false)
    String modifiedBy;

    // Tự động tạo thông tin khi khởi tạo đối tượng
    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    // Tự động cập nhâ thông tin
    @PreUpdate
    void preUpdate() {
        modifiedAt = LocalDateTime.now();
    }
}
