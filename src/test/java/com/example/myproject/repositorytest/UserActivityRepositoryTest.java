package com.example.myproject.repositorytest;

import com.example.myproject.entity.UserActivityEntity;
import com.example.myproject.entity.LoginEntity;
import com.example.myproject.repositary.LoginRepository;
import com.example.myproject.repositary.UserActivityRepository;


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

public class UserActivityRepositoryTest {

        @Mock
        private UserActivityRepository userActivityRepository;

        @Mock
        private LoginRepository loginRepository;

        @InjectMocks
        private UserActivityEntity userActivityEntity;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testFindById() {
            LoginEntity user = new LoginEntity();
            user.setId(1L);
            user.setUsername("testuser");
            user.setPassword("password");
            user.setEmail("test@example.com");
            user.setRole(Role.USER);  // Use Role enum

            UserActivityEntity userActivity = new UserActivityEntity();
            userActivity.setId(1L);
            userActivity.setActivityType("Logged in");
            userActivity.setTimestamp(LocalDateTime.now());
            userActivity.setLoginEntity(user);

            when(userActivityRepository.findById(1L)).thenReturn(Optional.of(userActivity));

            Optional<UserActivityEntity> foundUserActivity = userActivityRepository.findById(1L);

            assertEquals("Logged in", foundUserActivity.get().getActivityType());
            assertEquals("testuser", foundUserActivity.get().getLoginEntity().getUsername());
        }

        @Test
        public void testSaveUserActivity() {

                LoginEntity user = new LoginEntity();
                user.setId(2L);
                user.setUsername("anotheruser");
                user.setPassword("password");
                user.setEmail("anotheruser@example.com");
                user.setRole(Role.ADMIN);  // Use Role enum

                UserActivityEntity userActivity = new UserActivityEntity();
                userActivity.setId(2L);
                userActivity.setActivityType("Profile Created");
                userActivity.setTimestamp(LocalDateTime.now());
                userActivity.setLoginEntity(user);

                when(loginRepository.findById(2L)).thenReturn(Optional.of(user));
                when(userActivityRepository.save(userActivity)).thenReturn(userActivity); // Directly return userActivity

                UserActivityEntity savedUserActivity = userActivityRepository.save(userActivity);

                assertEquals(2L, savedUserActivity.getId());
                assertEquals("Profile Created", savedUserActivity.getActivityType());
                assertEquals("anotheruser", savedUserActivity.getLoginEntity().getUsername());
            }


            @Test
        public void testDeleteUserActivity() {
            LoginEntity user = new LoginEntity();
            user.setId(2L);
            user.setUsername("anotheruser");
            user.setPassword("password");
            user.setEmail("anotheruser@example.com");
            user.setRole(Role.ADMIN);  // Use Role enum

            UserActivityEntity userActivity = new UserActivityEntity();
            userActivity.setId(2L);
            userActivity.setActivityType("Logged out");
            userActivity.setTimestamp(LocalDateTime.now());
            userActivity.setLoginEntity(user);

            doNothing().when(userActivityRepository).delete(userActivity);
            userActivityRepository.delete(userActivity);

            verify(userActivityRepository, times(1)).delete(userActivity);
        }

        @Test
        public void testFindByIdNotFound() {
            when(userActivityRepository.findById(anyLong())).thenReturn(Optional.empty());

            Optional<UserActivityEntity> foundUserActivity = userActivityRepository.findById(10L);

            assertEquals(Optional.empty(), foundUserActivity);
        }
    }


