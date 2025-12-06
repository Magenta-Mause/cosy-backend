package com.magentamause.cosybackend.services;

import com.magentamause.cosybackend.entities.UserEntity;
import com.magentamause.cosybackend.entities.UserInviteEntity;
import com.magentamause.cosybackend.repositories.UserInviteRepository;
import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInviteService {
    private static final char[] POSSIBLE_CHARACTERS =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final int KEY_LENGTH = 16;
    private final UserInviteRepository userInviteRepository;
    private final UserEntityService userEntityService;
    private final PasswordEncoder passwordEncoder;

    public List<UserInviteEntity> getAllInvites() {
        return userInviteRepository.findAll();
    }

    public void revokeInvite(String inviteUuid) {
        // Check existence first to provide clear feedback
        getInviteByUuid(inviteUuid); // throws 404 if not found
        userInviteRepository.deleteById(inviteUuid);
    }

    public UserInviteEntity createInvite(String ownerCreationId, String username) {
        UserInviteEntity invite =
                UserInviteEntity.builder()
                        .invitedBy(userEntityService.getUserByUuid(ownerCreationId))
                        .secretKey(generateRandomKey())
                        .username(username)
                        .build();

        return userInviteRepository.save(invite);
    }

    public UserInviteEntity getInviteBySecretKey(String secretToken) {
        return userInviteRepository
                .findBySecretKey(secretToken)
                .orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND, "Invite not found"));
    }

    public UserInviteEntity getInviteByUuid(String inviteUuid) {
        return userInviteRepository
                .findById(inviteUuid)
                .orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND, "Invite not found"));
    }

    @Transactional
    public UserEntity useInvite(String secretKey, String username, String password) {
        UserInviteEntity invite = userInviteRepository.findBySecretKeyLocked(secretKey)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invite not found"));
        UserEntity.UserEntityBuilder userBuilder =
                UserEntity.builder()
                        .role(UserEntity.Role.QUOTA_USER)
                        .password(passwordEncoder.encode(password))
                        .defaultPasswordReset(true);
        if (Objects.isNull(invite.getUsername())) {
            userBuilder.username(username);
        } else {
            userBuilder.username(invite.getUsername());
        }
        UserEntity user = userEntityService.saveUserEntity(userBuilder.build());
        userInviteRepository.delete(invite); // Delete the invite after use
        log.info("Invite [{}] used for user {}", invite.getUuid(), user.getUsername());
        return user;
    }

    private String generateRandomKey() {
        SecureRandom random = new SecureRandom();
        return random.ints(0, POSSIBLE_CHARACTERS.length)
                .limit(KEY_LENGTH)
                .map(i -> POSSIBLE_CHARACTERS[i])
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
