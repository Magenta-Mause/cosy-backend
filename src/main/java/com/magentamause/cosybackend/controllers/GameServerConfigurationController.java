package com.magentamause.cosybackend.controllers;

import com.magentamause.cosybackend.entities.GameServerConfigurationEntity;
import com.magentamause.cosybackend.services.GameServerConfigurationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
}
