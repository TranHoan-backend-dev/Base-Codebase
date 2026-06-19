package com.common.utilities.security;

/**
 * Định nghĩa các danh mục dữ liệu nhạy cảm cần được che giấu (masking)
 * để tránh việc vô tình tiết lộ thông tin (Information Sensitive Disclosure).<br/>
 * Created at 19/06/2026
 *
 * @see <a href="../../../../../resources/docs/security_filters/security-filter-guide.md">Security and Web Filter Guide</a>
 * @see "Common/src/main/resources/docs/security_filters/security-filter-guide.md"
 * @author txhoan
 */
public enum MaskType {
    /**
     * Định dạng Email (Ví dụ: abc***@gmail.com)
     */
    EMAIL,

    /**
     * Số điện thoại (Ví dụ: 098****789)
     */
    PHONE,

    /**
     * Mật khẩu (Ví dụ: ******)
     */
    PASSWORD,

    /**
     * Thẻ tín dụng/ghi nợ (Ví dụ: ****-****-****-1234)
     */
    CREDIT_CARD,

    /**
     * Chứng minh nhân dân / Căn cước công dân (Ví dụ: 001******789)
     */
    ID_CARD
}
