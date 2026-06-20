package com.common.model.sql.grid;

import com.common.model.sql.BaseModel;
import com.common.model.sql.grid.enumerate.ColumnDataType;
import com.common.model.sql.grid.enumerate.PinnedPosition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * Thực thể lưu cấu hình từng cột trong bảng động {@link DynamicGridConfig}.<br/>
 * Định nghĩa đầy đủ thuộc tính render của cột: kiểu dữ liệu, độ rộng, ghim, resize, sắp xếp, lọc.<br/>
 * <p>
 * Datasource: {@code system} — quản lý bởi {@code SystemJpaConfig}.<br/>
 * Created at 20/06/2026
 *
 * @see DynamicGridConfig
 * @see ColumnDataType
 * @see PinnedPosition
 * @author txhoan
 */
@Entity
@Table(name = "dynamic_grid_columns")
@Getter
@Setter
@ToString(callSuper = true, exclude = "gridConfig")
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DynamicGridColumn extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * Cấu hình bảng cha chứa cột này.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grid_config_id", nullable = false)
    DynamicGridConfig gridConfig;

    /**
     * Tên field trong data response của API (phân biệt hoa thường).<br/>
     * Ví dụ: {@code "createdAt"}, {@code "userName"}, {@code "status"}
     */
    @Column(name = "field_name", nullable = false, length = 100)
    String fieldName;

    /**
     * Tiêu đề cột hiển thị trên giao diện.
     */
    @Column(name = "header_label", nullable = false, length = 255)
    String headerLabel;

    /**
     * Kiểu dữ liệu của cột — xác định cách frontend render và format giá trị.
     *
     * @see ColumnDataType
     */
    @Column(name = "data_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    ColumnDataType dataType;

    /**
     * Độ rộng cột tính bằng pixel. {@code null} = tự động co giãn (auto).
     */
    @Column(name = "width")
    Integer width;

    /**
     * Độ rộng tối thiểu của cột tính bằng pixel. {@code null} = không giới hạn.
     */
    @Column(name = "min_width")
    Integer minWidth;

    /**
     * Vị trí ghim cột: {@code LEFT} (ghim trái), {@code RIGHT} (ghim phải).<br/>
     * {@code null} = không ghim, cột cuộn bình thường.
     *
     * @see PinnedPosition
     */
    @Column(name = "pinned", length = 10)
    @Enumerated(EnumType.STRING)
    PinnedPosition pinned;

    /**
     * Cho phép người dùng kéo để thay đổi chiều rộng cột.
     */
    @Column(name = "resizable", nullable = false)
    @Builder.Default
    boolean resizable = true;

    /**
     * Cho phép sắp xếp dữ liệu theo cột này (click tiêu đề cột).
     */
    @Column(name = "sortable", nullable = false)
    @Builder.Default
    boolean sortable = true;

    /**
     * Cho phép lọc dữ liệu theo cột này (hiển thị ô input filter).
     */
    @Column(name = "filterable", nullable = false)
    @Builder.Default
    boolean filterable = false;

    /**
     * Ẩn/hiện cột trên giao diện. Cột ẩn vẫn tồn tại trong config nhưng không hiển thị.
     */
    @Column(name = "visible", nullable = false)
    @Builder.Default
    boolean visible = true;

    /**
     * Thứ tự hiển thị cột (ascending). Giá trị nhỏ hơn = hiển thị trước (bên trái).
     */
    @Column(name = "display_order", nullable = false)
    @Builder.Default
    int displayOrder = 0;
}
