package com.hilbert.features.exercise.dto;

import com.hilbert.features.exercise.model.ExerciseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateExerciseDto {
    private Long id;
    private Long creatorId;
    private ExerciseData exerciseData;
}
