package com.example.myproject.servicetest;
import com.example.myproject.dto.UserActivityDto;
import com.example.myproject.entity.LoginEntity;
import com.example.myproject.entity.UserActivityEntity;

import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.repositary.UserActivityRepository;
import com.example.myproject.service.UserActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserActivityServiceTest {


        @Mock
        private UserActivityRepository userActivityRepository;

        @InjectMocks
        private UserActivityService userActivityService;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testDtoToEntity() {
            UserActivityDto dto = new UserActivityDto();
            dto.setId(1L);
            dto.setActivityType("Logged in");
            dto.setTimestamp(LocalDateTime.now());
            LoginEntity userEntity = new LoginEntity();
            userEntity.setId(1234567890123L);
            dto.setLoginEntity(userEntity);

            UserActivityEntity entity = userActivityService.dtoToEntity(dto);

            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getActivityType(), entity.getActivityType());
            assertEquals(dto.getTimestamp(), entity.getTimestamp());
            assertEquals(dto.getLoginEntity(), entity.getLoginEntity());
        }

        @Test
        public void testEntityToDto() {
            UserActivityEntity entity = new UserActivityEntity();
            entity.setId(1L);
            entity.setActivityType("Logged in");
            entity.setTimestamp(LocalDateTime.now());
            LoginEntity userEntity = new LoginEntity();
            userEntity.setId(1234567890123L);
            entity.setLoginEntity(userEntity);

            UserActivityDto dto = userActivityService.entityToDto(entity);

            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getActivityType(), entity.getActivityType());
            assertEquals(dto.getTimestamp(), entity.getTimestamp());
            assertEquals(dto.getLoginEntity(), entity.getLoginEntity());
        }

        @Test
        public void testSaveExerciseDetails() {
            UserActivityDto dto = new UserActivityDto();
            dto.setId(1L);
            dto.setActivityType("Logged in");
            dto.setTimestamp(LocalDateTime.now());
            LoginEntity userEntity = new LoginEntity();
            userEntity.setId(1234567890123L);
            dto.setLoginEntity(userEntity);

            UserActivityEntity entity = new UserActivityEntity();
            entity.setId(dto.getId());
            entity.setActivityType(dto.getActivityType());
            entity.setTimestamp(dto.getTimestamp());
            entity.setLoginEntity(dto.getLoginEntity());

            when(userActivityRepository.save(any(UserActivityEntity.class))).thenReturn(entity);

            UserActivityDto savedDto = userActivityService.saveActivityDetails(dto);

            assertEquals(dto.getId(), savedDto.getId());
            assertEquals(dto.getActivityType(), savedDto.getActivityType());
            assertEquals(dto.getTimestamp(), savedDto.getTimestamp());
            assertEquals(dto.getLoginEntity(), savedDto.getLoginEntity());
        }

        @Test
        public void testGetAllExerciseDetails() {
            List<UserActivityEntity> entities = new ArrayList<>();
            UserActivityEntity entity1 = new UserActivityEntity();
            entity1.setId(1L);
            entity1.setActivityType("Logged in");
            entity1.setTimestamp(LocalDateTime.now());
            LoginEntity userEntity1 = new LoginEntity();
            userEntity1.setId(1234567890123L);
            entity1.setLoginEntity(userEntity1);
            entities.add(entity1);

            UserActivityEntity entity2 = new UserActivityEntity();
            entity2.setId(2L);
            entity2.setActivityType("Created Profile");
            entity2.setTimestamp(LocalDateTime.now());
            LoginEntity userEntity2 = new LoginEntity();
            userEntity2.setId(2234567890123L);
            entity2.setLoginEntity(userEntity2);
            entities.add(entity2);

            when(userActivityRepository.findAll()).thenReturn(entities);

            List<UserActivityDto> dtos = userActivityService.getAllActivityDetails();

            assertEquals(2, dtos.size());
            assertEquals(entities.get(0).getActivityType(), dtos.get(0).getActivityType());
            assertEquals(entities.get(1).getActivityType(), dtos.get(1).getActivityType());
        }

        @Test
        public void testGetExerciseDetailsById_Success() throws UserNotFoundException {
            Long id = 1L;
            UserActivityEntity entity = new UserActivityEntity();
            entity.setId(id);
            entity.setActivityType("Logged in");
            entity.setTimestamp(LocalDateTime.now());
            LoginEntity userEntity = new LoginEntity();
            userEntity.setId(1234567890123L);
            entity.setLoginEntity(userEntity);

            when(userActivityRepository.findById(id)).thenReturn(Optional.of(entity));

            UserActivityDto dto = userActivityService.getActivityDetailsById(id);

            assertNotNull(dto);
            assertEquals(entity.getId(), dto.getId());
            assertEquals(entity.getActivityType(), dto.getActivityType());
            assertEquals(entity.getTimestamp(), dto.getTimestamp());
            assertEquals(entity.getLoginEntity(), dto.getLoginEntity());
        }

        @Test
        public void testGetExerciseDetailsById_NotFound() {
            Long id = 1L;

            when(userActivityRepository.findById(id)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> userActivityService.getActivityDetailsById(id));
        }

        @Test
        public void testDeleteExerciseDetails_Success() throws UserNotFoundException {
            Long id = 1L;
            UserActivityEntity entity = new UserActivityEntity();
            entity.setId(id);
            entity.setActivityType("Logged in");
            entity.setTimestamp(LocalDateTime.now());
            LoginEntity userEntity = new LoginEntity();
            userEntity.setId(1234567890123L);
            entity.setLoginEntity(userEntity);

            when(userActivityRepository.findById(id)).thenReturn(Optional.of(entity));

            userActivityService.deleteActivityDetails(id);

            verify(userActivityRepository, times(1)).deleteById(id);
        }

        @Test
        public void testDeleteExerciseDetails_NotFound() {
            Long id = 1L;

            when(userActivityRepository.findById(id)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> userActivityService.deleteActivityDetails(id));
            verify(userActivityRepository, never()).deleteById(id);
        }
    }

