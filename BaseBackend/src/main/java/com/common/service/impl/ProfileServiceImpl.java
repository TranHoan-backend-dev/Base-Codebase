package com.common.service.impl;

import com.common.model.sql.Profile;
import com.common.repository.sql.ProfileRepository;
import com.common.service.contract.IProfileService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation cho Profile Entity.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@Service
public class ProfileServiceImpl extends BaseServiceImpl<Profile, Long, ProfileRepository> implements IProfileService {

    public ProfileServiceImpl(ProfileRepository repository) {
        super(repository);
    }

    @Override
    public Optional<Profile> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
}
