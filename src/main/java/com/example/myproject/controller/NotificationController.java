package com.example.myproject.controller;


import com.example.myproject.dto.NotificationDto;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @PostMapping("/create")
    public ResponseEntity<NotificationDto> createNotification(@RequestBody @Valid NotificationDto notificationDetailsEntity) {
        NotificationDto notification=notificationService.saveNotificationDetails(notificationDetailsEntity);
        return  new ResponseEntity<>(notification, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<NotificationDto>> getAllNotification() {
        return new ResponseEntity<>(notificationService.getAllNotificationDetails(), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable long id) throws UserNotFoundException {
        return new ResponseEntity<>(notificationService.getNotificationDetailsById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<NotificationDto> updateNotification(@PathVariable long id, @RequestBody @Valid NotificationDto notificationDetailsEntity) throws UserNotFoundException {
        notificationService.getNotificationDetailsById(id);
        notificationDetailsEntity.setId(id);
        return new ResponseEntity<>(notificationService.saveNotificationDetails(notificationDetailsEntity), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) throws UserNotFoundException {
        notificationService.deleteNotificationDetails(id);
        return ResponseEntity.ok("Deleted notification "+id);
    }

}
