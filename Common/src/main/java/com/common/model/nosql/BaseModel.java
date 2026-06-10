package com.common.model.nosql;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;

/**
 * Lớp model chung cho tất cả các NoSQL (MongoDB) entities.<br/>
 * Created at 10/06/2026
 *
 * @author txhoan
 */
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseModel {
    // Khóa chính cho MongoDB Document (sử dụng String tự động ánh xạ với ObjectId)
    @Id
    String id;

    // Thời điểm tạo bản ghi
    @CreatedDate
    LocalDateTime createdAt;

    // Thời điểm chỉnh sửa bản ghi
    @LastModifiedDate
    LocalDateTime modifiedAt;

    // Người tạo
    @CreatedBy
    String createdBy;

    // Người chỉnh sửa
    @LastModifiedBy
    String modifiedBy;

    // Phiên bản dữ liệu hỗ trợ optimistic locking
    @Version
    Long version;
}
