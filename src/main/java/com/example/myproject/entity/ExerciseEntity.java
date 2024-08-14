package com.example.myproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "exercise")
@Entity
public class ExerciseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String type; // e.g., Cardio, Strength, Flexibility
    @OneToOne
    @JoinColumn(name ="notification_id",referencedColumnName = "id")
    private NotificationEntity notificationEntity;

}
