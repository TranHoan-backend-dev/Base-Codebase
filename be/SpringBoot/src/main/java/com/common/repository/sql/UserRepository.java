package com.common.repository.sql;

import com.common.model.sql.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository thao tác SQL cho User Entity.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
