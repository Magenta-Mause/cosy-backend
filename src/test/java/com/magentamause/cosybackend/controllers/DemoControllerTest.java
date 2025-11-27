package com.magentamause.cosybackend.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magentamause.cosybackend.configs.UtilConfig;
import com.magentamause.cosybackend.entities.DemoEntity;
import com.magentamause.cosybackend.services.DemoService;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DemoController.class)
@Import(UtilConfig.class)
class DemoControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockitoBean private DemoService demoService;

    @Autowired private ObjectMapper objectMapper;

    @Test
    void getAllDemoEntities_shouldReturnList() throws Exception {
        DemoEntity entity = DemoEntity.builder().uuid("test-uuid").name("test-name").build();
        given(demoService.getAllDemoEntities()).willReturn(Collections.singletonList(entity));

        mockMvc.perform(get("/demo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uuid").value("test-uuid"))
                .andExpect(jsonPath("$[0].name").value("test-name"));
    }

    @Test
    void getDemoEntityById_shouldReturnEntity() throws Exception {
        String id = "test-uuid";
        DemoEntity entity = DemoEntity.builder().uuid(id).name("test-name").build();
        given(demoService.getDemoEntityById(id)).willReturn(entity);

        mockMvc.perform(get("/demo/{uuid}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(id))
                .andExpect(jsonPath("$.name").value("test-name"));
    }

    @Test
    void saveDemoEntity_shouldReturnSavedEntity() throws Exception {
        DemoEntity inputEntity = DemoEntity.builder().name("test-name").build();
        DemoEntity savedEntity =
                DemoEntity.builder().uuid("generated-uuid").name("test-name").build();

        given(demoService.saveDemoEntity(any(DemoEntity.class))).willReturn(savedEntity);

        mockMvc.perform(
                        post("/demo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(inputEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value("generated-uuid"))
                .andExpect(jsonPath("$.name").value("test-name"));
    }

    @Test
    void deleteDemoEntityById_shouldReturnOk() throws Exception {
        String id = "test-uuid";

        mockMvc.perform(delete("/demo/{uuid}", id)).andExpect(status().isOk());

        verify(demoService).deleteDemoEntityById(id);
    }
}
