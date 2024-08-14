package com.example.myproject.controller;


import com.example.myproject.dto.UserActivityDto;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.service.UserActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/userActivity")
public class UserActivityController {
    @Autowired
    private UserActivityService userActivityService;
    @PostMapping("/create")
    public ResponseEntity<UserActivityDto> createUser(@RequestBody @Valid UserActivityDto userDetailsEntity) {
        UserActivityDto u=userActivityService.saveActivityDetails(userDetailsEntity);
        return  new ResponseEntity<>(u, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserActivityDto>> getAllUsers() {
        return new ResponseEntity<>(userActivityService.getAllActivityDetails(), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<UserActivityDto> getUserById(@PathVariable long id) throws UserNotFoundException {
        return new ResponseEntity<>(userActivityService.getActivityDetailsById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserActivityDto> updateUser(@PathVariable long id, @RequestBody @Valid UserActivityDto userDetailsEntity) throws UserNotFoundException {
        userActivityService.getActivityDetailsById(id);
        userDetailsEntity.setId(id);
        return new ResponseEntity<>(userActivityService.saveActivityDetails(userDetailsEntity), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) throws UserNotFoundException {
        userActivityService.deleteActivityDetails(id);
        return ResponseEntity.ok("Deleted activity "+id);
    }
}
