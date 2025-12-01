package com.magentamause.cosybackend.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magentamause.cosybackend.security.config.JwtProperties;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class UtilConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public SecretKey getSignInKey(JwtProperties jwtProperties) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Bean
    public JwtParser jwtParser(SecretKey jwtSecretKey) {
        return Jwts.parser()
                .verifyWith(jwtSecretKey)
                .build();
    }

}
