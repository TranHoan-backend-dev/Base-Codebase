package com.common.service.contract;

import com.common.model.sql.User;

import java.util.Optional;

/**
 * Service contract cho User Entity.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
public interface IUserService extends IBaseService<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
