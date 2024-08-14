package com.example.myproject.servicetest;
import com.example.myproject.dto.NutritionDto;
import com.example.myproject.entity.NutritionEntity;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.repositary.NutritionRepository;
import com.example.myproject.service.NutritionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NutritionServiceTest {

        @Mock
        private NutritionRepository nutritionRepository;

        @InjectMocks
        private NutritionService nutritionService;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testDtoToEntity() {
            NutritionDto dto = new NutritionDto();
            dto.setId(1L);
            dto.setName("Apple");
            dto.setCategory("Fruit");
            dto.setCalories(95);
            dto.setNutrients("Vitamin C, Fiber");

            NutritionEntity entity = nutritionService.dtoToEntity(dto);

            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getName(), entity.getName());
            assertEquals(dto.getCategory(), entity.getCategory());
            assertEquals(dto.getCalories(), entity.getCalories());
            assertEquals(dto.getNutrients(), entity.getNutrients());
        }

        @Test
        public void testEntityToDto() {
            NutritionEntity entity = new NutritionEntity();
            entity.setId(1L);
            entity.setName("Apple");
            entity.setCategory("Fruit");
            entity.setCalories(95);
            entity.setNutrients("Vitamin C, Fiber");

            NutritionDto dto = nutritionService.entityToDto(entity);

            assertEquals(entity.getId(), dto.getId());
            assertEquals(entity.getName(), dto.getName());
            assertEquals(entity.getCategory(), dto.getCategory());
            assertEquals(entity.getCalories(), dto.getCalories());
            assertEquals(entity.getNutrients(), dto.getNutrients());
        }

        @Test
        public void testSaveNutritionDetails() {
            NutritionDto dto = new NutritionDto();
            dto.setId(1L);
            dto.setName("Apple");
            dto.setCategory("Fruit");
            dto.setCalories(95);
            dto.setNutrients("Vitamin C, Fiber");

            NutritionEntity entity = new NutritionEntity();
            entity.setId(1L);
            entity.setName(dto.getName());
            entity.setCategory(dto.getCategory());
            entity.setCalories(dto.getCalories());
            entity.setNutrients(dto.getNutrients());

            when(nutritionRepository.save(any(NutritionEntity.class))).thenReturn(entity);

            NutritionDto savedDto = nutritionService.saveNutritionDetails(dto);

            assertEquals(dto.getId(), savedDto.getId());
            assertEquals(dto.getName(), savedDto.getName());
            assertEquals(dto.getCategory(), savedDto.getCategory());
            assertEquals(dto.getCalories(), savedDto.getCalories());
            assertEquals(dto.getNutrients(), savedDto.getNutrients());
        }

        @Test
        public void testGetAllNutritionDetails() {
            List<NutritionEntity> entities = new ArrayList<>();
            NutritionEntity entity1 = new NutritionEntity();
            entity1.setId(1L);
            entity1.setName("Apple");
            entity1.setCategory("Fruit");
            entity1.setCalories(95);
            entity1.setNutrients("Vitamin C, Fiber");
            entities.add(entity1);

            NutritionEntity entity2 = new NutritionEntity();
            entity2.setId(2L);
            entity2.setName("Banana");
            entity2.setCategory("Fruit");
            entity2.setCalories(105);
            entity2.setNutrients("Potassium, Vitamin B6");
            entities.add(entity2);

            when(nutritionRepository.findAll()).thenReturn(entities);

            List<NutritionDto> dtos = nutritionService.getAllNutritionDetails();

            assertEquals(2, dtos.size());
            assertEquals(entities.get(0).getName(), dtos.get(0).getName());
            assertEquals(entities.get(1).getName(), dtos.get(1).getName());
        }

        @Test
        public void testGetNutritionDetailsById() throws UserNotFoundException {
            Long id = 1L;
            NutritionEntity entity = new NutritionEntity();
            entity.setId(id);
            entity.setName("Apple");
            entity.setCategory("Fruit");
            entity.setCalories(95);
            entity.setNutrients("Vitamin C, Fiber");

            when(nutritionRepository.findById(id)).thenReturn(Optional.of(entity));

            NutritionDto dto = nutritionService.getNutritionDetailsById(id);

            assertNotNull(dto);
            assertEquals(entity.getId(), dto.getId());
            assertEquals(entity.getName(), dto.getName());
            assertEquals(entity.getCategory(), dto.getCategory());
            assertEquals(entity.getCalories(), dto.getCalories());
            assertEquals(entity.getNutrients(), dto.getNutrients());
        }

        @Test
        public void testGetNutritionDetailsById_NotFound() {
            Long id = 1L;

            when(nutritionRepository.findById(id)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> nutritionService.getNutritionDetailsById(id));
        }

        @Test
        public void testDeleteNutritionDetails_Success() throws UserNotFoundException {
            Long id = 1L;
            NutritionEntity entity = new NutritionEntity();
            entity.setId(id);
            entity.setName("Apple");
            entity.setCategory("Fruit");
            entity.setCalories(95);
            entity.setNutrients("Vitamin C, Fiber");

            when(nutritionRepository.findById(id)).thenReturn(Optional.of(entity));

            nutritionService.deleteNutrionDetails(id);

            verify(nutritionRepository, times(1)).deleteById(id);
        }

        @Test
        public void testDeleteNutritionDetails_NotFound() {
            Long id = 1L;

            when(nutritionRepository.findById(id)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> nutritionService.deleteNutrionDetails(id));
            verify(nutritionRepository, never()).deleteById(id);
        }
    }

