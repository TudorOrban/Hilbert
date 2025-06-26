package com.hilbert.features.exercise.dto;

import com.hilbert.features.exercise.model.ExerciseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonExercise {
    private LessonExerciseType exerciseType;
    private Long existingExerciseId;
    private ExerciseData newExerciseData;
}
