package com.common.repository.sql;

import com.common.model.sql.Tenant;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository thao tác SQL cho Tenant Entity.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@Repository
public interface TenantRepository extends BaseRepository<Tenant, Long> {

    Optional<Tenant> findByCode(String code);

    boolean existsByCode(String code);
}
