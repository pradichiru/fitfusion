package com.example.myproject.dto;

import com.example.myproject.resource.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {
    private long id;
    @NotBlank(message = "name must be provided")
    private String name;
    @NotBlank(message = "Username must be provided")
    private String username;
    @NotBlank(message = "Password must be provided")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    @NotBlank(message = "Email must be provided")
    @Email(message = "Email should be valid")
    private String email;
    @NotNull
    private Role role;
}
