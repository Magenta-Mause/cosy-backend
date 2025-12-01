package com.magentamause.cosybackend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.magentamause.cosybackend.entities.LoginEntry;
import com.magentamause.cosybackend.entities.UserEntity;
import com.magentamause.cosybackend.security.JwtTokenBody;
import com.magentamause.cosybackend.security.JwtUtils;
import com.magentamause.cosybackend.services.JwtService;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTests {
    @Mock private JwtUtils jwtUtils;

    @InjectMocks private JwtService jwtService;

    private LoginEntry createLoginEntry() {
        LoginEntry entry = new LoginEntry();
        entry.setUsername("john");
        UserEntity user = new UserEntity();
        entry.setUser(user);
        return entry;
    }

    @Test
    void buildRefreshToken_callsJwtUtilsWithCorrectClaims() {
        LoginEntry entry = createLoginEntry();

        jwtService.buildRefreshToken(entry);

        ArgumentCaptor<Map<String, Object>> claimsCaptor = ArgumentCaptor.forClass(Map.class);
        verify(jwtUtils).generateRefreshToken(claimsCaptor.capture(), eq("john"));

        Map<String, Object> claims = claimsCaptor.getValue();
        assertThat(claims.get("username")).isEqualTo("john");
        assertThat(claims.get("tokenType")).isEqualTo(JwtTokenBody.TokenType.REFRESH_TOKEN);
    }

    @Test
    void buildRefreshToken_returnsTokenFromJwtUtils() {
        LoginEntry entry = createLoginEntry();
        when(jwtUtils.generateRefreshToken(any(), any())).thenReturn("REFRESH_TOKEN");

        String token = jwtService.buildRefreshToken(entry);

        assertThat(token).isEqualTo("REFRESH_TOKEN");
    }

    @Test
    void buildRefreshToken_passesCorrectSubject() {
        LoginEntry entry = createLoginEntry();

        jwtService.buildRefreshToken(entry);

        verify(jwtUtils).generateRefreshToken(any(), eq("john"));
    }

    @Test
    void buildIdentityToken_callsJwtUtilsWithCorrectClaims() {
        LoginEntry entry = createLoginEntry();

        jwtService.buildIdentityToken(entry);

        ArgumentCaptor<Map<String, Object>> claimsCaptor = ArgumentCaptor.forClass(Map.class);
        verify(jwtUtils).generateIdentityToken(claimsCaptor.capture(), eq("john"));

        Map<String, Object> claims = claimsCaptor.getValue();
        assertThat(claims.get("username")).isEqualTo("john");
        assertThat(claims.get("user")).isEqualTo(entry.getUser());
        assertThat(claims.get("tokenType")).isEqualTo(JwtTokenBody.TokenType.IDENTITY_TOKEN);
    }

    @Test
    void buildIdentityToken_returnsTokenFromJwtUtils() {
        LoginEntry entry = createLoginEntry();
        when(jwtUtils.generateIdentityToken(any(), any())).thenReturn("IDENTITY_TOKEN");

        String token = jwtService.buildIdentityToken(entry);

        assertThat(token).isEqualTo("IDENTITY_TOKEN");
    }

    @Test
    void buildIdentityToken_passesCorrectSubject() {
        LoginEntry entry = createLoginEntry();

        jwtService.buildIdentityToken(entry);

        verify(jwtUtils).generateIdentityToken(any(), eq("john"));
    }
}
