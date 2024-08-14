package com.example.myproject.dto;

import com.example.myproject.entity.LoginEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private Long profileId; // Separate primary key
    @NotBlank(message = "Username must be provided")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String firstName;
    private String lastName;
    @NotBlank(message = "Bio must be provided")
    private String bio;
    private LoginEntity loginEntity ;

}
