package com.common.controller;

import com.common.dto.request.AuthRequest;
import com.common.dto.request.RefreshTokenRequest;
import com.common.dto.request.RegisterRequest;
import com.common.dto.response.AuthResponse;
import com.common.dto.response.UserDTO;
import com.common.dto.response.WrapperApiResponse;
import com.common.service.MessageService;
import com.common.service.impl.AuthService;
import com.common.utilities.Utils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller xử lý các API xác thực Auth (Đăng ký, Đăng nhập, Refresh Token, Đăng xuất, User Info).
 *
 * @created_at 2026-07-31
 * @author txhoan
 */
@RestController
@RequestMapping("${app.api.auth-prefix:${server.servlet.context-path:/api/v1}/auth}")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<WrapperApiResponse> register(@RequestBody @Valid RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return Utils.returnResponse(org.springframework.http.HttpStatus.CREATED, MessageService.getMessage("controller.create_success"), response);
    }

    @PostMapping("/login")
    public ResponseEntity<WrapperApiResponse> login(@RequestBody @Valid AuthRequest request) {
        AuthResponse response = authService.login(request);
        return Utils.returnOkResponse(MessageService.getMessage("controller.get_success"), response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<WrapperApiResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        AuthResponse response = authService.refreshToken(request);
        return Utils.returnOkResponse(MessageService.getMessage("controller.get_success"), response);
    }

    @PostMapping("/logout")
    public ResponseEntity<WrapperApiResponse> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        authService.logout(authHeader);
        return Utils.returnOkResponse(MessageService.getMessage("controller.delete_success"), null);
    }

    @GetMapping("/me")
    public ResponseEntity<WrapperApiResponse> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return Utils.returnOkResponse(MessageService.getMessage("controller.get_success"), null);
        }
        UserDTO userDTO = authService.getCurrentUser(authentication.getName());
        return Utils.returnOkResponse(MessageService.getMessage("controller.get_success"), userDTO);
    }
}
