package com.magentamause.cosybackend.services.dummydata;

import com.magentamause.cosybackend.entities.UserEntity;
import com.magentamause.cosybackend.repositories.DummyInstantiatedPropertiesRepository;
import com.magentamause.cosybackend.entities.DummyInstantiatedProperties;
import com.magentamause.cosybackend.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEntityDummyService {

    private final DummyInstantiatedPropertiesRepository dummyInstantiatedPropertiesRepository;
    private final UserEntityService userEntityService;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeAdminUserEntity() {
        if (dummyInstantiatedPropertiesRepository.findById("admin-user-entity").isPresent()) {
            log.info("Admin user entity already exists");
            return;
        }

        UserEntity adminUser = UserEntity.builder()
                .username("admin")
                .password("admin")
                .defaultPasswordReset(false)
                .role(UserEntity.Role.OWNER)
                .build();

        userEntityService.saveUserEntity(adminUser);

        dummyInstantiatedPropertiesRepository.save(
                DummyInstantiatedProperties.builder()
                        .key("admin-user-entity")
                        .build());

        log.info("Admin user entity initialized");
    }
}
