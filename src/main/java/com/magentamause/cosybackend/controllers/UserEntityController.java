package com.magentamause.cosybackend.controllers;

import com.magentamause.cosybackend.DTOs.UserEntityDTO;
import com.magentamause.cosybackend.services.UserEntityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user-entity")
public class UserEntityController {

    private final UserEntityService userEntityService;

    @GetMapping
    public ResponseEntity<List<UserEntityDTO>> getAllUserEntities() {
        return ResponseEntity.ok(userEntityService.getAllUsers());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserEntityDTO> getUserEntity(@PathVariable String uuid) {
        return ResponseEntity.ok(userEntityService.getUserByUuid(uuid));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUserEntity(@PathVariable String uuid) {
        userEntityService.deleteUserByUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}
