package com.hilbert.features.exercise.dto;

import com.hilbert.features.exercise.model.ExerciseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseFullDto {
    private Long id;
    private Long creatorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ExerciseData exerciseData;
}
