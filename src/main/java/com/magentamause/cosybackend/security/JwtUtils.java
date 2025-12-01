package com.magentamause.cosybackend.security;

import com.magentamause.cosybackend.security.config.JwtProperties;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtParser jwtParser;
    private final JwtProperties jwtProperties;
    private final Key signingKey;

    public String generateIdentityToken(Map<String, Object> claims, String username) {
        return createToken(claims, username, jwtProperties.identityTokenExpirationTime());
    }

    public String generateRefreshToken(Map<String, Object> claims, String username) {
        return createToken(claims, username, jwtProperties.refreshTokenExpirationTime());
    }

    private String createToken(Map<String, Object> claims, String subject, long expirationTime) {
        return Jwts.builder()
                .issuer("cosy-backend")
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(signingKey)
                .compact();
    }

    public Map<String, Object> getTokenContentBody(String token, JwtTokenBody.TokenType tokenType)
            throws SecurityException {
        try {
            Claims claims = jwtParser.parseSignedClaims(token).getPayload();

            if (!tokenType.toString().equals(claims.get("tokenType"))) {
                throw new SecurityException("Token type mismatch. Expected: " + tokenType);
            }

            if (!"cosy-backend".equals(claims.getIssuer())){
                throw new SecurityException("Invalid token issuer");
            }

            if (claims.getSubject() == null || claims.getSubject().isEmpty()) {
                throw new SecurityException("Missing subject claim");
            }

            return claims;

        } catch (ExpiredJwtException e) {
            log.error("Token expired: {}", e.getMessage());
            throw new SecurityException("Token expired");
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            log.error("Invalid token format: {}", e.getMessage());
            throw new SecurityException("Invalid token format");
        } catch (SecurityException e) {
            log.error("Token validation failed: {}", e.getMessage());
            throw new SecurityException(e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error while parsing JWT token", e);
            throw new SecurityException("Token validation failed");
        }
    }
}
