package com.hilbert.features.exercise.dto;

import com.hilbert.features.exercise.model.ExerciseAnswerData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseAnswerDto {
    private Long id;
    private Long userId;
    private ExerciseAnswerData answerData;
}
