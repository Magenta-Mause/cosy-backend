package com.magentamause.cosybackend.controllers;

import com.magentamause.cosybackend.services.GameServerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game-server")
public class GameServerController {

    private final GameServerService gameServerService;

    @GetMapping("/{serviceName}")
    public String getServiceInfo(@PathVariable String serviceName) {
        return gameServerService.getStatus(serviceName);
    }

    @PostMapping("/{serviceName}/start")
    public List<Integer> startService(@PathVariable String serviceName) {
        return gameServerService.startServer(serviceName);
    }

    @PostMapping("/{serviceName}/stop")
    public void stopService(@PathVariable String serviceName) {
        gameServerService.stopServer(serviceName);
    }
}
