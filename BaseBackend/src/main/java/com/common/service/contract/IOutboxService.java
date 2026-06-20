package com.common.service.contract;

/**
 * Interface dịch vụ quản lý và lưu trữ sự kiện theo mô hình Transactional Outbox.<br/>
 * Đảm bảo ghi nhận sự kiện vào cơ sở dữ liệu trước khi gửi đi.<br/>
 * Created at 20/06/2026
 *
 * @author txhoan
 */
public interface IOutboxService {

    /**
     * Lưu trữ sự kiện nghiệp vụ vào bảng outbox dưới dạng JSON.<br/>
     * Phương thức này cần chạy trong cùng transaction của luồng nghiệp vụ chính.<br/>
     *
     * @param aggregateType Loại đối tượng nghiệp vụ phát sinh sự kiện (ví dụ: Order, Account, SystemSetting)
     * @param aggregateId Định danh của đối tượng nghiệp vụ
     * @param eventType Tên loại sự kiện (ví dụ: OrderCreated, SystemSettingUpdated)
     * @param payload Dữ liệu chi tiết của sự kiện (sẽ được tự động serialize sang JSON)
     */
    void saveEvent(String aggregateType, String aggregateId, String eventType, Object payload);
}
