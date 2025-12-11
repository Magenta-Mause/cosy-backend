package com.magentamause.cosybackend.controllers;

import com.magentamause.cosybackend.DTOs.actiondtos.GameServerCreationDto;
import com.magentamause.cosybackend.DTOs.entitydtos.GameServerDto;
import com.magentamause.cosybackend.entities.GameServerConfigurationEntity;
import com.magentamause.cosybackend.entities.UserEntity;
import com.magentamause.cosybackend.entities.utility.VolumeMountConfiguration;
import com.magentamause.cosybackend.services.GameServerConfigurationService;
import com.magentamause.cosybackend.services.SecurityContextService;
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
    private final SecurityContextService securityContextService;

    @GetMapping
    public ResponseEntity<List<GameServerDto>> getAllGameServers() {
        List<GameServerDto> dtos =
                gameServerConfigurationService.getAllGameServers().stream()
                        .map(GameServerConfigurationEntity::toDto)
                        .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<GameServerDto> getGameServerById(@PathVariable String uuid) {
        GameServerConfigurationEntity entity =
                gameServerConfigurationService.getGameServerById(uuid);
        return ResponseEntity.ok(entity.toDto());
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteGameServerById(@PathVariable String uuid) {
        gameServerConfigurationService.deleteGameServerById(uuid);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<GameServerDto> createGameServer(
            @Valid @RequestBody GameServerCreationDto gameServerCreationDto) {
        UserEntity user = securityContextService.getUser();

        GameServerConfigurationEntity createdGameServer =
                GameServerConfigurationEntity.builder()
                        .owner(user)
                        .gameUuid(gameServerCreationDto.getGameUuid())
                        .serverName(gameServerCreationDto.getServerName())
                        .template(gameServerCreationDto.getTemplate())
                        .dockerImageName(gameServerCreationDto.getDockerImageName())
                        .dockerImageTag(gameServerCreationDto.getDockerImageTag())
                        .dockerExecutionCommand(gameServerCreationDto.getExecutionCommand())
                        .environmentVariables(gameServerCreationDto.getEnvironmentVariables())
                        .volumeMounts(
                                gameServerCreationDto.getVolumeMounts().stream()
                                        .map(VolumeMountConfiguration::fromDto)
                                        .toList())
                        .portMappings(gameServerCreationDto.getPortMappings())
                        .build();

        gameServerConfigurationService.saveGameServer(createdGameServer);
        return ResponseEntity.status(201).body(createdGameServer.toDto());
    }
}
