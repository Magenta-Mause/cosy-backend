package com.magentamause.cosybackend.repositories;

import com.magentamause.cosybackend.entities.UserInviteEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInviteRepository extends JpaRepository<UserInviteEntity, String> {
    Optional<UserInviteEntity> findByInviteKey(String inviteKey);
}
