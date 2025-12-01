package com.magentamause.cosybackend.service;

import static org.junit.jupiter.api.Assertions.*;

import com.magentamause.cosybackend.security.JwtTokenBody;
import com.magentamause.cosybackend.security.JwtUtils;
import com.magentamause.cosybackend.security.config.JwtProperties;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class JwtUtilsTests {

    private JwtUtils jwtUtils;
    private SecretKey signingKey;
    private JwtParser jwtParser;
    private JwtProperties jwtProperties;

    private Map<String, Object> claims;
    private String username;

    @BeforeEach
    void setUp() {
        username = "testuser";
        claims = Map.of(
                "username", username,
                "tokenType", JwtTokenBody.TokenType.IDENTITY_TOKEN);

        signingKey = Jwts.SIG.HS256.key().build();

        jwtParser = Jwts.parser()
                .verifyWith(signingKey)
                .build();

        jwtProperties = new JwtProperties("secret", 60000, 3600000);

        jwtUtils = new JwtUtils(jwtParser, jwtProperties, signingKey);
    }

    @Test
    void generateIdentityToken_shouldReturnValidToken() {
        String token = jwtUtils.generateIdentityToken(claims, username);
        assertNotNull(token);

        Map<String, Object> parsedClaims =
                jwtUtils.getTokenContentBody(token, JwtTokenBody.TokenType.IDENTITY_TOKEN);
        assertEquals(username, parsedClaims.get("username"));
        assertEquals(JwtTokenBody.TokenType.IDENTITY_TOKEN.toString(), parsedClaims.get("tokenType"));
    }

    @Test
    void generateRefreshToken_shouldReturnValidToken() {
        claims = Map.of(
                "username", username,
                "tokenType", JwtTokenBody.TokenType.REFRESH_TOKEN);

        String token = jwtUtils.generateRefreshToken(claims, username);
        assertNotNull(token);

        Map<String, Object> parsedClaims =
                jwtUtils.getTokenContentBody(token, JwtTokenBody.TokenType.REFRESH_TOKEN);
        assertEquals(username, parsedClaims.get("username"));
        assertEquals(JwtTokenBody.TokenType.REFRESH_TOKEN.toString(), parsedClaims.get("tokenType"));
    }

    @Test
    void getTokenContentBody_withWrongTokenType_shouldThrowException() {
        String token = jwtUtils.generateIdentityToken(claims, username);

        SecurityException exception = assertThrows(
                SecurityException.class,
                () -> jwtUtils.getTokenContentBody(token, JwtTokenBody.TokenType.REFRESH_TOKEN)
        );

        assertTrue(exception.getMessage().contains("Token type mismatch"));
    }

    @Test
    void getTokenContentBody_withInvalidToken_shouldThrowException() {
        String invalidToken = "this.is.not.a.valid.jwt";

        SecurityException exception = assertThrows(
                SecurityException.class,
                () -> jwtUtils.getTokenContentBody(invalidToken, JwtTokenBody.TokenType.IDENTITY_TOKEN)
        );

        assertTrue(exception.getMessage().contains("Invalid token format"));
    }

    @Test
    void getTokenContentBody_withExpiredToken_shouldThrowException() throws InterruptedException {
        JwtProperties shortExpiryProps = new JwtProperties("secret", 1000, 1);
        JwtUtils shortLivedJwtUtils = new JwtUtils(jwtParser, shortExpiryProps, signingKey);

        String token = shortLivedJwtUtils.generateIdentityToken(claims, username);

        Thread.sleep(5);

        SecurityException exception = assertThrows(
                SecurityException.class,
                () -> shortLivedJwtUtils.getTokenContentBody(token, JwtTokenBody.TokenType.IDENTITY_TOKEN)
        );

        assertTrue(exception.getMessage().contains("Token expired"));
    }
}
