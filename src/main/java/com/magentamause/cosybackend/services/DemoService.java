package com.magentamause.cosybackend.services;

import com.magentamause.cosybackend.entities.DemoEntity;
import com.magentamause.cosybackend.exceptions.EntityNotFoundException;
import com.magentamause.cosybackend.repositories.DemoEntityRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemoService {

    private final DemoEntityRepository demoEntityRepository;

    public List<DemoEntity> getAllDemoEntities() {
        return demoEntityRepository.findAll();
    }

    public DemoEntity getDemoEntityById(String id) {
        return demoEntityRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("DemoEntity not found with id: " + id));
    }

    public DemoEntity saveDemoEntity(DemoEntity demoEntity) {
        demoEntity.setUuid(null);
        return demoEntityRepository.save(demoEntity);
    }

    public void deleteDemoEntityById(String id) {
        demoEntityRepository.deleteById(id);
    }
}
