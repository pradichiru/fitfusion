package com.example.myproject.servicetest;

import com.example.myproject.dto.ExerciseDto;
import com.example.myproject.entity.ExerciseEntity;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.repositary.ExerciseRepository;
import com.example.myproject.service.ExerciseService;
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
public class ExerciseServiceTest {

        @Mock
        private ExerciseRepository exerciseRepository;

        @InjectMocks
        private ExerciseService exerciseService;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testDtoToEntity() {
            ExerciseDto dto = new ExerciseDto();
            dto.setId(1L);
            dto.setTitle("Sample Title");
            dto.setDescription("Sample Description");
            dto.setType("Sample Type");

            ExerciseEntity entity = exerciseService.dtoToEntity(dto);

            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getTitle(), entity.getTitle());
            assertEquals(dto.getDescription(), entity.getDescription());
            assertEquals(dto.getType(), entity.getType());
        }

        @Test
        public void testEntityToDto() {
            ExerciseEntity entity = new ExerciseEntity();
            entity.setId(1L);
            entity.setTitle("Sample Title");
            entity.setDescription("Sample Description");
            entity.setType("Sample Type");

            ExerciseDto dto = exerciseService.entityToDto(entity);

            assertEquals(entity.getId(), dto.getId());
            assertEquals(entity.getTitle(), dto.getTitle());
            assertEquals(entity.getDescription(), dto.getDescription());
            assertEquals(entity.getType(), dto.getType());
        }

        @Test
        public void testSaveExerciseDetails() {
            ExerciseDto dto = new ExerciseDto();
            dto.setId(1L);
            dto.setTitle("Sample Title");
            dto.setDescription("Sample Description");
            dto.setType("Sample Type");

            ExerciseEntity entity = new ExerciseEntity();
            entity.setId(dto.getId());
            entity.setTitle(dto.getTitle());
            entity.setDescription(dto.getDescription());
            entity.setType(dto.getType());

            when(exerciseRepository.save(any(ExerciseEntity.class))).thenReturn(entity);

            ExerciseDto savedDto = exerciseService.saveExerciseDetails(dto);

            assertEquals(dto.getId(), savedDto.getId());
            assertEquals(dto.getTitle(), savedDto.getTitle());
            assertEquals(dto.getDescription(), savedDto.getDescription());
            assertEquals(dto.getType(), savedDto.getType());
        }

        @Test
        public void testGetAllExerciseDetails() {
            List<ExerciseEntity> entities = new ArrayList<>();
            ExerciseEntity entity1 = new ExerciseEntity();
            entity1.setId(1L);
            entity1.setTitle("Sample Title 1");
            entity1.setDescription("Sample Description 1");
            entity1.setType("Sample Type 1");
            entities.add(entity1);

            ExerciseEntity entity2 = new ExerciseEntity();
            entity2.setId(2L);
            entity2.setTitle("Sample Title 2");
            entity2.setDescription("Sample Description 2");
            entity2.setType("Sample Type 2");
            entities.add(entity2);

            when(exerciseRepository.findAll()).thenReturn(entities);

            List<ExerciseDto> dtos = exerciseService.getAllExerciseDetails();

            assertEquals(2, dtos.size());
            assertEquals(entities.get(0).getTitle(), dtos.get(0).getTitle());
            assertEquals(entities.get(1).getTitle(), dtos.get(1).getTitle());
        }

        @Test
        public void testGetExerciseDetailsById() throws UserNotFoundException {
            Long id = 1L;
            ExerciseEntity entity = new ExerciseEntity();
            entity.setId(id);
            entity.setTitle("Sample Title");
            entity.setDescription("Sample Description");
            entity.setType("Sample Type");

            when(exerciseRepository.findById(id)).thenReturn(Optional.of(entity));

            ExerciseDto dto = exerciseService.getExerciseDetailsById(id);

            assertNotNull(dto);
            assertEquals(entity.getId(), dto.getId());
            assertEquals(entity.getTitle(), dto.getTitle());
            assertEquals(entity.getDescription(), dto.getDescription());
            assertEquals(entity.getType(), dto.getType());
        }

        @Test
        public void testGetExerciseDetailsById_NotFound() {
            Long id = 1L;

            when(exerciseRepository.findById(id)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> exerciseService.getExerciseDetailsById(id));
        }

        @Test
        public void testDeleteExerciseDetails_Success() throws UserNotFoundException {
            Long id = 1L;
            ExerciseEntity entity = new ExerciseEntity();
            entity.setId(id);
            entity.setTitle("Sample Title");
            entity.setDescription("Sample Description");
            entity.setType("Sample Type");

            when(exerciseRepository.findById(id)).thenReturn(Optional.of(entity));

            exerciseService.deleteExerciseDetails(id);

            verify(exerciseRepository, times(1)).deleteById(id);
        }

        @Test
        public void testDeleteExerciseDetails_NotFound() {
            Long id = 1L;

            when(exerciseRepository.findById(id)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> exerciseService.deleteExerciseDetails(id));
            verify(exerciseRepository, never()).deleteById(id);
        }
    }

