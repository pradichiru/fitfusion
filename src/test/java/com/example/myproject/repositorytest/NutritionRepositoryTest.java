package com.example.myproject.repositorytest;
import com.example.myproject.entity.NutritionEntity;
import com.example.myproject.entity.NotificationEntity;
import com.example.myproject.repositary.LoginRepository;
import com.example.myproject.repositary.NutritionRepository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
public class NutritionRepositoryTest {

        @Mock
        private NutritionRepository nutritionRepository;

        @Mock
        private LoginRepository loginRepository;

        @InjectMocks
        private NutritionEntity nutritionEntity;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testFindById() {
            NutritionEntity nutrition = new NutritionEntity();
            nutrition.setId(1L);
            nutrition.setName("Apple");
            nutrition.setCategory("Fruit");
            nutrition.setCalories(52);
            nutrition.setNutrients("Vitamin C, Fiber");

            NotificationEntity notification = new NotificationEntity();
            notification.setId(1L);
            notification.setMessage("New nutrition added");

            nutrition.setNotificationEntity(notification);

            when(nutritionRepository.findById(1L)).thenReturn(Optional.of(nutrition));

            Optional<NutritionEntity> foundNutrition = nutritionRepository.findById(1L);

            assertEquals("Apple", foundNutrition.get().getName());
            assertEquals("Fruit", foundNutrition.get().getCategory());
            assertEquals(52, foundNutrition.get().getCalories());
        }

        @Test
        public void testSaveNutrition() {
            NutritionEntity nutrition = new NutritionEntity();
            nutrition.setId(2L);
            nutrition.setName("Spinach");
            nutrition.setCategory("Vegetable");
            nutrition.setCalories(23);
            nutrition.setNutrients("Iron, Vitamin A");

            NotificationEntity notification = new NotificationEntity();
            notification.setId(2L);
            notification.setMessage("New nutrition added");

            nutrition.setNotificationEntity(notification);

            when(nutritionRepository.save(any(NutritionEntity.class))).thenReturn(nutrition);

            NutritionEntity savedNutrition = nutritionRepository.save(nutrition);

            assertEquals(2L, savedNutrition.getId());
            assertEquals("Spinach", savedNutrition.getName());
            assertEquals("Vegetable", savedNutrition.getCategory());
        }

        @Test
        public void testDeleteNutrition() {
            NutritionEntity nutrition = new NutritionEntity();
            nutrition.setId(2L);
            nutrition.setName("Spinach");
            nutrition.setCategory("Vegetable");
            nutrition.setCalories(23);
            nutrition.setNutrients("Iron, Vitamin A");

            NotificationEntity notification = new NotificationEntity();
            notification.setId(2L);
            notification.setMessage("New nutrition added");

            nutrition.setNotificationEntity(notification);

            doNothing().when(nutritionRepository).delete(nutrition);
            nutritionRepository.delete(nutrition);

            verify(nutritionRepository, times(1)).delete(nutrition);
        }

        @Test
        public void testFindByIdNotFound() {
            when(nutritionRepository.findById(anyLong())).thenReturn(Optional.empty());

            Optional<NutritionEntity> foundNutrition = nutritionRepository.findById(10L);

            assertEquals(Optional.empty(), foundNutrition);
        }
    }


