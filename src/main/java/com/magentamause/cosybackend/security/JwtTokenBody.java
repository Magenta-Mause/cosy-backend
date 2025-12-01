package com.magentamause.cosybackend.security;

import com.magentamause.cosybackend.entities.UserEntity;
import java.util.Collection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtTokenBody {
    private String email;
    private TokenType tokenType;
    private Collection<UserEntity> authorizedUsers;

    public enum TokenType {
        REFRESH_TOKEN,
        IDENTITY_TOKEN,
        REQUEST_TOKEN
    }
}
