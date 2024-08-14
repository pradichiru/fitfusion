package com.example.myproject.dto;

import com.example.myproject.entity.NotificationEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NutritionDto {
    private Long id;
    @NotBlank(message = "name must be provided")
    private String name;
    @NotBlank(message = "category must be provided")
    private String category; // e.g., Fruit, Vegetable, Protein
    @NotNull(message = "calories must be provided")
    private Integer calories;
    private String nutrients;

    private NotificationEntity notificationEntity;
}
