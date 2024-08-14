package com.example.myproject.controllertest;
import com.example.myproject.controller.NutritionController;
import com.example.myproject.dto.NutritionDto;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.service.NutritionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class NutritionControllerTest {


        @Mock
        private NutritionService nutritionService;

        @InjectMocks
        private NutritionController nutritionController;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testCreateSuccess() {
            NutritionDto dto = new NutritionDto();
            NutritionDto createdDto = new NutritionDto();
            createdDto.setId(1L);
            createdDto.setName("Apple");
            createdDto.setCategory("Fruit");
            createdDto.setCalories(95);
            createdDto.setNutrients("Vitamin C, Fiber");

            when(nutritionService.saveNutritionDetails(any(NutritionDto.class))).thenReturn(createdDto);

            ResponseEntity<NutritionDto> response = nutritionController.createNutrition(dto);
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(createdDto, response.getBody());
        }

        @Test
        public void testGetAllSuccess() {
            NutritionDto dto1 = new NutritionDto();
            NutritionDto dto2 = new NutritionDto();
            List<NutritionDto> dtos = Arrays.asList(dto1, dto2);

            when(nutritionService.getAllNutritionDetails()).thenReturn(dtos);

            ResponseEntity<List<NutritionDto>> response = nutritionController.getAllNutrition();
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(dtos, response.getBody());
        }

        @Test
        public void testGetByIdSuccess() throws UserNotFoundException {
            long id = 10;
            NutritionDto dto = new NutritionDto();
            dto.setId(id);
            dto.setName("Apple");
            dto.setCategory("Fruit");
            dto.setCalories(95);
            dto.setNutrients("Vitamin C, Fiber");

            when(nutritionService.getNutritionDetailsById(id)).thenReturn(dto);

            ResponseEntity<NutritionDto> response = nutritionController.getNutritionById(id);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(dto, response.getBody());
        }

        @Test
        public void testGetByIdNotFound() throws UserNotFoundException {
            long id = 10;

            when(nutritionService.getNutritionDetailsById(id)).thenThrow(new UserNotFoundException("User Not Found"));

            try {
                nutritionController.getNutritionById(id);
            } catch (UserNotFoundException e) {
                assertEquals("User Not Found", e.getMessage());
            }
        }

        @Test
        public void testUpdateSuccess() throws UserNotFoundException {
            long id = 10;
            NutritionDto dto = new NutritionDto();
            dto.setId(id);
            dto.setName("Apple");
            dto.setCategory("Fruit");
            dto.setCalories(95);
            dto.setNutrients("Vitamin C, Fiber");

            when(nutritionService.saveNutritionDetails(any(NutritionDto.class))).thenReturn(dto);

            ResponseEntity<NutritionDto> response = nutritionController.updateNutrition(id, dto);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(dto, response.getBody());
        }

        @Test
        public void testUpdateNotFound() throws UserNotFoundException {
            long id = 10;
            NutritionDto dto = new NutritionDto();
            dto.setId(id);
            dto.setName("Apple");
            dto.setCategory("Fruit");
            dto.setCalories(95);
            dto.setNutrients("Vitamin C, Fiber");

            when(nutritionService.getNutritionDetailsById(id)).thenThrow(new UserNotFoundException("User Not Found"));

            try {
                nutritionController.updateNutrition(id, dto);
            } catch (UserNotFoundException e) {
                assertEquals("User Not Found", e.getMessage());
            }
        }

        @Test
        public void testDeleteSuccess() throws UserNotFoundException {
            long id = 10;

            ResponseEntity<String> response = nutritionController.deleteNutrition(id);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Deleted nutrition " + id, response.getBody());
            verify(nutritionService, times(1)).deleteNutrionDetails(id);
        }

        @Test
        public void testDeleteNotFound() throws UserNotFoundException {
            long id = 10;

            doThrow(new UserNotFoundException("User Not Found")).when(nutritionService).deleteNutrionDetails(id);

            try {
                nutritionController.deleteNutrition(id);
            } catch (UserNotFoundException e) {
                assertEquals("User Not Found", e.getMessage());
            }
        }
    }

