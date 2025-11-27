package com.magentamause.cosybackend.services;

import com.magentamause.cosybackend.entities.DemoEntity;
import com.magentamause.cosybackend.repositories.DemoEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DemoServiceTest {

	@Mock
	private DemoEntityRepository demoEntityRepository;

	@InjectMocks
	private DemoService demoService;

	@Test
	void getAllDemoEntities_shouldReturnList() {
		DemoEntity entity = new DemoEntity();
		when(demoEntityRepository.findAll()).thenReturn(Collections.singletonList(entity));

		List<DemoEntity> result = demoService.getAllDemoEntities();

		assertEquals(1, result.size());
		verify(demoEntityRepository).findAll();
	}

	@Test
	void getDemoEntityById_shouldReturnEntity_whenFound() {
		String id = "test-id";
		DemoEntity entity = new DemoEntity();
		when(demoEntityRepository.findById(id)).thenReturn(Optional.of(entity));

		DemoEntity result = demoService.getDemoEntityById(id);

		assertNotNull(result);
		verify(demoEntityRepository).findById(id);
	}

	@Test
	void getDemoEntityById_shouldThrowException_whenNotFound() {
		String id = "test-id";
		when(demoEntityRepository.findById(id)).thenReturn(Optional.empty());

		ResponseStatusException exception =
				assertThrows(
						ResponseStatusException.class,
						() -> {
							demoService.getDemoEntityById(id);
						});

		assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
	}

	@Test
	void saveDemoEntity_shouldResetUuidAndSave() {
		DemoEntity entity = new DemoEntity();
		entity.setUuid("old-uuid");

		when(demoEntityRepository.save(any(DemoEntity.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		DemoEntity result = demoService.saveDemoEntity(entity);

		assertNull(result.getUuid());
		verify(demoEntityRepository).save(entity);
	}

	@Test
	void deleteDemoEntityById_shouldDelete_whenExists() {
		String id = "test-id";
		when(demoEntityRepository.existsById(id)).thenReturn(true);

		demoService.deleteDemoEntityById(id);

		verify(demoEntityRepository).deleteById(id);
	}

	@Test
	void deleteDemoEntityById_shouldThrowException_whenNotExists() {
		String id = "test-id";
		when(demoEntityRepository.existsById(id)).thenReturn(false);

		ResponseStatusException exception =
				assertThrows(
						ResponseStatusException.class,
						() -> {
							demoService.deleteDemoEntityById(id);
						});

		assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
		verify(demoEntityRepository, never()).deleteById(any());
	}
}
