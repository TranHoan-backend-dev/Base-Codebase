package com.common.repository.sql;

import com.common.model.sql.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository thao tác SQL cho Profile Entity.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@Repository
public interface ProfileRepository extends BaseRepository<Profile, Long> {

    Optional<Profile> findByUserId(Long userId);
}
