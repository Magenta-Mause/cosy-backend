package com.magentamause.cosybackend.services;

import com.magentamause.cosybackend.entities.DemoEntity;
import com.magentamause.cosybackend.repositories.DemoEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DemoService {

	private final DemoEntityRepository demoEntityRepository;

	public List<DemoEntity> getAllDemoEntities() {
		return demoEntityRepository.findAll();
	}

	public DemoEntity getDemoEntityById(String id) {
		return demoEntityRepository.findById(id).orElse(null);
	}

	public DemoEntity saveDemoEntity(DemoEntity demoEntity) {
		demoEntity.setUuid(null);
		return demoEntityRepository.save(demoEntity);
	}

	public void deleteDemoEntityById(String id) {
		demoEntityRepository.deleteById(id);
	}
}
