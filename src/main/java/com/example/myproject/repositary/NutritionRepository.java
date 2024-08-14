package com.example.myproject.repositary;

import com.example.myproject.entity.NutritionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionRepository extends JpaRepository<NutritionEntity,Long> {
}
