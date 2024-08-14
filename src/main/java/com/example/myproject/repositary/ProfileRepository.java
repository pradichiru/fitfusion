package com.example.myproject.repositary;

import com.example.myproject.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity,Long> {
}
