package com.example.myproject.repositorytest;
import com.example.myproject.entity.LoginEntity;
import com.example.myproject.entity.NotificationEntity;

import com.example.myproject.repositary.LoginRepository;
import com.example.myproject.repositary.NotificationRepository;


import com.example.myproject.resource.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
public class NotificationRepositoryTest {

        @Mock
        private NotificationRepository notificationRepository;

        @Mock
        private LoginRepository loginRepository;

        @InjectMocks
        private NotificationEntity notificationEntity;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testFindById() {
            LoginEntity user = new LoginEntity();
            user.setId(1);
            user.setUsername("testuser");
            user.setPassword("password");
            user.setEmail("test@example.com");
            user.setRole(Role.USER);

            NotificationEntity notification = new NotificationEntity();
            notification.setId(1L);
            notification.setMessage("New message received");
            notification.setTimestamp(LocalDateTime.now());
            notification.setLoginEntity(user);

            when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

            Optional<NotificationEntity> foundNotification = notificationRepository.findById(1L);

            assertEquals("New message received", foundNotification.get().getMessage());
            assertEquals("testuser", foundNotification.get().getLoginEntity().getUsername());
        }

        @Test
        public void testSaveNotification() {
            LoginEntity user = new LoginEntity();
            user.setId(2L);
            user.setUsername("anotheruser");
            user.setPassword("password");
            user.setEmail("anotheruser@example.com");
            user.setRole(Role.ADMIN);

            NotificationEntity notification = new NotificationEntity();
            notification.setId(2L);
            notification.setMessage("Reminder: Meeting at 2 PM");
            notification.setTimestamp(LocalDateTime.now());
            notification.setLoginEntity(user);

            when(loginRepository.findById(2L)).thenReturn(Optional.of(user));
            when(notificationRepository.save(any(NotificationEntity.class))).thenReturn(notification);

            NotificationEntity savedNotification = notificationRepository.save(notification);

            assertEquals(2L, savedNotification.getId());
            assertEquals("Reminder: Meeting at 2 PM", savedNotification.getMessage());
            assertEquals("anotheruser", savedNotification.getLoginEntity().getUsername());
        }

        @Test
        public void testDeleteNotification() {
            LoginEntity user = new LoginEntity();
            user.setId(2L);
            user.setUsername("anotheruser");
            user.setPassword("password");
            user.setEmail("anotheruser@example.com");
            user.setRole(Role.ADMIN);

            NotificationEntity notification = new NotificationEntity();
            notification.setId(2L);
            notification.setMessage("New notification");
            notification.setTimestamp(LocalDateTime.now());
            notification.setLoginEntity(user);

            doNothing().when(notificationRepository).delete(notification);
            notificationRepository.delete(notification);

            verify(notificationRepository, times(1)).delete(notification);
        }

        @Test
        public void testFindByIdNotFound() {
            when(notificationRepository.findById(anyLong())).thenReturn(Optional.empty());

            Optional<NotificationEntity> foundNotification = notificationRepository.findById(10L);

            assertEquals(Optional.empty(), foundNotification);
        }
    }

