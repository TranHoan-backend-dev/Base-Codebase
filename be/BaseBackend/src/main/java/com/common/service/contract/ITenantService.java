package com.common.service.contract;

import com.common.model.sql.Tenant;

import java.util.Optional;

/**
 * Service contract cho Tenant Entity.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
public interface ITenantService extends IBaseService<Tenant, Long> {

    Optional<Tenant> findByCode(String code);
}
