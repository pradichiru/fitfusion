package com.example.myproject.service;

import com.example.myproject.dto.ExerciseDto;
import com.example.myproject.entity.ExerciseEntity;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.repositary.ExerciseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;
    public ExerciseEntity dtoToEntity(ExerciseDto exercise){
        ExerciseEntity exerciseEntity=new ExerciseEntity();
        BeanUtils.copyProperties(exercise,exerciseEntity);
        return exerciseEntity;
    }

    public ExerciseDto entityToDto(ExerciseEntity exerciseEntity){
        ExerciseDto exerciseDto=new ExerciseDto();
        BeanUtils.copyProperties(exerciseEntity,exerciseDto);
        return exerciseDto;
    }

    public List<ExerciseDto> getAllExerciseDetails() {
        List<ExerciseDto> exercise=new ArrayList<>();
        for(ExerciseEntity exerciseEntity: exerciseRepository.findAll()){
            exercise.add(entityToDto(exerciseEntity));
        }
        return exercise;
    }

    public ExerciseDto getExerciseDetailsById(long id) throws UserNotFoundException {
        ExerciseEntity exercise = exerciseRepository.findById(id).orElse(null);
        if (exercise != null) {
            return entityToDto(exercise);
        } else
            throw new UserNotFoundException("ExerciseId Not Found");

    }

    public ExerciseDto saveExerciseDetails(ExerciseDto ExcerciseDetail) {
        ExerciseEntity exercise=dtoToEntity(ExcerciseDetail);
        return entityToDto(exerciseRepository.save(exercise));
    }

    public void deleteExerciseDetails(long id) throws UserNotFoundException {
        ExerciseEntity exercise = exerciseRepository.findById(id).orElse(null);
        if (exercise != null) {
            exerciseRepository.deleteById(id);
        } else
            throw new UserNotFoundException("ExerciseId not found");
    }
}
