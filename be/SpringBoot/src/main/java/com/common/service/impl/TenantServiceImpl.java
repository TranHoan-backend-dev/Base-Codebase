package com.common.service.impl;

import com.common.model.sql.Tenant;
import com.common.repository.sql.TenantRepository;
import com.common.service.contract.ITenantService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation cho Tenant Entity.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@Service
public class TenantServiceImpl extends BaseServiceImpl<Tenant, Long, TenantRepository> implements ITenantService {

    public TenantServiceImpl(TenantRepository repository) {
        super(repository);
    }

    @Override
    public Optional<Tenant> findByCode(String code) {
        return repository.findByCode(code);
    }
}
