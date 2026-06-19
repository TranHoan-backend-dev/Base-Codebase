package com.common.model.sql;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Lớp model cơ sở hỗ trợ xóa mềm cho các SQL JPA Entity.<br/>
 * Kế thừa từ BaseModel để giữ lại các thông tin kiểm toán (Audit fields).<br/>
 * Created at 19/06/2026
 *
 * @see <a href="../../../../../resources/docs/spring_structure/datasource-guide.md">Datasource and Auditing Guide</a>
 * @author txhoan
 */
@Getter
@Setter
@ToString(callSuper = true)
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseSoftDeleteModel extends BaseModel {
    
    @Column(nullable = false)
    @lombok.Builder.Default
    boolean deleted = false;

    LocalDateTime deletedAt;

    String deletedBy;
}
