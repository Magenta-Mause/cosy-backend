package com.magentamause.cosybackend.controllers;

import com.magentamause.cosybackend.DTOs.actiondtos.UserCreationDto;
import com.magentamause.cosybackend.DTOs.actiondtos.UserInviteCreationDto;
import com.magentamause.cosybackend.DTOs.entitydtos.UserEntityDto;
import com.magentamause.cosybackend.DTOs.entitydtos.UserInviteDto;
import com.magentamause.cosybackend.entities.UserEntity;
import com.magentamause.cosybackend.entities.UserInviteEntity;
import com.magentamause.cosybackend.services.SecurityContextService;
import com.magentamause.cosybackend.services.UserEntityService;
import com.magentamause.cosybackend.services.UserInviteService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-invites")
public class UserInviteController {

    private final UserInviteService userInviteService;
    private final SecurityContextService securityContextService;
    private final UserEntityService userEntityService;

    @GetMapping
    public ResponseEntity<List<UserInviteDto>> getAllUserInvites() {
        securityContextService.assertUserHasAccessOfRole(UserEntity.Role.OWNER);
        return ResponseEntity.ok(
                userInviteService.getAllInvites().stream()
                        .map(UserInviteEntity::convertToDto)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{secretKey}")
    public ResponseEntity<UserInviteDto> getUserInvite(
            @PathVariable("secretKey") String secretKey) {
        return ResponseEntity.ok(userInviteService.getInviteBySecretKey(secretKey).convertToDto());
    }

    @PostMapping
    public ResponseEntity<UserInviteDto> createInvite(
            @Valid @RequestBody UserInviteCreationDto userInviteCreationDto) {
        securityContextService.assertUserHasAccessOfRole(UserEntity.Role.OWNER);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        userInviteService
                                .createInvite(
                                        securityContextService.getUserId(),
                                        userInviteCreationDto.getUsername())
                                .convertToDto());
    }

    @PostMapping("/use/{secretKey}")
    public ResponseEntity<UserEntityDto> useInvite(
            @PathVariable("secretKey") String secretKey, @Valid @RequestBody UserCreationDto user) {
        UserEntity createdUser =
                userInviteService.useInvite(secretKey, user.getUsername(), user.getPassword());
        return ResponseEntity.ok(userEntityService.convertToDTO(createdUser));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> revokeInvite(@PathVariable String uuid) {
        userInviteService.revokeInvite(uuid);
        return ResponseEntity.noContent().build();
    }
}
