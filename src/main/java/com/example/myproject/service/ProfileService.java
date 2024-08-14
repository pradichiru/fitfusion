package com.example.myproject.service;


import com.example.myproject.dto.ProfileDto;
import com.example.myproject.entity.ProfileEntity;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.repositary.ProfileRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    public ProfileEntity dtoToEntity(ProfileDto user){
        ProfileEntity userEntity=new ProfileEntity();
        BeanUtils.copyProperties(user,userEntity);
        return userEntity;
    }

    public ProfileDto entityToDto(ProfileEntity userEntity){
        ProfileDto userDto=new ProfileDto();
        BeanUtils.copyProperties(userEntity,userDto);
        return userDto;
    }

    public List<ProfileDto> getAllProfileDetails() {
        List<ProfileDto> profile=new ArrayList<>();
        for(ProfileEntity profileDetailsEntity: profileRepository.findAll()){
            profile.add(entityToDto(profileDetailsEntity));
        }
        return profile;
    }

    public ProfileDto getProfileDetailsById(long profileId) throws UserNotFoundException {
        ProfileEntity profile = profileRepository.findById(profileId).orElse(null);
        if (profile != null) {
            return entityToDto(profile);
        } else
            throw new UserNotFoundException("ProfileId Not Found");

    }

    public ProfileDto saveProfileDetails(ProfileDto profileDetails) {
        ProfileEntity profile=dtoToEntity(profileDetails);
        return entityToDto(profileRepository.save(profile));
    }

    public void deleteProfileDetails(long profileId) throws UserNotFoundException {
        ProfileEntity profile = profileRepository.findById(profileId).orElse(null);
        if (profile != null) {
            profileRepository.deleteById(profileId);
        } else
            throw new UserNotFoundException("ProfileId Not Found");
    }
}
