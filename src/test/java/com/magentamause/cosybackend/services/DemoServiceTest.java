package com.magentamause.cosybackend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.magentamause.cosybackend.entities.DemoEntity;
import com.magentamause.cosybackend.repositories.DemoEntityRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DemoServiceTest {

    @Mock private DemoEntityRepository demoEntityRepository;

    @InjectMocks private DemoService demoService;

    private DemoEntity testEntity1;
    private DemoEntity testEntity2;

    @BeforeEach
    void setUp() {
        testEntity1 = DemoEntity.builder().uuid("uuid-1").name("Test Entity 1").build();
        testEntity2 = DemoEntity.builder().uuid("uuid-2").name("Test Entity 2").build();
    }

    @Test
    void getAllDemoEntities_shouldReturnAllEntities() {
        List<DemoEntity> expectedEntities = Arrays.asList(testEntity1, testEntity2);
        when(demoEntityRepository.findAll()).thenReturn(expectedEntities);

        List<DemoEntity> result = demoService.getAllDemoEntities();

        assertEquals(expectedEntities, result);
        verify(demoEntityRepository, times(1)).findAll();
    }

    @Test
    void getAllDemoEntities_shouldReturnEmptyListWhenNoEntities() {
        when(demoEntityRepository.findAll()).thenReturn(List.of());

        List<DemoEntity> result = demoService.getAllDemoEntities();

        assertTrue(result.isEmpty());
        verify(demoEntityRepository, times(1)).findAll();
    }

    @Test
    void getDemoEntityById_shouldReturnEntityWhenExists() {
        when(demoEntityRepository.findById("uuid-1")).thenReturn(Optional.of(testEntity1));

        DemoEntity result = demoService.getDemoEntityById("uuid-1");

        assertNotNull(result);
        assertEquals("uuid-1", result.getUuid());
        assertEquals("Test Entity 1", result.getName());
        verify(demoEntityRepository, times(1)).findById("uuid-1");
    }

    @Test
    void getDemoEntityById_shouldReturnNullWhenNotExists() {
        when(demoEntityRepository.findById("non-existent")).thenReturn(Optional.empty());

        DemoEntity result = demoService.getDemoEntityById("non-existent");

        assertNull(result);
        verify(demoEntityRepository, times(1)).findById("non-existent");
    }

    @Test
    void saveDemoEntity_shouldSetUuidToNullBeforeSaving() {
        DemoEntity entityToSave =
                DemoEntity.builder().uuid("existing-uuid").name("New Entity").build();
        DemoEntity savedEntity =
                DemoEntity.builder().uuid("generated-uuid").name("New Entity").build();

        when(demoEntityRepository.save(any(DemoEntity.class))).thenReturn(savedEntity);

        DemoEntity result = demoService.saveDemoEntity(entityToSave);

        assertNull(entityToSave.getUuid());
        assertEquals(savedEntity, result);
        verify(demoEntityRepository, times(1)).save(entityToSave);
    }

    @Test
    void saveDemoEntity_shouldSaveEntityWithNullUuid() {
        DemoEntity entityToSave = DemoEntity.builder().uuid(null).name("New Entity").build();
        DemoEntity savedEntity =
                DemoEntity.builder().uuid("generated-uuid").name("New Entity").build();

        when(demoEntityRepository.save(any(DemoEntity.class))).thenReturn(savedEntity);

        DemoEntity result = demoService.saveDemoEntity(entityToSave);

        assertEquals(savedEntity, result);
        verify(demoEntityRepository, times(1)).save(entityToSave);
    }

    @Test
    void deleteDemoEntityById_shouldDeleteEntity() {
        doNothing().when(demoEntityRepository).deleteById("uuid-1");

        demoService.deleteDemoEntityById("uuid-1");

        verify(demoEntityRepository, times(1)).deleteById("uuid-1");
    }
}
