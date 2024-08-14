package com.example.myproject.repositary;

import com.example.myproject.entity.UserActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityRepository extends JpaRepository<UserActivityEntity,Long> {
}
