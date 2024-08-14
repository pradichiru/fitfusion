package com.example.myproject.dto;

import com.example.myproject.entity.LoginEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserActivityDto {
    private Long id;
    @NotBlank(message = "ActivityType must be provided")
    private String activityType; // e.g., Logged in, Created Profile, etc.
    @NotNull(message = "Date must be provided")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    @NotNull(message ="must not be null" )
    private LoginEntity loginEntity ;
}
