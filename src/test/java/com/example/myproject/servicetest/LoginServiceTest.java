package com.example.myproject.servicetest;
import com.example.myproject.entity.LoginEntity;
import com.example.myproject.repositary.LoginRepository;
import com.example.myproject.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class LoginServiceTest {


        @Mock
        private LoginRepository loginRepository;

        @InjectMocks
        private LoginService loginService;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testLoadUserByUsernameSuccess() {
            LoginEntity loginEntity = new LoginEntity();
            loginEntity.setUsername("johndoe");
            loginEntity.setPassword("password123");

            when(loginRepository.findByUsername("johndoe")).thenReturn(Optional.of(loginEntity));

            UserDetails userDetails = loginService.loadUserByUsername("johndoe");

            assertNotNull(userDetails);
            assertEquals("johndoe", userDetails.getUsername());
            assertEquals("password123", userDetails.getPassword());
        }

        @Test
        public void testLoadUserByUsernameNotFound() {
            when(loginRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

            Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
                loginService.loadUserByUsername("nonexistentuser");
            });

            assertEquals("User not found", exception.getMessage());
        }
    }

