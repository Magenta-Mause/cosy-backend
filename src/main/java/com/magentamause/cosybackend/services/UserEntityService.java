package com.magentamause.cosybackend.services;

import com.magentamause.cosybackend.entities.UserEntity;
import com.magentamause.cosybackend.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;

    public List<UserEntity> getAllUsers() {
        return userEntityRepository.findAll();
    }

    public UserEntity getUserByUuid(String uuid) {
        return userEntityRepository.findById(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User with uuid " + uuid + " not found")
        );
    }

    public void saveUserEntity(UserEntity userEntity) {
        userEntityRepository.save(userEntity);
    }

    public void deleteUserByUuid(String uuid) {
        UserEntity user = userEntityRepository.findById(uuid).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User with uuid " + uuid + " not found"
                )
        );
        userEntityRepository.delete(user);
    }
}
