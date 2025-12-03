package com.magentamause.cosybackend.services;

import com.magentamause.cosybackend.entities.UserEntity;
import com.magentamause.cosybackend.entities.UserInviteEntity;
import com.magentamause.cosybackend.repositories.UserInviteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInviteService {

    private final UserInviteRepository userInviteRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserEntityService userEntityService;

    public UserInviteEntity inviteUser(String username) {
        return userInviteRepository.save(UserInviteEntity.builder().username(username).build());
    }

    public UserInviteEntity getUserInviteByToken(String userInviteToken) {
        return userInviteRepository
                .findByInviteKey(userInviteToken)
                .orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Invite " + userInviteToken + " not found"));
    }

    public void revokeInvite(String uuid) {
        getUserInviteByToken(uuid);
        userInviteRepository.deleteById(uuid);
    }

    public UserEntity createUserFromInvite(String userInviteKey, String password, String username) {
        UserInviteEntity invite = getUserInviteByToken(userInviteKey);
        UserEntity.UserEntityBuilder userEntityBuilder = UserEntity.builder();
        if (invite.getUsername() == null) {
            if (username == null || username.length() < 3) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Username must be at least 3 characters long");
            }
            userEntityBuilder.username(username);
        } else {
            userEntityBuilder.username(invite.getUsername());
        }
        userEntityBuilder.password(passwordEncoder.encode(password));
        userEntityBuilder.defaultPasswordReset(true);
        log.info(
                "Creating user from invite: [InviteUuid: {}, User: {}]",
                invite.getUuid(),
                userEntityBuilder.build());
        UserEntity createdUser = userEntityService.saveUserEntity(userEntityBuilder.build());
        userInviteRepository.deleteById(invite.getUuid());
        return createdUser;
    }
}
