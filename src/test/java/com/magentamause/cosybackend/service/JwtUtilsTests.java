package com.magentamause.cosybackend.service;

import static org.junit.jupiter.api.Assertions.*;

import com.magentamause.cosybackend.security.JwtTokenBody;
import com.magentamause.cosybackend.security.JwtUtils;
import com.magentamause.cosybackend.security.config.JwtProperties;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import java.util.Map;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtUtilsTests {
    @Autowired private JwtUtils jwtUtils;

    @Autowired private JwtParser jwtParser;

    @Autowired private JwtProperties jwtProperties;

    private Map<String, Object> claims;
    private String username;

    @BeforeEach
    void setUp() {
        username = "testuser";
        claims = Map.of("username", username, "tokenType", JwtTokenBody.TokenType.IDENTITY_TOKEN);
    }

    @Test
    void generateIdentityToken_shouldReturnValidToken() {
        String token = jwtUtils.generateIdentityToken(claims, username);
        assertNotNull(token);

        Map<String, Object> parsedClaims =
                jwtUtils.getTokenContentBody(token, JwtTokenBody.TokenType.IDENTITY_TOKEN);
        assertEquals(username, parsedClaims.get("username"));
        assertEquals(
                JwtTokenBody.TokenType.IDENTITY_TOKEN.toString(), parsedClaims.get("tokenType"));
    }

    @Test
    void generateRefreshToken_shouldReturnValidToken() {
        claims = Map.of("username", username, "tokenType", JwtTokenBody.TokenType.REFRESH_TOKEN);

        String token = jwtUtils.generateRefreshToken(claims, username);
        assertNotNull(token);

        Map<String, Object> parsedClaims =
                jwtUtils.getTokenContentBody(token, JwtTokenBody.TokenType.REFRESH_TOKEN);
        assertEquals(username, parsedClaims.get("username"));
        assertEquals(
                JwtTokenBody.TokenType.REFRESH_TOKEN.toString(), parsedClaims.get("tokenType"));
    }

    @Test
    void getTokenContentBody_withWrongTokenType_shouldThrowException() {
        String token = jwtUtils.generateIdentityToken(claims, username);

        SecurityException exception =
                assertThrows(
                        SecurityException.class,
                        () ->
                                jwtUtils.getTokenContentBody(
                                        token, JwtTokenBody.TokenType.REFRESH_TOKEN));

        assertTrue(exception.getMessage().contains("Token type mismatch"));
    }

    @Test
    void getTokenContentBody_withInvalidToken_shouldThrowException() {
        String invalidToken = "this.is.not.a.valid.jwt";

        SecurityException exception =
                assertThrows(
                        SecurityException.class,
                        () ->
                                jwtUtils.getTokenContentBody(
                                        invalidToken, JwtTokenBody.TokenType.IDENTITY_TOKEN));

        assertTrue(exception.getMessage().contains("Invalid token format"));
    }

    @Test
    void getTokenContentBody_withExpiredToken_shouldThrowException() throws InterruptedException {
        SecretKey key = Jwts.SIG.HS256.key().build();

        JwtParser jwtParser = Jwts.parser().verifyWith(key).build();

        JwtProperties shortExpiryProps = new JwtProperties("secret", 1000, 1); // 1ms expiry
        JwtUtils shortLivedJwtUtils = new JwtUtils(jwtParser, shortExpiryProps, key);

        Map<String, Object> claims =
                Map.of("username", "testuser", "tokenType", JwtTokenBody.TokenType.IDENTITY_TOKEN);

        String token = shortLivedJwtUtils.generateIdentityToken(claims, "testuser");

        Thread.sleep(5);

        SecurityException exception =
                assertThrows(
                        SecurityException.class,
                        () ->
                                shortLivedJwtUtils.getTokenContentBody(
                                        token, JwtTokenBody.TokenType.IDENTITY_TOKEN));

        assertTrue(exception.getMessage().contains("Token expired"));
    }
}
