package com.example.myproject.repositorytest;
import com.example.myproject.entity.LoginEntity;
import com.example.myproject.entity.ProfileEntity;

import com.example.myproject.repositary.LoginRepository;
import com.example.myproject.repositary.ProfileRepository;


import com.example.myproject.resource.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ProfileRepositoryTest {

        @Mock
        private ProfileRepository profileRepository;

        @Mock
        private LoginRepository loginRepository;

        @InjectMocks
        private ProfileEntity profileEntity;

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
            user.setRole(Role.USER);

            ProfileEntity profile = new ProfileEntity();
            profile.setProfileId(1L);
            profile.setFirstName("John");
            profile.setLastName("Doe");
            profile.setBio("Software Engineer");
            profile.setLoginEntity(user);

            when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));

            Optional<ProfileEntity> foundProfile = profileRepository.findById(1L);

            assertEquals("John", foundProfile.get().getFirstName());
            assertEquals("Doe", foundProfile.get().getLastName());
        }

        @Test
        public void testSaveProfile() {
            LoginEntity user = new LoginEntity();
            user.setId(2L);
            user.setUsername("anotheruser");
            user.setPassword("password");
            user.setEmail("anotheruser@example.com");
            user.setRole(Role.ADMIN);

            ProfileEntity profile = new ProfileEntity();
            profile.setProfileId(2L);
            profile.setFirstName("Jane");
            profile.setLastName("Smith");
            profile.setBio("Data Scientist");
            profile.setLoginEntity(user);

            when(loginRepository.findById(2L)).thenReturn(Optional.of(user));
            when(profileRepository.save(any(ProfileEntity.class))).thenReturn(profile);

            ProfileEntity savedProfile = profileRepository.save(profile);

            assertEquals(2L, savedProfile.getProfileId());
            assertEquals("Jane", savedProfile.getFirstName());
            assertEquals("Smith", savedProfile.getLastName());
        }

        @Test
        public void testDeleteProfile() {
            LoginEntity user = new LoginEntity();
            user.setId(2L);
            user.setUsername("anotheruser");
            user.setPassword("password");
            user.setEmail("anotheruser@example.com");
            user.setRole(Role.ADMIN);

            ProfileEntity profile = new ProfileEntity();
            profile.setProfileId(2L);
            profile.setFirstName("Jane");
            profile.setLastName("Smith");
            profile.setBio("Data Scientist");
            profile.setLoginEntity(user);

            doNothing().when(profileRepository).delete(profile);
            profileRepository.delete(profile);

            verify(profileRepository, times(1)).delete(profile);
        }

        @Test
        public void testFindByIdNotFound() {
            when(profileRepository.findById(anyLong())).thenReturn(Optional.empty());

            Optional<ProfileEntity> foundProfile = profileRepository.findById(10L);

            assertEquals(Optional.empty(), foundProfile);
        }
    }

