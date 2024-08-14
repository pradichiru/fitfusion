package com.example.myproject.dtotest;
import com.example.myproject.dto.UserActivityDto;

import com.example.myproject.entity.LoginEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserActivityDtoTest {

        private Validator validator;

        @BeforeEach
        public void setUp() throws Exception {
            try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
                validator = factory.getValidator();
            }
        }

        @Test
        public void testUserActivityDtoValid() {
            UserActivityDto userActivityDto = new UserActivityDto();
            userActivityDto.setId(1L);
            userActivityDto.setActivityType("Logged in");
            userActivityDto.setTimestamp(LocalDateTime.now());
            LoginEntity userEntity = new LoginEntity();
            userEntity.setId(1234567890123L);
            userActivityDto.setLoginEntity(userEntity);

            Set<ConstraintViolation<UserActivityDto>> violations = validator.validate(userActivityDto);
            assertTrue(violations.isEmpty(), "There should be no validation errors");
        }

        @Test
        public void testUserActivityDtoInvalid() {
            UserActivityDto userActivityDto = new UserActivityDto();
            userActivityDto.setId(1L);
            userActivityDto.setActivityType(""); // Invalid: blank
            userActivityDto.setTimestamp(null); // Invalid: null
            userActivityDto.setLoginEntity(null); // Invalid: null

            Set<ConstraintViolation<UserActivityDto>> violations = validator.validate(userActivityDto);
            assertFalse(violations.isEmpty(), "There should be validation errors");

            for (ConstraintViolation<UserActivityDto> violation : violations) {
                System.out.println(violation.getMessage());
            }

            assertEquals(3, violations.size());
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("ActivityType must be provided")));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Date must be provided")));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("must not be null")));
        }
    }

