package com.common.service.impl;

import com.common.model.sql.User;
import com.common.repository.sql.UserRepository;
import com.common.service.contract.IUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation cho User Entity.
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements IUserService {

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
