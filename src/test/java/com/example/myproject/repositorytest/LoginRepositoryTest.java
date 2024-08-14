package com.example.myproject.repositorytest;
import com.example.myproject.entity.LoginEntity;
import com.example.myproject.repositary.LoginRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginRepositoryTest {


        @Mock
        private LoginRepository loginRepository;

        private LoginEntity loginEntity;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
            loginEntity = new LoginEntity();
            loginEntity.setName("John Doe");
            loginEntity.setUsername("johndoe");
            loginEntity.setPassword("password123");
        }

        @Test
        public void testFindByUsernameSuccess() {
            when(loginRepository.findByUsername("johndoe")).thenReturn(Optional.of(loginEntity));
            Optional<LoginEntity> foundUser = loginRepository.findByUsername("johndoe");
            assertTrue(foundUser.isPresent());
            assertEquals(loginEntity.getUsername(), foundUser.get().getUsername());
        }

        @Test
        public void testFindByUsernameNotFound() {
            when(loginRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());
            Optional<LoginEntity> foundUser = loginRepository.findByUsername("nonexistentuser");
            assertFalse(foundUser.isPresent());
        }

        @Test
        public void testSaveLoginEntity() {
            LoginEntity newLoginEntity = new LoginEntity();
            newLoginEntity.setName("Jane Doe");
            newLoginEntity.setUsername("janedoe");
            newLoginEntity.setPassword("password456");
            when(loginRepository.save(any(LoginEntity.class))).thenReturn(newLoginEntity);

            LoginEntity savedLoginEntity = loginRepository.save(newLoginEntity);
            assertNotNull(savedLoginEntity.getId());
            assertEquals("Jane Doe", savedLoginEntity.getName());
            assertEquals("janedoe", savedLoginEntity.getUsername());
        }

        @Test
        public void testDeleteLoginEntity() {
            doNothing().when(loginRepository).delete(loginEntity);
            loginRepository.delete(loginEntity);
            verify(loginRepository, times(1)).delete(loginEntity);
        }
    }

