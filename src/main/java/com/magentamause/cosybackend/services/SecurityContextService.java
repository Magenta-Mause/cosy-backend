package com.magentamause.cosybackend.services;

import com.magentamause.cosybackend.entities.UserEntity;
import com.magentamause.cosybackend.exceptions.NoAuthenticationFoundException;
import com.magentamause.cosybackend.security.jwtfilter.AuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SecurityContextService {
    public AuthenticationToken getAuthenticationToken() {
        try {
            return (AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        } catch (ClassCastException e) {
            throw new NoAuthenticationFoundException(e);
        }
    }

    public String getUsername() {
        return getAuthenticationToken().getUser().getUsername();
    }

    public String getUserId() {
        return getAuthenticationToken().getUserId();
    }

    public UserEntity getUser() {
        return getAuthenticationToken().getUser();
    }

    public void assertUserHasRole(UserEntity.Role role) {
        if (getUser().getRole().equals(UserEntity.Role.OWNER)) {
            return;
        }

        if (!getAuthenticationToken().getUser().getRole().equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient permissions");
        }
    }
}
