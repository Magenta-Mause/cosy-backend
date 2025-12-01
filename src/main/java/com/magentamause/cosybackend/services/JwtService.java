package com.magentamause.cosybackend.services;

import com.magentamause.cosybackend.entities.LoginEntry;
import com.magentamause.cosybackend.security.JwtTokenBody;
import com.magentamause.cosybackend.security.JwtUtils;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtUtils jwtUtils;

    public String buildRefreshToken(LoginEntry loginEntry) {
        Map<String, Object> claims =
                Map.of(
                        "username",
                        loginEntry.getUsername(),
                        "tokenType",
                        JwtTokenBody.TokenType.REFRESH_TOKEN);

        return jwtUtils.generateRefreshToken(claims, loginEntry.getUsername());
    }

    public String buildIdentityToken(LoginEntry loginEntry) {
        Map<String, Object> claims =
                Map.of(
                        "username", loginEntry.getUsername(),
                        "user", loginEntry.getUser(),
                        "tokenType", JwtTokenBody.TokenType.IDENTITY_TOKEN);

        return jwtUtils.generateIdentityToken(claims, loginEntry.getUsername());
    }
}
