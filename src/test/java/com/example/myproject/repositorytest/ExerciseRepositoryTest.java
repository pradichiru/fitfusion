package com.example.myproject.repositorytest;

import com.example.myproject.entity.ExerciseEntity;
import com.example.myproject.entity.NotificationEntity;
import com.example.myproject.repositary.ExerciseRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ExerciseRepositoryTest {

        @Mock
        private ExerciseRepository exerciseRepository;

        @InjectMocks
        private ExerciseEntity exerciseEntity;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testFindById() {
            NotificationEntity notification = new NotificationEntity();
            notification.setId(1L);
            notification.setMessage("Workout reminder");
            notification.setTimestamp(LocalDateTime.now());

            ExerciseEntity exercise = new ExerciseEntity();
            exercise.setId(1L);
            exercise.setTitle("Morning Run");
            exercise.setDescription("A 5km run to start the day");
            exercise.setType("Cardio");
            exercise.setNotificationEntity(notification);

            when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));

            Optional<ExerciseEntity> foundExercise = exerciseRepository.findById(1L);

            assertEquals("Morning Run", foundExercise.get().getTitle());
            assertEquals("Workout reminder", foundExercise.get().getNotificationEntity().getMessage());
        }

        @Test
        public void testSaveExercise() {
            NotificationEntity notification = new NotificationEntity();
            notification.setId(2L);
            notification.setMessage("Strength training session");
            notification.setTimestamp(LocalDateTime.now());

            ExerciseEntity exercise = new ExerciseEntity();
            exercise.setId(2L);
            exercise.setTitle("Weight Lifting");
            exercise.setDescription("Upper body strength training");
            exercise.setType("Strength");
            exercise.setNotificationEntity(notification);

            when(exerciseRepository.save(any(ExerciseEntity.class))).thenReturn(exercise);

            ExerciseEntity savedExercise = exerciseRepository.save(exercise);

            assertEquals(2L, savedExercise.getId());
            assertEquals("Weight Lifting", savedExercise.getTitle());
            assertEquals("Strength", savedExercise.getType());
            assertEquals("Strength training session", savedExercise.getNotificationEntity().getMessage());
        }

        @Test
        public void testDeleteExercise() {
            NotificationEntity notification = new NotificationEntity();
            notification.setId(3L);
            notification.setMessage("Yoga session");
            notification.setTimestamp(LocalDateTime.now());

            ExerciseEntity exercise = new ExerciseEntity();
            exercise.setId(3L);
            exercise.setTitle("Yoga");
            exercise.setDescription("Evening yoga session for relaxation");
            exercise.setType("Flexibility");
            exercise.setNotificationEntity(notification);

            doNothing().when(exerciseRepository).delete(exercise);
            exerciseRepository.delete(exercise);

            verify(exerciseRepository, times(1)).delete(exercise);
        }

        @Test
        public void testFindByIdNotFound() {
            when(exerciseRepository.findById(anyLong())).thenReturn(Optional.empty());

            Optional<ExerciseEntity> foundExercise = exerciseRepository.findById(10L);

            assertEquals(Optional.empty(), foundExercise);
        }
    }

