package com.example.myproject.dtotest;
import com.example.myproject.dto.ExerciseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseDtoTest {

        private Validator validator;

        @BeforeEach
        public void setUp() throws Exception {
            try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
                validator = factory.getValidator();
            }
        }

        @Test
        public void testExerciseDtoValid() {
            ExerciseDto exerciseDto = new ExerciseDto();
            exerciseDto.setId(1L);
            exerciseDto.setTitle("Sample Title");
            exerciseDto.setDescription("Sample Description");
            exerciseDto.setType("Sample Type");

            Set<ConstraintViolation<ExerciseDto>> violations = validator.validate(exerciseDto);
            assertTrue(violations.isEmpty(), "There should be no validation errors");
        }

        @Test
        public void testExerciseDtoInvalid() {
            ExerciseDto exerciseDto = new ExerciseDto();
            exerciseDto.setId(1L);
            exerciseDto.setTitle(""); // Invalid: blank
            exerciseDto.setDescription(""); // Invalid: blank
            exerciseDto.setType(""); // Invalid: blank

            Set<ConstraintViolation<ExerciseDto>> violations = validator.validate(exerciseDto);
            assertFalse(violations.isEmpty(), "There should be validation errors");

            for (ConstraintViolation<ExerciseDto> violation : violations) {
                System.out.println(violation.getMessage());
            }

            assertEquals(3, violations.size());
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("title must be provided")));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("description must be provided")));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("type must be provided")));
        }
    }

