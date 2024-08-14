package com.example.myproject.dtotest;
import com.example.myproject.dto.NotificationDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationDtoTest {

        private Validator validator;

        @BeforeEach
        public void setUp() throws Exception {
            try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
                validator = factory.getValidator();
            }
        }

        @Test
        public void testNotificationDtoValid() {
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setId(1L);
            notificationDto.setMessage("This is a test notification.");
            notificationDto.setTimestamp(LocalDateTime.now());
            notificationDto.setLoginEntity(null); // Assuming userEntity can be null

            Set<ConstraintViolation<NotificationDto>> violations = validator.validate(notificationDto);
            assertTrue(violations.isEmpty(), "There should be no validation errors");
        }

        @Test
        public void testNotificationDtoInvalid() {
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setId(1L);
            notificationDto.setMessage(""); // Invalid: blank
            notificationDto.setTimestamp(null); // Invalid: null
            notificationDto.setLoginEntity(null); // Assuming userEntity can be null

            Set<ConstraintViolation<NotificationDto>> violations = validator.validate(notificationDto);
            assertFalse(violations.isEmpty(), "There should be validation errors");

            for (ConstraintViolation<NotificationDto> violation : violations) {
                System.out.println(violation.getMessage());
            }

            assertEquals(2, violations.size());
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("message must be provided")));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Date must be provided")));
        }
    }

