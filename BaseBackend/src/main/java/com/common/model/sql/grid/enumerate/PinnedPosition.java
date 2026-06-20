package com.common.model.sql.grid.enumerate;

import com.common.model.sql.grid.DynamicGridColumn;

/**
 * Vị trí ghim (pinned) của cột trong bảng động {@link DynamicGridColumn}.<br/>
 * Cột được ghim sẽ không cuộn theo khi người dùng scroll ngang.<br/>
 * Created at 20/06/2026
 *
 * @author txhoan
 */
public enum PinnedPosition {

    /** Ghim cột bên trái bảng */
    LEFT,

    /** Ghim cột bên phải bảng */
    RIGHT
}
