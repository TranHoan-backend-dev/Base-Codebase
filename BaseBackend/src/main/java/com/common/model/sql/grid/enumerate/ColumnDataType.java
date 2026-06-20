package com.common.model.sql.grid.enumerate;

import com.common.model.sql.grid.DynamicGridColumn;

/**
 * Kiểu dữ liệu của cột trong bảng động {@link DynamicGridColumn}.<br/>
 * Được dùng để frontend biết cách render và format giá trị của từng cột.<br/>
 * Created at 20/06/2026
 *
 * @author txhoan
 */
public enum ColumnDataType {

    /** Văn bản thông thường */
    TEXT,

    /** Số nguyên hoặc số thực */
    NUMBER,

    /** Ngày (yyyy-MM-dd) */
    DATE,

    /** Ngày giờ (yyyy-MM-dd HH:mm:ss) */
    DATETIME,

    /** Giá trị đúng/sai */
    BOOLEAN,

    /** Giá trị từ danh sách cố định (dropdown/select) */
    ENUM,

    /** Badge màu sắc (ví dụ: trạng thái ACTIVE/INACTIVE) */
    BADGE,

    /** Đường dẫn / liên kết */
    LINK,

    /** Cột chứa các hành động (button edit, delete,...) */
    ACTION
}
