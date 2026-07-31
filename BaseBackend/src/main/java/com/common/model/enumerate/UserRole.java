package com.common.model.enumerate;

/**
 * Vai trò (Role) của người dùng trong hệ thống.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
public enum UserRole {

    /** Quản trị viên hệ thống toàn quyền */
    SUPER_ADMIN,

    /** Quản trị viên tổ chức (Tenant admin) */
    ADMIN,

    /** Người dùng thông thường */
    USER
}
