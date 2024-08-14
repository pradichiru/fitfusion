package com.example.myproject.controller;

import com.example.myproject.dto.NutritionDto;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.service.NutritionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/nutrition")
public class NutritionController {
    @Autowired
    private NutritionService nutritionService;
    @PostMapping("/create")
    public ResponseEntity<NutritionDto> createNutrition(@RequestBody @Valid NutritionDto nutritionDetailsEntity) {
        NutritionDto nutrition=nutritionService.saveNutritionDetails(nutritionDetailsEntity);
        return  new ResponseEntity<>(nutrition, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<NutritionDto>> getAllNutrition() {
        return new ResponseEntity<>(nutritionService.getAllNutritionDetails(), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<NutritionDto> getNutritionById(@PathVariable long id) throws UserNotFoundException {
        return new ResponseEntity<>(nutritionService.getNutritionDetailsById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<NutritionDto> updateNutrition(@PathVariable long id, @RequestBody @Valid NutritionDto nutritionDetailsEntity) throws UserNotFoundException {
        nutritionService.getNutritionDetailsById(id);
        nutritionDetailsEntity.setId(id);
        return new ResponseEntity<>(nutritionService.saveNutritionDetails(nutritionDetailsEntity), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNutrition(@PathVariable long id) throws UserNotFoundException {
        nutritionService.deleteNutrionDetails(id);
        return ResponseEntity.ok("Deleted nutrition "+id);
    }
}
