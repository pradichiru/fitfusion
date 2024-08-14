package com.example.myproject.dtotest;
import com.example.myproject.dto.ProfileDto;
import com.example.myproject.entity.LoginEntity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileDtoTest {

        private Validator validator;

        @BeforeEach
        public void setUp() {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }

        @Test
        public void testProfileDtoValid() {
            ProfileDto profileDto = new ProfileDto();
            profileDto.setFirstName("John");
            profileDto.setLastName("Doe");
            profileDto.setBio("Computer Scientist");
            profileDto.setLoginEntity(new LoginEntity()); // Assuming you have a valid UserEntity

            Set<ConstraintViolation<ProfileDto>> violations = validator.validate(profileDto);
            assertTrue(violations.isEmpty(), "There should be no validation errors");
        }

        @Test
        public void testProfileDtoInvalid() {
            ProfileDto profileDto = new ProfileDto();
            profileDto.setFirstName(""); // Invalid: blank
            profileDto.setLastName(""); // Valid but no constraint
            profileDto.setBio(""); // Invalid: blank
            profileDto.setLoginEntity(null); // Invalid: null

            Set<ConstraintViolation<ProfileDto>> violations = validator.validate(profileDto);
            assertFalse(violations.isEmpty(), "There should be validation errors");

            for (ConstraintViolation<ProfileDto> violation : violations) {
                System.out.println(violation.getMessage());
            }

            assertEquals(3, violations.size()); // Adjust based on actual constraints

            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Username must be provided")));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Bio must be provided")));
        }
    }

