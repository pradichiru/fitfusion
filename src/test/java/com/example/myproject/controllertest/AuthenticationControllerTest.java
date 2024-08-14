package com.example.myproject.controllertest;

import com.example.myproject.controller.AuthenticationController;
import com.example.myproject.dto.LoginDto;
import com.example.myproject.entity.LoginEntity;
import com.example.myproject.entity.LoginMapper;
import com.example.myproject.response.AuthenticationResponse;
import com.example.myproject.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthenticationControllerTest {


        @Mock
        private AuthenticationService authenticationService;

        @Mock
        private LoginMapper loginMapper;

        @InjectMocks
        private AuthenticationController authenticationController;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testRegisterSuccess() {
            LoginDto requestDto = new LoginDto();
            LoginEntity requestEntity = new LoginEntity();
            AuthenticationResponse response = new AuthenticationResponse("xyz");

            when(loginMapper.toEntity(any(LoginDto.class))).thenReturn(requestEntity);
            when(authenticationService.register(any(LoginEntity.class))).thenReturn(response);

            ResponseEntity<AuthenticationResponse> result = authenticationController.register(requestDto);
            assertEquals(HttpStatus.CREATED, result.getStatusCode());
            assertEquals(response, result.getBody());
        }

        @Test
        public void testLoginSuccess() {
            LoginDto requestDto = new LoginDto();
            LoginEntity requestEntity = new LoginEntity();
            AuthenticationResponse response = new AuthenticationResponse("xyz");

            when(loginMapper.toEntity(any(LoginDto.class))).thenReturn(requestEntity);
            when(authenticationService.login(any(UserDetails.class))).thenReturn(response);

            ResponseEntity<AuthenticationResponse> result = authenticationController.login(requestDto);
            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals(response, result.getBody());
        }

        @Test
        public void testRegisterUserAlreadyExists() {
            LoginDto requestDto = new LoginDto();
            LoginEntity requestEntity = new LoginEntity();

            when(loginMapper.toEntity(any(LoginDto.class))).thenReturn(requestEntity);
            when(authenticationService.register(any(LoginEntity.class))).thenThrow(new RuntimeException("User already exists"));

            try {
                authenticationController.register(requestDto);
            } catch (RuntimeException e) {
                assertEquals("User already exists", e.getMessage());
            }
        }

        @Test
        public void testLoginUserNotFound() {
            LoginDto requestDto = new LoginDto();
            LoginEntity requestEntity = new LoginEntity();

            when(loginMapper.toEntity(any(LoginDto.class))).thenReturn(requestEntity);
            when(authenticationService.login(any(UserDetails.class))).thenThrow(new RuntimeException("User not found"));

            try {
                authenticationController.login(requestDto);
            } catch (RuntimeException e) {
                assertEquals("User not found", e.getMessage());
            }
        }
    }


