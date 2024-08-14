package com.example.myproject.controller;


import com.example.myproject.dto.ProfileDto;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @PostMapping("/create")
    public ResponseEntity<ProfileDto> createProfile(@RequestBody @Valid ProfileDto profileDetailsEntity) {
        ProfileDto profile=profileService.saveProfileDetails(profileDetailsEntity);
        return  new ResponseEntity<>(profile, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProfileDto>> getAllProfiles() {
        return new ResponseEntity<>(profileService.getAllProfileDetails(), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ProfileDto> getProfileById(@PathVariable("id") long profileId) throws UserNotFoundException {
        return new ResponseEntity<>(profileService.getProfileDetailsById(profileId), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProfileDto> updateProfile(@PathVariable("id") long profileId, @RequestBody @Valid ProfileDto profileDetailsEntity) throws UserNotFoundException {
        profileService.getProfileDetailsById(profileId);
        profileDetailsEntity.setProfileId(profileId);
        return new ResponseEntity<>(profileService.saveProfileDetails(profileDetailsEntity), HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long profileId) throws UserNotFoundException {
        profileService.deleteProfileDetails(profileId);
        return ResponseEntity.ok("Deleted profile "+ profileId);
    }
}
