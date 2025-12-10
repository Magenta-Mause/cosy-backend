package com.magentamause.cosybackend.controllers;

import com.magentamause.cosybackend.dtos.entitydtos.UserEntityDto;
import com.magentamause.cosybackend.entities.UserEntity;
import com.magentamause.cosybackend.services.UserEntityService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user-entity")
public class UserEntityController {

    private final UserEntityService userEntityService;

    @GetMapping
    public ResponseEntity<List<UserEntityDto>> getAllUserEntities() {
        List<UserEntity> users = userEntityService.getAllUsers();
        List<UserEntityDto> userDTOs =
                users.stream().map(userEntityService::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserEntityDto> getUserEntity(@PathVariable String uuid) {
        UserEntity user = userEntityService.getUserByUuid(uuid);
        return ResponseEntity.ok(userEntityService.convertToDTO(user));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUserEntity(@PathVariable String uuid) {
        userEntityService.deleteUserByUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}
