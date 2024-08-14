package com.example.myproject.service;

import com.example.myproject.entity.LoginEntity;
import com.example.myproject.repositary.LoginRepository;
import com.example.myproject.response.AuthenticationResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class AuthenticationService {

        @Autowired
        private LoginRepository loginRepository;
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private JwtService jwtService;
        @Autowired
        private AuthenticationManager authenticationManager;

        public AuthenticationResponse register(LoginEntity request) {

            LoginEntity user = new LoginEntity();
            BeanUtils.copyProperties(request, user);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user = loginRepository.save(user);

            String token = jwtService.generateToken(user);
            return new AuthenticationResponse(token);
        }

        public AuthenticationResponse login(UserDetails request) {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            LoginEntity user = loginRepository.findByUsername(request.getUsername()).orElseThrow();
            String token = jwtService.generateToken(user);

            return new AuthenticationResponse(token);
        }
    }


