package com.example.myproject.servicetest;
import com.example.myproject.dto.NotificationDto;
import com.example.myproject.entity.NotificationEntity;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.repositary.NotificationRepository;
import com.example.myproject.service.NotificationService;
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

public class NotificationServiceTest {

        @Mock
        private NotificationRepository notificationRepository;

        @InjectMocks
        private NotificationService notificationService;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testDtoToEntity() {
            NotificationDto dto = new NotificationDto();
            dto.setId(1L);
            dto.setMessage("Test message");
            dto.setTimestamp(LocalDateTime.now());

            NotificationEntity entity = notificationService.dtoToEntity(dto);

            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getMessage(), entity.getMessage());
            assertEquals(dto.getTimestamp(), entity.getTimestamp());
        }

        @Test
        public void testEntityToDto() {
            NotificationEntity entity = new NotificationEntity();
            entity.setId(1L);
            entity.setMessage("Test message");
            entity.setTimestamp(LocalDateTime.now());

            NotificationDto dto = notificationService.entityToDto(entity);

            assertEquals(entity.getId(), dto.getId());
            assertEquals(entity.getMessage(), dto.getMessage());
            assertEquals(entity.getTimestamp(), dto.getTimestamp());
        }

        @Test
        public void testSaveExerciseDetails() {
            NotificationDto dto = new NotificationDto();
            dto.setId(1L);
            dto.setMessage("Test message");
            dto.setTimestamp(LocalDateTime.now());

            NotificationEntity entity = new NotificationEntity();
            entity.setId(1L);
            entity.setMessage(dto.getMessage());
            entity.setTimestamp(dto.getTimestamp());

            when(notificationRepository.save(any(NotificationEntity.class))).thenReturn(entity);

            NotificationDto savedDto = notificationService.saveNotificationDetails(dto);

            assertEquals(dto.getId(), savedDto.getId());
            assertEquals(dto.getMessage(), savedDto.getMessage());
            assertEquals(dto.getTimestamp(), savedDto.getTimestamp());
        }

        @Test
        public void testGetAllExerciseDetails() {
            List<NotificationEntity> entities = new ArrayList<>();
            NotificationEntity entity1 = new NotificationEntity();
            entity1.setId(1L);
            entity1.setMessage("Test message 1");
            entity1.setTimestamp(LocalDateTime.now());
            entities.add(entity1);

            NotificationEntity entity2 = new NotificationEntity();
            entity2.setId(2L);
            entity2.setMessage("Test message 2");
            entity2.setTimestamp(LocalDateTime.now());
            entities.add(entity2);

            when(notificationRepository.findAll()).thenReturn(entities);

            List<NotificationDto> dtos = notificationService.getAllNotificationDetails();

            assertEquals(2, dtos.size());
            assertEquals(entities.get(0).getMessage(), dtos.get(0).getMessage());
            assertEquals(entities.get(1).getMessage(), dtos.get(1).getMessage());
        }

        @Test
        public void testGetExerciseDetailsById() throws UserNotFoundException {
            Long id = 1L;
            NotificationEntity entity = new NotificationEntity();
            entity.setId(id);
            entity.setMessage("Test message");
            entity.setTimestamp(LocalDateTime.now());

            when(notificationRepository.findById(id)).thenReturn(Optional.of(entity));

            NotificationDto dto = notificationService.getNotificationDetailsById(id);

            assertNotNull(dto);
            assertEquals(entity.getId(), dto.getId());
            assertEquals(entity.getMessage(), dto.getMessage());
            assertEquals(entity.getTimestamp(), dto.getTimestamp());
        }

        @Test
        public void testGetExerciseDetailsById_NotFound() {
            Long id = 1L;

            when(notificationRepository.findById(id)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> notificationService.getNotificationDetailsById(id));
        }

        @Test
        public void testDeleteExerciseDetails_Success() throws UserNotFoundException {
            Long id = 1L;
            NotificationEntity entity = new NotificationEntity();
            entity.setId(id);
            entity.setMessage("Test message");
            entity.setTimestamp(LocalDateTime.now());

            when(notificationRepository.findById(id)).thenReturn(Optional.of(entity));

            notificationService.deleteNotificationDetails(id);

            verify(notificationRepository, times(1)).deleteById(id);
        }

        @Test
        public void testDeleteExerciseDetails_NotFound() {
            Long id = 1L;

            when(notificationRepository.findById(id)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> notificationService.deleteNotificationDetails(id));
            verify(notificationRepository, never()).deleteById(id);
        }
    }

