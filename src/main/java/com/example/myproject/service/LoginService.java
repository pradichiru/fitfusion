package com.example.myproject.service;


import com.example.myproject.dto.LoginDto;
import com.example.myproject.entity.LoginEntity;

import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.repositary.LoginRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loginRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    public LoginEntity dtoToEntity(LoginDto user){
        LoginEntity userEntity=new LoginEntity();
        BeanUtils.copyProperties(user,userEntity);
        return userEntity;
    }

    public LoginDto entityToDto(LoginEntity userEntity){
        LoginDto userDto=new LoginDto();
        BeanUtils.copyProperties(userEntity,userDto);
        return userDto;
    }

    public List<LoginDto> getAllUserDetails() {
        List<LoginDto> user=new ArrayList<>();
        for(LoginEntity busDetailsEntity: loginRepository.findAll()){
            user.add(entityToDto(busDetailsEntity));
        }
        return user;
    }

    public LoginDto getUserDetailsById(long id) throws UserNotFoundException {
        LoginEntity user = loginRepository.findById(id).orElse(null);
        if (user != null) {
            return entityToDto(user);
        } else
            throw new UserNotFoundException("UserId Not Found");

    }

    public LoginDto saveUserDetails(LoginDto userDetails) {
        LoginEntity user=dtoToEntity(userDetails);
        return entityToDto(loginRepository.save(user));
    }
    public LoginDto create(LoginDto userDetails) {
        return saveUserDetails(userDetails);
    }

    public void deleteUserDetails(long id) throws UserNotFoundException {
        LoginEntity user = loginRepository.findById(id).orElse(null);
        if (user != null) {
            loginRepository.deleteById(id);
        } else
            throw new UserNotFoundException("UserId not found");
    }
}
