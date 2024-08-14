package com.example.myproject.controllertest;

import com.example.myproject.controller.ExerciseController;
import com.example.myproject.dto.ExerciseDto;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.service.ExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class ExerciseControllerTest {

        @Mock
        private ExerciseService exerciseService;

        @InjectMocks
        private ExerciseController exerciseController;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testCreateSuccess() {
            ExerciseDto dto = new ExerciseDto();
            ExerciseDto createdDto = new ExerciseDto();
            createdDto.setId(1L);
            createdDto.setTitle("Sample Title");
            createdDto.setDescription("Sample Description");
            createdDto.setType("Sample Type");

            when(exerciseService.saveExerciseDetails(any(ExerciseDto.class))).thenReturn(createdDto);

            ResponseEntity<ExerciseDto> response = exerciseController.createUser(dto);
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(createdDto, response.getBody());
        }

        @Test
        public void testGetAllSuccess() {
            ExerciseDto dto1 = new ExerciseDto();
            ExerciseDto dto2 = new ExerciseDto();
            List<ExerciseDto> dtos = Arrays.asList(dto1, dto2);

            when(exerciseService.getAllExerciseDetails()).thenReturn(dtos);

            ResponseEntity<List<ExerciseDto>> response = exerciseController.getAllUsers();
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(dtos, response.getBody());
        }

        @Test
        public void testGetBySubCodeSuccess() throws UserNotFoundException {
            long id = 10;
            ExerciseDto dto = new ExerciseDto();

            when(exerciseService.getExerciseDetailsById(id)).thenReturn(dto);

            ResponseEntity<ExerciseDto> response = exerciseController.getUserById(id);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(dto, response.getBody());
        }

        @Test
        public void testGetBySubCodeNotFound() throws UserNotFoundException {
            long id = 10;
            when(exerciseService.getExerciseDetailsById(id)).thenThrow(new UserNotFoundException("Exercise Not Found"));

            try {
                exerciseController.getUserById(id);
            } catch (UserNotFoundException e) {
                assertEquals("Exercise Not Found", e.getMessage());
            }
        }

        @Test
        public void testUpdateSuccess() throws UserNotFoundException {
            long id = 10;
            ExerciseDto dto = new ExerciseDto();
            dto.setId(id);

            when(exerciseService.saveExerciseDetails(any(ExerciseDto.class))).thenReturn(dto);

            ResponseEntity<ExerciseDto> response = exerciseController.updateUser(id, dto);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(dto, response.getBody());
        }

        @Test
        public void testUpdateSubjectNotFound() throws UserNotFoundException {
            long id = 10;
            ExerciseDto dto = new ExerciseDto();
            dto.setId(id);

            when(exerciseService.getExerciseDetailsById(id)).thenThrow(new UserNotFoundException("Exercise Not Found"));

            try {
                exerciseController.updateUser(id, dto);
            } catch (UserNotFoundException e) {
                assertEquals("Exercise Not Found", e.getMessage());
            }
        }

        @Test
        public void testDeleteSuccess() throws UserNotFoundException {
            long id = 10;
            ResponseEntity<String> response = exerciseController.deleteUser(id);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Deleted exercise " + id, response.getBody());
            verify(exerciseService, times(1)).deleteExerciseDetails(id);
        }

        @Test
        public void testDeleteSubjectNotFound() throws UserNotFoundException {
            long id = 10;
            doThrow(new UserNotFoundException("Exercise Not Found")).when(exerciseService).deleteExerciseDetails(id);

            try {
                exerciseController.deleteUser(id);
            } catch (UserNotFoundException e) {
                assertEquals("Exercise Not Found", e.getMessage());
            }
        }

    }

