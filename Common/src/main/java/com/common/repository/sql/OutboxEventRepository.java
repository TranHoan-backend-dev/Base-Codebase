package com.common.repository.sql;

import com.common.model.sql.OutboxEvent;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface Repository quản lý thực thể OutboxEvent cho cơ sở dữ liệu SQL.<br/>
 * Kế thừa BaseRepository để có sẵn các phương thức CRUD tối ưu.<br/>
 * Created at 20/06/2026
 *
 * @author txhoan
 */
@Repository
public interface OutboxEventRepository extends BaseRepository<OutboxEvent, Long> {

    /**
     * Tìm kiếm các sự kiện outbox theo trạng thái và được sắp xếp tăng dần theo ID.<br/>
     *
     * @param status trạng thái của sự kiện (PENDING, PROCESSED, FAILED)
     * @return danh sách các sự kiện outbox khớp điều kiện
     */
    List<OutboxEvent> findByStatusOrderByIdAsc(String status);

    /**
     * Tìm kiếm các sự kiện outbox có trạng thái PENDING hoặc FAILED với số lần retry nhỏ hơn giới hạn.<br/>
     *
     * @param status trạng thái của sự kiện
     * @param maxRetryCount giới hạn tối đa số lần retry
     * @return danh sách các sự kiện outbox khớp điều kiện
     */
    List<OutboxEvent> findByStatusAndRetryCountLessThanOrderByIdAsc(String status, int maxRetryCount);
}
