package com.hilbert.features.exercise.dto;

import com.hilbert.features.exercise.model.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExerciseMapper {
    ExerciseMapper INSTANCE = Mappers.getMapper(ExerciseMapper.class);

    @Mapping(source = "exercise.id", target = "id")
    @Mapping(source = "exercise.creatorId", target = "creatorId")
    @Mapping(source = "exercise.createdAt", target = "createdAt")
    @Mapping(source = "exercise.updatedAt", target = "updatedAt")
    @Mapping(source = "exercise.exerciseData", target = "exerciseData")
    ExerciseFullDto exerciseToExerciseFullDto(Exercise exercise);

    @Mapping(source = "exercise.creatorId", target = "creatorId")
    @Mapping(source = "exercise.exerciseData", target = "exerciseData")
    Exercise createExerciseDtoToExercise(CreateExerciseDto exerciseDto);

}
