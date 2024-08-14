package com.example.myproject.controllertest;
import com.example.myproject.controller.ProfileController;
import com.example.myproject.dto.ProfileDto;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.service.ProfileService;
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


public class ProfileControllerTest {

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private ProfileController profileController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSuccess() {
        ProfileDto dto = new ProfileDto();
        ProfileDto createdDto = new ProfileDto();
        createdDto.setProfileId(1L);
        createdDto.setFirstName("John");
        createdDto.setLastName("Doe");
        createdDto.setBio("Computer Scientist");

        when(profileService.saveProfileDetails(any(ProfileDto.class))).thenReturn(createdDto);

        ResponseEntity<ProfileDto> response = profileController.createProfile(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDto, response.getBody());
    }

    @Test
    public void testGetAllSuccess() {
        ProfileDto dto1 = new ProfileDto();
        ProfileDto dto2 = new ProfileDto();
        List<ProfileDto> dtos = Arrays.asList(dto1, dto2);

        when(profileService.getAllProfileDetails()).thenReturn(dtos);

        ResponseEntity<List<ProfileDto>> response = profileController.getAllProfiles();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtos, response.getBody());
    }

    @Test
    public void testGetByIdSuccess() throws UserNotFoundException {
        long profileId = 1L;
        ProfileDto dto = new ProfileDto();
        dto.setProfileId(profileId);

        when(profileService.getProfileDetailsById(profileId)).thenReturn(dto);

        ResponseEntity<ProfileDto> response = profileController.getProfileById(profileId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testGetByIdNotFound() throws UserNotFoundException {
        long profileId = 1L;

        when(profileService.getProfileDetailsById(profileId)).thenThrow(new UserNotFoundException("User Not Found"));
        try {
            ResponseEntity<ProfileDto> response = profileController.getProfileById(profileId);
        } catch (UserNotFoundException e) {

            assertEquals("User Not Found", e.getMessage());
        }
    }

    @Test
    public void testUpdateSuccess() throws UserNotFoundException {
        long profileId = 1L;
        ProfileDto dto = new ProfileDto();
        dto.setProfileId(profileId);

        when(profileService.saveProfileDetails(any(ProfileDto.class))).thenReturn(dto);

        ResponseEntity<ProfileDto> response = profileController.updateProfile(profileId, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void testUpdateNotFound() throws UserNotFoundException {
        long profileId = 1L;
        ProfileDto dto = new ProfileDto();
        dto.setProfileId(profileId);

        doThrow(new UserNotFoundException("User Not Found")).when(profileService).getProfileDetailsById(profileId);
        try {

            ResponseEntity<ProfileDto> response = profileController.updateProfile(profileId, dto);
        } catch (UserNotFoundException e) {
            assertEquals("User Not Found", e.getMessage());
        }
    }

    @Test
    public void testDeleteSuccess() throws UserNotFoundException {
        long id = 1L;

        ResponseEntity<String> response = profileController.deleteUser(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted profile " + id, response.getBody());
        verify(profileService, times(1)).deleteProfileDetails(id);
    }

    @Test
    public void testDeleteNotFound() throws UserNotFoundException {
        long profileId = 1L;
        doThrow(new UserNotFoundException("User Not Found")).when(profileService).deleteProfileDetails(profileId);
        try {
            ResponseEntity<String> response = profileController.deleteUser(profileId);
        } catch (UserNotFoundException e) {
            assertEquals("User Not Found", e.getMessage());
        }
    }
}
