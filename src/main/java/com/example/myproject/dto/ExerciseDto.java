package com.example.myproject.dto;

import com.example.myproject.entity.NotificationEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDto {
    private Long id;
    @NotBlank(message = "title must be provided")
    private String title;
    @NotBlank(message = "description must be provided")
    private String description;
    @NotBlank(message = "type must be provided")
    private String type;
    private NotificationEntity notificationEntity;
}
