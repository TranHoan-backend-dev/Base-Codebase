package com.common.service.contract;

import com.common.model.sql.Profile;

import java.util.Optional;

/**
 * Service contract cho Profile Entity.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
public interface IProfileService extends IBaseService<Profile, Long> {

    Optional<Profile> findByUserId(Long userId);
}
