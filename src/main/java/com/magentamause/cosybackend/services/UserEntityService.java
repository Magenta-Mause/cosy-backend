package com.magentamause.cosybackend.services;

import com.magentamause.cosybackend.DTOs.UserEntityDTO;
import com.magentamause.cosybackend.entities.UserEntity;
import com.magentamause.cosybackend.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;

    public List<UserEntityDTO> getAllUsers() {
        List<UserEntity> users  = userEntityRepository.findAll();

        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserEntityDTO getUserByUuid(String uuid) {
        UserEntity user = userEntityRepository.findById(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User with uuid " + uuid + " not found")
        );
        return convertToDTO(user);
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

    private UserEntityDTO convertToDTO(UserEntity user) {
        UserEntityDTO dto = new UserEntityDTO();
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        return dto;
    }
}
