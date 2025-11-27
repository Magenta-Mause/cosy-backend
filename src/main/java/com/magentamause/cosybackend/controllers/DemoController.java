package com.magentamause.cosybackend.controllers;

import com.magentamause.cosybackend.entities.DemoEntity;
import com.magentamause.cosybackend.services.DemoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/demo")
public class DemoController {

    private final DemoService demoService;

    @GetMapping
    public ResponseEntity<List<DemoEntity>> getAllDemoEntities() {
        return ResponseEntity.ok(demoService.getAllDemoEntities());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<DemoEntity> getDemoEntityById(@PathVariable String uuid) {
        return ResponseEntity.ok(demoService.getDemoEntityById(uuid));
    }

    @PostMapping
    public ResponseEntity<DemoEntity> saveDemoEntity(@RequestBody DemoEntity demoEntity) {
        return ResponseEntity.ok(demoService.saveDemoEntity(demoEntity));
    }

    @DeleteMapping("/{uuid}")
    public void deleteDemoEntityById(@PathVariable String uuid) {
        demoService.deleteDemoEntityById(uuid);
    }
}
