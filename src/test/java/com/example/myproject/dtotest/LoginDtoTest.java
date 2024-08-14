package com.example.myproject.dtotest;
import com.example.myproject.dto.LoginDto;
import com.example.myproject.resource.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LoginDtoTest {


        private Validator validator;

        @BeforeEach
        public void setUp() throws Exception {
            try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
                validator = factory.getValidator();
            }
        }

        @Test
        public void testLoginDtoValid() {
            LoginDto loginDto = new LoginDto();
            loginDto.setId(1234567890123L);
            loginDto.setName("John Doe");
            loginDto.setUsername("john.doe");
            loginDto.setPassword("Password123");
            loginDto.setEmail("john.doe@example.com");
            loginDto.setRole(Role.ADMIN);

            Set<ConstraintViolation<LoginDto>> violations = validator.validate(loginDto);
            assertTrue(violations.isEmpty(), "There should be no validation errors");
        }

        @Test
        public void testLoginDtoInvalid() {
            LoginDto loginDto = new LoginDto();
            loginDto.setId(1234567890123L);
            loginDto.setName(""); // Invalid: blank
            loginDto.setUsername(""); // Invalid: blank
            loginDto.setPassword("short"); // Invalid: less than 8 characters
            loginDto.setEmail("invalid-email"); // Invalid: not a valid email format
            loginDto.setRole(null); // Invalid: null

            Set<ConstraintViolation<LoginDto>> violations = validator.validate(loginDto);
            assertFalse(violations.isEmpty(), "There should be validation errors");

            for (ConstraintViolation<LoginDto> violation : violations) {
                System.out.println(violation.getMessage());
            }

            assertEquals(5, violations.size());
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("name must be provided")));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Username must be provided")));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Password must be at least 8 characters long")));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Email should be valid")));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("must not be null")));
        }
    }






