package com.example.myproject.repositary;

import com.example.myproject.entity.LoginEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<LoginEntity,Long> {
    Optional<LoginEntity> findByUsername(String username);
}
