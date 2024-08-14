package com.example.myproject.controllertest;
import com.example.myproject.controller.NotificationController;
import com.example.myproject.dto.NotificationDto;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class NotificationControllerTest {

        @Mock
        private NotificationService notificationService;

        @InjectMocks
        private NotificationController notificationController;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testCreateNotificationSuccess() {
            NotificationDto dto = new NotificationDto();
            NotificationDto createdDto = new NotificationDto();
            createdDto.setId(1L);
            createdDto.setMessage("Test message");
            createdDto.setTimestamp(LocalDateTime.now());

            when(notificationService.saveNotificationDetails(any(NotificationDto.class))).thenReturn(createdDto);

            ResponseEntity<NotificationDto> response = notificationController.createNotification(dto);
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(createdDto, response.getBody());
        }

        @Test
        public void testGetAllNotificationsSuccess() {
            NotificationDto dto1 = new NotificationDto();
            NotificationDto dto2 = new NotificationDto();
            List<NotificationDto> dtos = Arrays.asList(dto1, dto2);

            when(notificationService.getAllNotificationDetails()).thenReturn(dtos);

            ResponseEntity<List<NotificationDto>> response = notificationController.getAllNotification();
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(dtos, response.getBody());
        }

        @Test
        public void testGetNotificationByIdSuccess() throws UserNotFoundException {
            long id = 1L;
            NotificationDto dto = new NotificationDto();

            when(notificationService.getNotificationDetailsById(id)).thenReturn(dto);

            ResponseEntity<NotificationDto> response = notificationController.getNotificationById(id);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(dto, response.getBody());
        }

        @Test
        public void testGetNotificationByIdNotFound() throws UserNotFoundException {
            long id = 1L;
            when(notificationService.getNotificationDetailsById(id)).thenThrow(new UserNotFoundException("User Not Found"));

            try {
                notificationController.getNotificationById(id);
            } catch (UserNotFoundException e) {
                assertEquals("User Not Found", e.getMessage());
            }
        }

        @Test
        public void testUpdateNotificationSuccess() throws UserNotFoundException {
            long id = 1L;
            NotificationDto dto = new NotificationDto();
            dto.setId(id);

            when(notificationService.saveNotificationDetails(any(NotificationDto.class))).thenReturn(dto);

            ResponseEntity<NotificationDto> response = notificationController.updateNotification(id, dto);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(dto, response.getBody());
        }

        @Test
        public void testUpdateNotificationNotFound() throws UserNotFoundException {
            long id = 1L;
            NotificationDto dto = new NotificationDto();
            dto.setId(id);

            when(notificationService.getNotificationDetailsById(id)).thenThrow(new UserNotFoundException("User Not Found"));

            try {
                notificationController.updateNotification(id, dto);
            } catch (UserNotFoundException e) {
                assertEquals("User Not Found", e.getMessage());
            }
        }

        @Test
        public void testDeleteNotificationSuccess() throws UserNotFoundException {
            long id = 1L;
            ResponseEntity<String> response = notificationController.deleteUser(id);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Deleted notification " + id, response.getBody());
            verify(notificationService, times(1)).deleteNotificationDetails(id);
        }

        @Test
        public void testDeleteNotificationNotFound() throws UserNotFoundException {
            long id = 1L;
            doThrow(new UserNotFoundException("User Not Found")).when(notificationService).deleteNotificationDetails(id);

            try {
                notificationController.deleteUser(id);
            } catch (UserNotFoundException e) {
                assertEquals("User Not Found", e.getMessage());
            }
        }
    }

