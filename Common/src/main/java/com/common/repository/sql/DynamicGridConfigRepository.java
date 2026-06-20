package com.common.repository.sql;

import com.common.model.sql.grid.DynamicGridConfig;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository thao tác dữ liệu cho {@link DynamicGridConfig}.<br/>
 * Sử dụng datasource {@code system} — xem {@code SystemJpaConfig}.<br/>
 * <p>
 * Lưu ý: Khi sử dụng {@code @Transactional} trong service, cần chỉ định qualifier:<br/>
 * {@code @Transactional("systemTransactionManager")}<br/>
 * Created at 20/06/2026
 *
 * @author txhoan
 */
@Repository
public interface DynamicGridConfigRepository extends BaseRepository<DynamicGridConfig, Long> {

    /**
     * Tìm cấu hình bảng theo định danh duy nhất {@code gridKey}.<br/>
     *
     * @param gridKey Định danh bảng (ví dụ: "USER_LIST")
     * @return Optional chứa config nếu tìm thấy
     */
    Optional<DynamicGridConfig> findByGridKey(String gridKey);

    /**
     * Kiểm tra sự tồn tại của {@code gridKey}.
     *
     * @param gridKey Định danh bảng cần kiểm tra
     * @return {@code true} nếu đã tồn tại
     */
    boolean existsByGridKey(String gridKey);
}
