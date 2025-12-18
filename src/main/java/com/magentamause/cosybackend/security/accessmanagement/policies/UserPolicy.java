package com.magentamause.cosybackend.security.accessmanagement.policies;

import com.magentamause.cosybackend.entities.UserEntity;
import com.magentamause.cosybackend.security.accessmanagement.Action;
import com.magentamause.cosybackend.security.accessmanagement.Resource;
import com.magentamause.cosybackend.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPolicy implements AccessPolicy {

    private final UserEntityService userEntityService;

    @Override
    public Resource resource() {
        return Resource.USER;
    }

    @Override
    public boolean can(UserEntity user, Action action, Object referenceId) {
        if (user.getRole().equals(UserEntity.Role.ADMIN) && action.equals(Action.DELETE)) {
            UserEntity userEntity = userEntityService.getUserByUuid(user.getUuid());
            if (userEntity.getRole().equals(UserEntity.Role.OWNER)) {
                return false;
            }
        }
        if (user.getRole().equals(UserEntity.Role.OWNER)
                || user.getRole().equals(UserEntity.Role.ADMIN)) {
            return true;
        }

        return switch (action) {
            case READ, DELETE -> user.getUuid().equals(referenceId);
            case UPDATE, CREATE -> false;
        };
    }
}
