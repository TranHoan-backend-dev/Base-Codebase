package com.common.service.contract;

/**
 * Interface cung cấp các thao tác quản lý khóa phân tán (Distributed Lock) lập trình sử dụng Redis.<br/>
 * Giúp đồng bộ hóa và ngăn chặn xung đột dữ liệu khi có nhiều tiến trình/thread truy cập tài nguyên dùng chung.<br/>
 * Created at 20/06/2026
 *
 * @see <a href="../../../../../resources/docs/redis/distributed-lock.md">Distributed Lock Specification</a>
 * @author txhoan
 */
public interface IDistributedLockService {

    /**
     * Cố gắng lấy khóa phân tán ngay lập tức (không chờ đợi).
     *
     * @param lockKey        Key đại diện cho tài nguyên cần khóa
     * @param lockValue      Giá trị định danh duy nhất của Client/Thread giữ khóa (để tránh giải phóng nhầm lock của thread khác)
     * @param expireTimeInMs Thời gian sống (TTL) của khóa tính bằng mili-giây
     * @return true nếu lấy khóa thành công, false nếu khóa đang bị giữ bởi tiến trình khác
     */
    boolean tryLock(String lockKey, String lockValue, long expireTimeInMs);

    /**
     * Cố gắng lấy khóa phân tán, nếu không lấy được sẽ thử lại liên tục trong khoảng thời gian timeout.
     *
     * @param lockKey        Key đại diện cho tài nguyên cần khóa
     * @param lockValue      Giá trị định danh duy nhất của Client/Thread giữ khóa
     * @param expireTimeInMs Thời gian sống (TTL) của khóa tính bằng mili-giây
     * @param timeoutInMs    Thời gian chờ đợi tối đa để lấy khóa tính bằng mili-giây
     * @return true nếu lấy khóa thành công trước khi hết timeout, false nếu hết timeout vẫn không lấy được khóa
     */
    boolean tryLockWithTimeout(String lockKey, String lockValue, long expireTimeInMs, long timeoutInMs);

    /**
     * Giải phóng khóa phân tán một cách an toàn bằng Lua Script (chỉ giải phóng khi giá trị lockValue trùng khớp).
     *
     * @param lockKey   Key đại diện cho tài nguyên cần giải phóng
     * @param lockValue Giá trị định danh duy nhất của Client/Thread đang giữ khóa
     * @return true nếu giải phóng khóa thành công, false nếu khóa không tồn tại hoặc lockValue không trùng khớp
     */
    boolean releaseLock(String lockKey, String lockValue);
}
