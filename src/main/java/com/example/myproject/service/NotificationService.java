package com.example.myproject.service;



import com.example.myproject.dto.NotificationDto;
import com.example.myproject.entity.NotificationEntity;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.repositary.NotificationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    public NotificationEntity dtoToEntity(NotificationDto notification){
        NotificationEntity notificationEntity=new NotificationEntity();
        BeanUtils.copyProperties(notification,notificationEntity);
        return notificationEntity;
    }

    public NotificationDto entityToDto(NotificationEntity notificationEntity){
        NotificationDto notificationDtoDto=new NotificationDto();
        BeanUtils.copyProperties(notificationEntity,notificationDtoDto);
        return notificationDtoDto;
    }

    public List<NotificationDto> getAllNotificationDetails() {
        List<NotificationDto> notification=new ArrayList<>();
        for(NotificationEntity notificationEntity: notificationRepository.findAll()){
            notification.add(entityToDto(notificationEntity));
        }
        return notification;
    }

    public NotificationDto getNotificationDetailsById(long id) throws UserNotFoundException {
        NotificationEntity notification = notificationRepository.findById(id).orElse(null);
        if (notification != null) {
            return entityToDto(notification);
        } else
            throw new UserNotFoundException("NotificationId Not Found");

    }

    public NotificationDto saveNotificationDetails(NotificationDto NotificationDetail) {
        NotificationEntity notification=dtoToEntity(NotificationDetail);
        return entityToDto(notificationRepository.save(notification));
    }

    public void deleteNotificationDetails(long id) throws UserNotFoundException {
        NotificationEntity notification = notificationRepository.findById(id).orElse(null);
        if (notification != null) {
            notificationRepository.deleteById(id);
        } else
            throw new UserNotFoundException("NotificationId not found");
    }
}
