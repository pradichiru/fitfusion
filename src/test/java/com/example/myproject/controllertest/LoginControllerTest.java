package com.example.myproject.controllertest;

import com.example.myproject.controller.LoginController;
import com.example.myproject.dto.LoginDto;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.service.LoginService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

        @Mock
        private LoginService loginService;

        @InjectMocks
        private LoginController loginController;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testCreateSuccess() {
            LoginDto dto = new LoginDto();
            LoginDto createdDto = new LoginDto();
            createdDto.setId(1L);
            createdDto.setUsername("johndoe");
            createdDto.setPassword("password");

            when(loginService.saveUserDetails(any(LoginDto.class))).thenReturn(createdDto);

            ResponseEntity<LoginDto> response = loginController.createUser(dto);
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(createdDto, response.getBody());
        }

        @Test
        public void testGetAllSuccess() {
            LoginDto dto1 = new LoginDto();
            LoginDto dto2 = new LoginDto();
            List<LoginDto> dtos = Arrays.asList(dto1, dto2);

            when(loginService.getAllUserDetails()).thenReturn(dtos);

            ResponseEntity<List<LoginDto>> response = loginController.getAllUsers();
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(dtos, response.getBody());
        }

        @Test
        public void testGetByIdSuccess() throws UserNotFoundException {
            long userId = 1L;
            LoginDto dto = new LoginDto();
            dto.setId(userId);

            when(loginService.getUserDetailsById(userId)).thenReturn(dto);

            ResponseEntity<LoginDto> response = loginController.getUserById(userId);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(dto, response.getBody());
        }

        @Test
        public void testGetByIdNotFound() throws UserNotFoundException {
            long userId = 1L;

            when(loginService.getUserDetailsById(userId)).thenThrow(new UserNotFoundException("User Not Found"));
            try {
                ResponseEntity<LoginDto> response = loginController.getUserById(userId);
            } catch (UserNotFoundException e) {
                assertEquals("User Not Found", e.getMessage());
            }
        }

        @Test
        public void testUpdateSuccess() throws UserNotFoundException {
            long userId = 1L;
            LoginDto dto = new LoginDto();
            dto.setId(userId);

            when(loginService.saveUserDetails(any(LoginDto.class))).thenReturn(dto);

            ResponseEntity<LoginDto> response = loginController.updateUser(userId, dto);
            assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
            assertEquals(dto, response.getBody());
        }

        @Test
        public void testUpdateNotFound() throws UserNotFoundException {
            long userId = 1L;
            LoginDto dto = new LoginDto();
            dto.setId(userId);

            doThrow(new UserNotFoundException("User Not Found")).when(loginService).getUserDetailsById(userId);
            try {
                ResponseEntity<LoginDto> response = loginController.updateUser(userId, dto);
            } catch (UserNotFoundException e) {
                assertEquals("User Not Found", e.getMessage());
            }
        }

        @Test
        public void testDeleteSuccess() throws UserNotFoundException {
            long id = 1L;

            ResponseEntity<String> response = loginController.deleteUser(id);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Deleted user " + id, response.getBody());
            verify(loginService, times(1)).deleteUserDetails(id);
        }

        @Test
        public void testDeleteNotFound() throws UserNotFoundException {
            long userId = 1L;
            doThrow(new UserNotFoundException("User Not Found")).when(loginService).deleteUserDetails(userId);
            try {
                ResponseEntity<String> response = loginController.deleteUser(userId);
            } catch (UserNotFoundException e) {
                assertEquals("User Not Found", e.getMessage());
            }
        }
    }

