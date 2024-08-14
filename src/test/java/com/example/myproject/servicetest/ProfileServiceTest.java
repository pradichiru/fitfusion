package com.example.myproject.servicetest;
import com.example.myproject.dto.ProfileDto;
import com.example.myproject.entity.ProfileEntity;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.repositary.ProfileRepository;

import com.example.myproject.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProfileServiceTest {

        @Mock
        private ProfileRepository profileRepository;

        @InjectMocks
        private ProfileService profileService;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testDtoToEntity() {
            ProfileDto dto = new ProfileDto();
            dto.setFirstName("John");
            dto.setLastName("Doe");
            dto.setBio("Computer Scientist");

            ProfileEntity entity = profileService.dtoToEntity(dto);

            assertEquals(dto.getFirstName(), entity.getFirstName());
            assertEquals(dto.getLastName(), entity.getLastName());
            assertEquals(dto.getBio(), entity.getBio());
        }

        @Test
        public void testEntityToDto() {
            ProfileEntity entity = new ProfileEntity();
            entity.setFirstName("John");
            entity.setLastName("Doe");
            entity.setBio("Computer Scientist");

            ProfileDto dto = profileService.entityToDto(entity);

            assertEquals(entity.getFirstName(), dto.getFirstName());
            assertEquals(entity.getLastName(), dto.getLastName());
            assertEquals(entity.getBio(), dto.getBio());
        }

        @Test
        public void testSaveUserDetails() {
            ProfileDto dto = new ProfileDto();
            dto.setFirstName("John");
            dto.setLastName("Doe");
            dto.setBio("Computer Scientist");

            ProfileEntity entity = new ProfileEntity();
            entity.setFirstName(dto.getFirstName());
            entity.setLastName(dto.getLastName());
            entity.setBio(dto.getBio());

            when(profileRepository.save(any(ProfileEntity.class))).thenReturn(entity);

            ProfileDto savedDto = profileService.saveProfileDetails(dto);

            assertEquals(dto.getFirstName(), savedDto.getFirstName());
            assertEquals(dto.getLastName(), savedDto.getLastName());
            assertEquals(dto.getBio(), savedDto.getBio());
        }

        @Test
        public void testGetAllUserDetails() {
            List<ProfileEntity> entities = new ArrayList<>();
            ProfileEntity entity1 = new ProfileEntity();
            entity1.setFirstName("John");
            entity1.setLastName("Doe");
            entity1.setBio("Computer Scientist");
            entities.add(entity1);

            ProfileEntity entity2 = new ProfileEntity();
            entity2.setFirstName("Jane");
            entity2.setLastName("Doe");
            entity2.setBio("Data Scientist");
            entities.add(entity2);

            when(profileRepository.findAll()).thenReturn(entities);

            List<ProfileDto> dtos = profileService.getAllProfileDetails();

            assertEquals(2, dtos.size());
            assertEquals(entities.get(0).getFirstName(), dtos.get(0).getFirstName());
            assertEquals(entities.get(1).getFirstName(), dtos.get(1).getFirstName());
        }

        @Test
        public void testGetUserDetailsById() throws UserNotFoundException {
            Long id = 1L;
            ProfileEntity entity = new ProfileEntity();
            entity.setProfileId(id);
            entity.setFirstName("John");
            entity.setLastName("Doe");
            entity.setBio("Computer Scientist");

            when(profileRepository.findById(id)).thenReturn(Optional.of(entity));

            ProfileDto dto = profileService.getProfileDetailsById(id);

            assertNotNull(dto);
            assertEquals(entity.getFirstName(), dto.getFirstName());
            assertEquals(entity.getLastName(), dto.getLastName());
            assertEquals(entity.getBio(), dto.getBio());
        }

        @Test
        public void testGetUserDetailsById_NotFound() {
            Long id = 1L;

            when(profileRepository.findById(id)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> profileService.getProfileDetailsById(id));
        }

        @Test
        public void testDeleteUserDetails() throws UserNotFoundException {
            Long id = 1L;
            ProfileEntity entity = new ProfileEntity();
            entity.setProfileId(id);
            entity.setFirstName("John");
            entity.setLastName("Doe");
            entity.setBio("Computer Scientist");

            when(profileRepository.findById(id)).thenReturn(Optional.of(entity));

            profileService.deleteProfileDetails(id);

            verify(profileRepository, times(1)).deleteById(id);
        }

        @Test
        public void testDeleteUserDetails_NotFound() {
            Long id = 1L;

            when(profileRepository.findById(id)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> profileService.deleteProfileDetails(id));
            verify(profileRepository, never()).deleteById(id);
        }
    }

