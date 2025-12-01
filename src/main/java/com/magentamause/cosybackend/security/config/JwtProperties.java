package com.magentamause.cosybackend.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String secretKey, long refreshTokenExpirationTime, long identityTokenExpirationTime) {}
