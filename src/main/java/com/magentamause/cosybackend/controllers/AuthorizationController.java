package com.magentamause.cosybackend.controllers;

import com.magentamause.cosybackend.DTOs.LoginDto;
import com.magentamause.cosybackend.security.jwtfilter.JwtTokenBody;
import com.magentamause.cosybackend.security.jwtfilter.JwtUtils;
import com.magentamause.cosybackend.services.AuthorizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthorizationService authorizationService;
    private final JwtUtils jwtUtils;

    @Value("${server.servlet.context-path}")
    private String basePath;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginDto loginDto) {
        String refreshToken =
                authorizationService.loginUser(loginDto.getUsername(), loginDto.getPassword());
        ResponseCookie responseCookie =
                ResponseCookie.from("refreshToken", refreshToken)
                        .httpOnly(true)
                        .secure(false)
                        .maxAge(
                                jwtUtils.getTokenValidityDuration(
                                                JwtTokenBody.TokenType.REFRESH_TOKEN)
                                        / 1000)
                        .path(basePath + "/auth/token")
                        .sameSite("Strict")
                        .build();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }

    @GetMapping("/token")
    public ResponseEntity<String> fetchToken(
            @CookieValue(value = "refreshToken") String refreshToken) {
        return ResponseEntity.ok(
                authorizationService.fetchIdentityTokenFromRefreshToken(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        ResponseCookie deleteCookie =
                ResponseCookie.from("refreshToken", "")
                        .httpOnly(true)
                        .secure(false)
                        .path("/")
                        .maxAge(0)
                        .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body("Success");
    }
}
