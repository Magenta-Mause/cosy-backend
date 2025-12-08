package com.magentamause.cosybackend.controllers;

import com.magentamause.cosybackend.DTOs.GameServerCreationDto;
import com.magentamause.cosybackend.entities.GameServerConfigurationEntity;
import com.magentamause.cosybackend.entities.utility.PortMapping;
import com.magentamause.cosybackend.services.GameServerConfigurationService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game-server-configurations")
public class GameServerConfigurationController {

    private final GameServerConfigurationService gameServerConfigurationService;

    @GetMapping
    public ResponseEntity<List<GameServerConfigurationEntity>> getAllGameServers() {
        return ResponseEntity.ok(gameServerConfigurationService.getAllGameServers());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<GameServerConfigurationEntity> getGameServerById(
            @PathVariable String uuid) {
        return ResponseEntity.ok(gameServerConfigurationService.getGameServerById(uuid));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteGameServerById(@PathVariable String uuid) {
        gameServerConfigurationService.deleteGameServerById(uuid);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<GameServerConfigurationEntity> createGameServer(
            @Valid @RequestBody GameServerCreationDto gameServerCreationDto) {
        String userId =
                (String) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();

        GameServerConfigurationEntity createdGameServer =
                GameServerConfigurationEntity.builder()
                        .ownerId(userId)
                        .gameUuid(gameServerCreationDto.getGameUuid())
                        .serverName(gameServerCreationDto.getServerName())
                        .template(gameServerCreationDto.getTemplate())
                        .dockerImageName(gameServerCreationDto.getDockerImageName())
                        .dockerImageTag(gameServerCreationDto.getDockerImageTag())
                        .dockerExecutionCommand(
                                List.of(gameServerCreationDto.getExecutionCommand().split(" ")))
                        .environmentVariables(gameServerCreationDto.getEnvironmentVariables())
                        .volumeMounts(gameServerCreationDto.getVolumeMounts())
                        .portMappings(
                                gameServerCreationDto.getPortMappings().stream()
                                        .map(
                                                portMapping ->
                                                        PortMapping.builder()
                                                                .instancePort(
                                                                        portMapping
                                                                                .getInstancePort())
                                                                .containerPort(
                                                                        portMapping
                                                                                .getContainerPort())
                                                                .build())
                                        .toList())
                        .build();

        gameServerConfigurationService.saveGameServer(createdGameServer);
        return ResponseEntity.status(201).body(createdGameServer);
    }
}
