package com.hilbert.features.exercise.service;

import com.hilbert.features.exercise.dto.CreateExerciseDto;
import com.hilbert.features.exercise.dto.ExerciseAnswerDto;
import com.hilbert.features.exercise.dto.ExerciseFullDto;
import com.hilbert.features.exercise.dto.ExerciseMapper;
import com.hilbert.features.exercise.model.Exercise;
import com.hilbert.features.exercise.repository.ExerciseRepository;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseAnswerService answerService;

    @Autowired
    public ExerciseServiceImpl(
            ExerciseRepository exerciseRepository,
            ExerciseAnswerService answerService
    ) {
        this.exerciseRepository = exerciseRepository;
        this.answerService = answerService;
    }

    public ExerciseFullDto getExerciseById(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.EXERCISE, ResourceIdentifierType.ID));

        return this.mapExerciseToExerciseFullDto(exercise);
    }

    public ExerciseFullDto createExercise(CreateExerciseDto exerciseDto) {
        Exercise exercise = this.mapCreateExerciseDtoToExercise(exerciseDto);

        Exercise savedExercise = exerciseRepository.save(exercise);

        return this.mapExerciseToExerciseFullDto(savedExercise);
    }

    public Boolean answerExercise(ExerciseAnswerDto answerDto) {
        Exercise exercise = exerciseRepository.findById(answerDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(answerDto.getId().toString(), ResourceType.EXERCISE, ResourceIdentifierType.ID));

        // TODO: Save to db

        return answerService.verifyExerciseAnswer(exercise, answerDto.getAnswerData());
    }

    public void deleteExercise(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.EXERCISE, ResourceIdentifierType.ID));

        exerciseRepository.delete(exercise);
    }

    private ExerciseFullDto mapExerciseToExerciseFullDto(Exercise exercise) {
        return ExerciseMapper.INSTANCE.exerciseToExerciseFullDto(exercise);
    }

    private Exercise mapCreateExerciseDtoToExercise(CreateExerciseDto exerciseDto) {
        return ExerciseMapper.INSTANCE.createExerciseDtoToExercise(exerciseDto);
    }
}
