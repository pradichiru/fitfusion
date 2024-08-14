package com.example.myproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profiles")
@Entity
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId; // Separate primary key
    private String firstName;
    private String lastName;
    private String bio;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private LoginEntity loginEntity ;
}
