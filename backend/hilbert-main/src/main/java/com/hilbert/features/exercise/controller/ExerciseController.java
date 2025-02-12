package com.hilbert.features.exercise.controller;

import com.hilbert.features.exercise.dto.CreateExerciseDto;
import com.hilbert.features.exercise.dto.ExerciseAnswerDto;
import com.hilbert.features.exercise.dto.ExerciseFullDto;
import com.hilbert.features.exercise.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseFullDto> getExerciseById(@PathVariable Long id) {
        ExerciseFullDto exercise = exerciseService.getExerciseById(id);
        return ResponseEntity.ok(exercise);
    }

    @PostMapping
    public ResponseEntity<ExerciseFullDto> createExercise(@RequestBody CreateExerciseDto exerciseDto) {
        ExerciseFullDto exercise = exerciseService.createExercise(exerciseDto);
        return ResponseEntity.ok(exercise);
    }

    @PostMapping("/answer")
    public ResponseEntity<Boolean> answerExercise(@RequestBody ExerciseAnswerDto answerDto) {
        Boolean isCorrect = exerciseService.answerExercise(answerDto);
        return ResponseEntity.ok(isCorrect);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.ok().build();
    }
}

