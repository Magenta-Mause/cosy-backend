package com.magentamause.cosybackend.controllers;

import com.magentamause.cosybackend.DTOs.actiondtos.GameServerCreationDto;
import com.magentamause.cosybackend.entities.GameServerConfigurationEntity;
import com.magentamause.cosybackend.entities.utility.VolumeMountConfiguration;
import com.magentamause.cosybackend.services.AuthorizationService;
import com.magentamause.cosybackend.services.GameServerConfigurationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game-server-configurations")
public class GameServerConfigurationController {

    private final GameServerConfigurationService gameServerConfigurationService;
    private final AuthorizationService authorizationService;

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
        String userId = authorizationService.getCurrentUserId();

        GameServerConfigurationEntity createdGameServer =
                GameServerConfigurationEntity.builder()
                        .ownerId(userId)
                        .gameUuid(gameServerCreationDto.getGameUuid())
                        .serverName(gameServerCreationDto.getServerName())
                        .template(gameServerCreationDto.getTemplate())
                        .dockerImageName(gameServerCreationDto.getDockerImageName())
                        .dockerImageTag(gameServerCreationDto.getDockerImageTag())
                        .dockerExecutionCommand(gameServerCreationDto.getExecutionCommand())
                        .environmentVariables(gameServerCreationDto.getEnvironmentVariables())
                        .volumeMounts(
                                gameServerCreationDto.getVolumeMounts() != null
                                        ? gameServerCreationDto.getVolumeMounts().stream()
                                                .map(
                                                        vmc ->
                                                                VolumeMountConfiguration.builder()
                                                                        .hostPath(vmc.getHostPath())
                                                                        .containerPath(
                                                                                vmc
                                                                                        .getContainerPath())
                                                                        .build())
                                                .toList()
                                        : null)
                        .portMappings(gameServerCreationDto.getPortMappings())
                        .build();

        gameServerConfigurationService.saveGameServer(createdGameServer);
        return ResponseEntity.status(201).body(createdGameServer);
    }
}
