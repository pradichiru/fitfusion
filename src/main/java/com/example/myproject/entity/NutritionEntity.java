package com.example.myproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "nutrition")
@Entity
public class NutritionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category; // e.g., Fruit, Vegetable, Protein
    private int calories;
    private String nutrients;
    @OneToOne
    @JoinColumn(name = "Notification_id",referencedColumnName = "id")
    private NotificationEntity notificationEntity;
}
