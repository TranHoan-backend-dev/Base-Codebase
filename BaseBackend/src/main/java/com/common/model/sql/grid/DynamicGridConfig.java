package com.common.model.sql.grid;

import com.common.model.sql.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Thực thể lưu cấu hình bảng động (Dynamic Grid) để render lên web.<br/>
 * Mỗi bản ghi định nghĩa một bảng với danh sách cột, hỗ trợ tree grid.<br/>
 * <p>
 * Ví dụ sử dụng: Grid "USER_LIST" có 5 cột, bật tree grid theo parentId.<br/>
 * <p>
 * Datasource: {@code system} — quản lý bởi {@code SystemJpaConfig}.<br/>
 * Created at 20/06/2026
 *
 * @see DynamicGridColumn
 * @author txhoan
 */
@Entity
@Table(name = "dynamic_grid_configs")
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DynamicGridConfig extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * Định danh duy nhất của bảng, dùng để tra cứu config từ frontend.<br/>
     * Ví dụ: "USER_LIST", "ORDER_MANAGEMENT", "PRODUCT_CATALOG"
     */
    @Column(name = "grid_key", nullable = false, unique = true, length = 100)
    String gridKey;

    /**
     * Tên hiển thị của bảng (dùng cho UI / admin).
     */
    @Column(name = "grid_label", nullable = false, length = 255)
    String gridLabel;

    /**
     * Bật/tắt chế độ tree grid — hiển thị dữ liệu dạng cây phân cấp.
     */
    @Column(name = "tree_grid_enabled", nullable = false)
    @Builder.Default
    boolean treeGridEnabled = false;

    /**
     * Tên field trong data response dùng làm ID node cha (chỉ áp dụng khi {@code treeGridEnabled = true}).<br/>
     * Ví dụ: {@code "id"}
     */
    @Column(name = "tree_id_field", length = 100)
    String treeIdField;

    /**
     * Tên field trong data response dùng làm tham chiếu đến node cha (chỉ áp dụng khi {@code treeGridEnabled = true}).<br/>
     * Ví dụ: {@code "parentId"}
     */
    @Column(name = "tree_parent_field", length = 100)
    String treeParentField;

    /**
     * Danh sách cấu hình cột của bảng này, sắp xếp theo {@code displayOrder} tăng dần.
     */
    @OneToMany(mappedBy = "gridConfig", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    @Builder.Default
    List<DynamicGridColumn> columns = new ArrayList<>();
}
