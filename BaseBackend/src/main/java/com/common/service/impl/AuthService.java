package com.common.service.impl;

import com.common.config.security.jwt.JwtProperties;
import com.common.config.security.jwt.JwtTokenProvider;
import com.common.config.security.jwt.TokenBlacklistService;
import com.common.dto.request.AuthRequest;
import com.common.dto.request.RefreshTokenRequest;
import com.common.dto.request.RegisterRequest;
import com.common.dto.response.AuthResponse;
import com.common.dto.response.ProfileDTO;
import com.common.dto.response.UserDTO;
import com.common.exception.BadRequestException;
import com.common.exception.NotFoundException;
import com.common.model.enumerate.EntityStatus;
import com.common.model.enumerate.UserRole;
import com.common.model.sql.Profile;
import com.common.model.sql.Tenant;
import com.common.model.sql.User;
import com.common.repository.sql.ProfileRepository;
import com.common.repository.sql.TenantRepository;
import com.common.repository.sql.UserRepository;
import com.common.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Service xử lý các nghiệp vụ xác thực Auth (Đăng ký, Đăng nhập, Refresh Token, Đăng xuất).
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistService tokenBlacklistService;
    private final JwtProperties jwtProperties;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException(MessageService.getMessage("auth.username_taken"));
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException(MessageService.getMessage("auth.email_taken"));
        }

        // Tìm hoặc Tạo mới Tenant
        Tenant tenant = tenantRepository.findByCode(request.getTenantCode())
                .orElseGet(() -> tenantRepository.save(Tenant.builder()
                        .name(request.getTenantName())
                        .code(request.getTenantCode())
                        .status(EntityStatus.ACTIVE)
                        .build()));

        // Tạo mới User với role mặc định USER
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(EntityStatus.ACTIVE)
                .tenant(tenant)
                .roles(Set.of(UserRole.USER))
                .build();
        user = userRepository.save(user);

        // Tạo mới Profile
        Profile profile = Profile.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .user(user)
                .build();
        profileRepository.save(profile);

        return buildAuthResponse(user);
    }

    @Transactional
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .or(() -> userRepository.findByEmail(request.getUsername()))
                .orElseThrow(() -> new BadRequestException(MessageService.getMessage("auth.invalid_credentials")));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException(MessageService.getMessage("auth.invalid_credentials"));
        }

        if (EntityStatus.ACTIVE != user.getStatus()) {
            throw new BadRequestException(MessageService.getMessage("auth.account_inactive"));
        }

        return buildAuthResponse(user);
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String token = request.getRefreshToken();
        if (!jwtTokenProvider.validateToken(token)) {
            throw new BadRequestException(MessageService.getMessage("auth.invalid_refresh_token"));
        }

        String username = jwtTokenProvider.extractUsername(token);
        String storedToken = tokenBlacklistService.getRefreshToken(username);
        if (storedToken == null || !storedToken.equals(token)) {
            throw new BadRequestException(MessageService.getMessage("auth.refresh_token_revoked"));
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(MessageService.getMessage("auth.user_not_found")));

        return buildAuthResponse(user);
    }

    public void logout(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.extractUsername(token);
                long remainingTtl = jwtTokenProvider.getRemainingTtlMs(token);
                tokenBlacklistService.blacklistToken(token, remainingTtl);
                tokenBlacklistService.revokeRefreshToken(username);
            }
        }
    }

    @Transactional(readOnly = true)
    public UserDTO getCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(MessageService.getMessage("auth.user_not_found")));

        Profile profile = profileRepository.findByUserId(user.getId()).orElse(null);

        ProfileDTO profileDTO = profile != null ? ProfileDTO.builder()
                .id(profile.getId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .phoneNumber(profile.getPhoneNumber())
                .avatarUrl(profile.getAvatarUrl())
                .position(profile.getPosition())
                .department(profile.getDepartment())
                .userId(user.getId())
                .createdAt(profile.getCreatedAt())
                .modifiedAt(profile.getModifiedAt())
                .build() : null;

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .tenantId(user.getTenant() != null ? user.getTenant().getId() : null)
                .tenantCode(user.getTenant() != null ? user.getTenant().getCode() : null)
                .roles(user.getRoles())
                .profile(profileDTO)
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .build();
    }

    private AuthResponse buildAuthResponse(User user) {
        Long tenantId = user.getTenant() != null ? user.getTenant().getId() : null;
        String tenantCode = user.getTenant() != null ? user.getTenant().getCode() : null;

        String accessToken = jwtTokenProvider.generateAccessToken(
                user.getId(), user.getUsername(), user.getEmail(), tenantId, tenantCode, user.getRoles());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUsername());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtProperties.getJwt().getExpirationMs() / 1000)
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .tenantId(tenantId)
                .tenantCode(tenantCode)
                .roles(user.getRoles())
                .build();
    }
}
