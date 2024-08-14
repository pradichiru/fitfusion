package com.example.myproject.repositary;

import com.example.myproject.entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity,Long> {
}
