package com.example.myproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userActivity")
@Entity
public class UserActivityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String activityType; // e.g., Logged in, Created Profile, etc.
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private LoginEntity loginEntity ;
}
