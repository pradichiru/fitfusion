package com.example.myproject.controller;

import com.example.myproject.dto.ExerciseDto;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/exercise")
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;
    @PostMapping("/create")
    public ResponseEntity<ExerciseDto> createUser(@RequestBody @Valid ExerciseDto userDetailsEntity) {
        ExerciseDto u=exerciseService.saveExerciseDetails(userDetailsEntity);
        return  new ResponseEntity<>(u, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ExerciseDto>> getAllUsers() {
        return new ResponseEntity<>(exerciseService.getAllExerciseDetails(), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ExerciseDto> getUserById(@PathVariable long id) throws UserNotFoundException {
        return new ResponseEntity<>(exerciseService.getExerciseDetailsById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ExerciseDto> updateUser(@PathVariable long id, @RequestBody @Valid ExerciseDto userDetailsEntity) throws UserNotFoundException {
        exerciseService.getExerciseDetailsById(id);
        userDetailsEntity.setId(id);
        return new ResponseEntity<>(exerciseService.saveExerciseDetails(userDetailsEntity), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) throws UserNotFoundException {
        exerciseService.deleteExerciseDetails(id);
        return ResponseEntity.ok("Deleted exercise "+id);
    }

}
