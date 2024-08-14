package com.example.myproject.dtotest;
import com.example.myproject.dto.NutritionDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
public class NutritionDtoTest {

        private Validator validator;

        @BeforeEach
        public void setUp() {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }

        @Test
        public void testNutritionDtoValid() {
            NutritionDto nutritionDto = new NutritionDto();
            nutritionDto.setId(1L);
            nutritionDto.setName("Apple");
            nutritionDto.setCategory("Fruit");
            nutritionDto.setCalories(95);
            nutritionDto.setNutrients("Vitamin C, Fiber");

            Set<ConstraintViolation<NutritionDto>> violations = validator.validate(nutritionDto);
            assertTrue(violations.isEmpty(), "There should be no validation errors");
        }

        @Test
        public void testNutritionDtoInvalid() {
            NutritionDto nutritionDto = new NutritionDto();
            nutritionDto.setId(1L);
            nutritionDto.setName(""); // Invalid: blank
            nutritionDto.setCategory(""); // Invalid: blank
            nutritionDto.setCalories(null); // Assuming 0 is an invalid calorie count
            nutritionDto.setNutrients(""); // Assuming nutrients can be empty

            Set<ConstraintViolation<NutritionDto>> violations = validator.validate(nutritionDto);
            assertFalse(violations.isEmpty(), "There should be validation errors");

            for (ConstraintViolation<NutritionDto> violation : violations) {
                System.out.println(violation.getMessage());
            }

            assertEquals(3, violations.size());
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("name must be provided")));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("category must be provided")));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("calories must be provided")));
        }
    }

