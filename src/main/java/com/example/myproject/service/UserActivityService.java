package com.example.myproject.service;


import com.example.myproject.dto.UserActivityDto;
import com.example.myproject.entity.UserActivityEntity;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.repositary.UserActivityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserActivityService {
    @Autowired
    private UserActivityRepository userActivityRepository;
    public UserActivityEntity dtoToEntity(UserActivityDto activity){
        UserActivityEntity exerciseEntity=new UserActivityEntity();
        BeanUtils.copyProperties(activity,exerciseEntity);
        return exerciseEntity;
    }

    public UserActivityDto entityToDto(UserActivityEntity activityEntity){
        UserActivityDto exerciseDto=new UserActivityDto();
        BeanUtils.copyProperties(activityEntity,exerciseDto);
        return exerciseDto;
    }

    public List<UserActivityDto> getAllActivityDetails() {
        List<UserActivityDto> activity=new ArrayList<>();
        for(UserActivityEntity activityEntity: userActivityRepository.findAll()){
            activity.add(entityToDto(activityEntity));
        }
        return activity;
    }

    public UserActivityDto getActivityDetailsById(long id) throws UserNotFoundException {
        UserActivityEntity activity = userActivityRepository.findById(id).orElse(null);
        if (activity != null) {
            return entityToDto(activity);
        } else
            throw new UserNotFoundException("ActivityId Not Found");

    }

    public UserActivityDto saveActivityDetails(UserActivityDto ActivityDetail) {
        UserActivityEntity activity=dtoToEntity(ActivityDetail);
        return entityToDto(userActivityRepository.save(activity));
    }

    public void deleteActivityDetails(long id) throws UserNotFoundException {
        UserActivityEntity activity = userActivityRepository.findById(id).orElse(null);
        if (activity != null) {
            userActivityRepository.deleteById(id);
        } else
            throw new UserNotFoundException("ActivityId not found");
    }
}
