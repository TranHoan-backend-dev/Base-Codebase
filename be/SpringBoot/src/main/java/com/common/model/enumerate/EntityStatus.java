package com.common.model.enumerate;

/**
 * Trạng thái hoạt động của Entity (Tenant, User).
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
public enum EntityStatus {

    /** Đang hoạt động bình thường */
    ACTIVE,

    /** Đã bị vô hiệu hóa / khóa */
    INACTIVE,

    /** Đang chờ xác minh */
    PENDING
}
