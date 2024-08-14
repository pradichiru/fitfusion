package com.example.myproject.service;

import com.example.myproject.dto.NutritionDto;
import com.example.myproject.entity.NutritionEntity;
import com.example.myproject.exception.UserNotFoundException;
import com.example.myproject.repositary.NutritionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NutritionService {
    @Autowired
    private NutritionRepository nutritionRepository;
    public NutritionEntity dtoToEntity(NutritionDto nutrition){
        NutritionEntity nutritionEntity=new NutritionEntity();
        BeanUtils.copyProperties(nutrition,nutritionEntity);
        return nutritionEntity;
    }

    public NutritionDto entityToDto(NutritionEntity nutritionEntity){
        NutritionDto nutritionDto=new NutritionDto();
        BeanUtils.copyProperties(nutritionEntity,nutritionDto);
        return nutritionDto;
    }

    public List<NutritionDto> getAllNutritionDetails() {
        List<NutritionDto> nutrition=new ArrayList<>();
        for(NutritionEntity nutritionEntryEntity: nutritionRepository.findAll()){
            nutrition.add(entityToDto(nutritionEntryEntity));
        }
        return nutrition;
    }

    public NutritionDto getNutritionDetailsById(long id) throws UserNotFoundException {
        NutritionEntity nutrition = nutritionRepository.findById(id).orElse(null);
        if (nutrition != null) {
            return entityToDto(nutrition);
        } else
            throw new UserNotFoundException("NutritionId Not Found");

    }

    public NutritionDto saveNutritionDetails(NutritionDto nutritionDetails) {
        NutritionEntity nutrition=dtoToEntity(nutritionDetails);
        return entityToDto(nutritionRepository.save(nutrition));
    }

    public void deleteNutrionDetails(long id) throws UserNotFoundException {
        NutritionEntity nutrition = nutritionRepository.findById(id).orElse(null);
        if (nutrition != null) {
            nutritionRepository.deleteById(id);
        } else
            throw new UserNotFoundException("NutritionId not found");
    }

}
