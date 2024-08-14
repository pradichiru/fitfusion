package com.example.myproject.controller;
import com.example.myproject.dto.LoginDto;

import com.example.myproject.entity.LoginEntity;
import com.example.myproject.entity.LoginMapper;
import com.example.myproject.response.AuthenticationResponse;
import com.example.myproject.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class AuthenticationController {

        @Autowired
        private AuthenticationService authenticationService;
      @Autowired
      private LoginMapper loginMapper;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid LoginDto request) {
        LoginEntity loginEntity = loginMapper.toEntity(request);
        AuthenticationResponse response = authenticationService.register(loginEntity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody  LoginDto request) {
        LoginEntity loginEntity = loginMapper.toEntity(request);
        AuthenticationResponse response = authenticationService.login(loginEntity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    }

