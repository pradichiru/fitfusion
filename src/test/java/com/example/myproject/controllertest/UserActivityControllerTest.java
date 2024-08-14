package com.example.myproject.controllertest;
import com.example.myproject.controller.UserActivityController;
import com.example.myproject.dto.UserActivityDto;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.service.UserActivityService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


public class UserActivityControllerTest {

        @Mock
        private UserActivityService userActivityService;

        @InjectMocks
        private UserActivityController userActivityController;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testCreateUserActivitySuccess() {
            UserActivityDto dto = new UserActivityDto();
            dto.setActivityType("Logged in");
            dto.setTimestamp(LocalDateTime.now());

            UserActivityDto createdDto = new UserActivityDto();
            createdDto.setId(1L);
            createdDto.setActivityType("Logged in");
            createdDto.setTimestamp(LocalDateTime.now());

            when(userActivityService.saveActivityDetails(any(UserActivityDto.class))).thenReturn(createdDto);

            ResponseEntity<UserActivityDto> response = userActivityController.createUser(dto);
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(createdDto, response.getBody());
        }

        @Test
        public void testGetAllUserActivitiesSuccess() {
            UserActivityDto dto1 = new UserActivityDto();
            UserActivityDto dto2 = new UserActivityDto();
            List<UserActivityDto> dtos = Arrays.asList(dto1, dto2);

            when(userActivityService.getAllActivityDetails()).thenReturn(dtos);

            ResponseEntity<List<UserActivityDto>> response = userActivityController.getAllUsers();
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(dtos, response.getBody());
        }

        @Test
        public void testGetUserActivityByIdSuccess() throws UserNotFoundException {
            long id = 10L;
            UserActivityDto dto = new UserActivityDto();
            dto.setId(id);
            dto.setActivityType("Logged in");
            dto.setTimestamp(LocalDateTime.now());

            when(userActivityService.getActivityDetailsById(id)).thenReturn(dto);

            ResponseEntity<UserActivityDto> response = userActivityController.getUserById(id);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(dto, response.getBody());
        }

        @Test
        public void testGetUserActivityByIdNotFound() throws UserNotFoundException {
            long id = 10L;

            when(userActivityService.getActivityDetailsById(id)).thenThrow(new UserNotFoundException("User Not Found"));

            try {
                userActivityController.getUserById(id);
            } catch (UserNotFoundException e) {
                assertEquals("User Not Found", e.getMessage());
            }
        }

        @Test
        public void testUpdateUserActivitySuccess() throws UserNotFoundException {
            long id = 10L;
            UserActivityDto dto = new UserActivityDto();
            dto.setActivityType("Logged in");
            dto.setTimestamp(LocalDateTime.now());

            UserActivityDto updatedDto = new UserActivityDto();
            updatedDto.setId(id);
            updatedDto.setActivityType("Logged in");
            updatedDto.setTimestamp(LocalDateTime.now());

            when(userActivityService.saveActivityDetails(any(UserActivityDto.class))).thenReturn(updatedDto);

            ResponseEntity<UserActivityDto> response = userActivityController.updateUser(id, dto);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(updatedDto, response.getBody());
        }

        @Test
        public void testUpdateUserActivityNotFound() throws UserNotFoundException {
            long id = 10L;
            UserActivityDto dto = new UserActivityDto();
            dto.setActivityType("Logged in");
            dto.setTimestamp(LocalDateTime.now());

            when(userActivityService.getActivityDetailsById(id)).thenThrow(new UserNotFoundException("User Not Found"));

            try {
                userActivityController.updateUser(id, dto);
            } catch (UserNotFoundException e) {
                assertEquals("User Not Found", e.getMessage());
            }
        }

        @Test
        public void testDeleteUserActivitySuccess() throws UserNotFoundException {
            long id = 10L;
            ResponseEntity<String> response = userActivityController.deleteUser(id);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Deleted activity " + id, response.getBody());
            verify(userActivityService, times(1)).deleteActivityDetails(id);
        }

        @Test
        public void testDeleteUserActivityNotFound() throws UserNotFoundException {
            long id = 10L;

            doThrow(new UserNotFoundException("User Not Found")).when(userActivityService).deleteActivityDetails(id);

            try {
                userActivityController.deleteUser(id);
            } catch (UserNotFoundException e) {
                assertEquals("User Not Found", e.getMessage());
            }
        }
    }

