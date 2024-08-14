package com.example.myproject.entity;

import com.example.myproject.dto.LoginDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class LoginMapper {

        public LoginEntity toEntity(LoginDto loginDto) {
            // Map properties from LoginDto to LoginEntity
            LoginEntity loginEntity = new LoginEntity();
            BeanUtils.copyProperties(loginDto,loginEntity);
            return loginEntity;
        }
    }


