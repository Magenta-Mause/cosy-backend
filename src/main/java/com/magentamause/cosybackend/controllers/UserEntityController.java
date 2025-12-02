package com.magentamause.cosybackend.controllers;


import com.magentamause.cosybackend.entities.UserEntity;
import com.magentamause.cosybackend.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user-entity")
public class UserEntityController {

    private final UserEntityService userEntityService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUserEntities() {
        return ResponseEntity.ok(userEntityService.getAllUsers());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserEntity> getUserEntity(@PathVariable String uuid) {
        return ResponseEntity.ok(userEntityService.getUserByUuid(uuid));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUserEntity(@PathVariable String uuid) {
        userEntityService.deleteUserByUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}
