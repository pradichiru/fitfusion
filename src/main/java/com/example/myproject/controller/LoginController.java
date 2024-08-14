package com.example.myproject.controller;
import com.example.myproject.dto.LoginDto;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {


        @Autowired
        private LoginService loginService;

        @PostMapping("/create")
        public ResponseEntity<LoginDto> createUser(@RequestBody @Valid LoginDto userDetails) {
            LoginDto createdUser = loginService.saveUserDetails(userDetails);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        }

        @GetMapping("/getAll")
        public ResponseEntity<List<LoginDto>> getAllUsers() {
            return new ResponseEntity<>(loginService.getAllUserDetails(), HttpStatus.OK);
        }

        @GetMapping("/getById/{id}")
        public ResponseEntity<LoginDto> getUserById(@PathVariable long id) throws UserNotFoundException {
            return new ResponseEntity<>(loginService.getUserDetailsById(id), HttpStatus.OK);
        }

        @PutMapping("/update/{id}")
        public ResponseEntity<LoginDto> updateUser(@PathVariable long id, @RequestBody @Valid LoginDto userDetails) throws UserNotFoundException {
            loginService.getUserDetailsById(id); // Verify user exists
            userDetails.setId(id); // Set the ID to ensure the correct user is updated
            return new ResponseEntity<>(loginService.saveUserDetails(userDetails), HttpStatus.ACCEPTED);
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<String> deleteUser(@PathVariable long id) throws UserNotFoundException {
            loginService.deleteUserDetails(id);
            return ResponseEntity.ok("Deleted user " + id);
        }
    }

