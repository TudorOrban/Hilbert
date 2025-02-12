package com.hilbert.features.exercise.service;

import com.hilbert.features.exercise.dto.CreateExerciseDto;
import com.hilbert.features.exercise.dto.ExerciseAnswerDto;
import com.hilbert.features.exercise.dto.ExerciseFullDto;

public interface ExerciseService {

    ExerciseFullDto getExerciseById(Long id);
    ExerciseFullDto createExercise(CreateExerciseDto exerciseDto);
    Boolean answerExercise(ExerciseAnswerDto answerDto);
    void deleteExercise(Long id);
}
