package com.magentamause.cosybackend.security.jwtfilter;

import com.magentamause.cosybackend.entities.UserEntity;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@Setter
public class AuthenticationToken extends UsernamePasswordAuthenticationToken {
    String userId;
    UserEntity user;

    public AuthenticationToken(String userId, UserEntity user) {
        super(userId, null, List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
        this.user = user;
        this.userId = userId;
    }
}
