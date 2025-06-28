package com.hilbert.features.exercise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLessonDto {
    private Long creatorId;
    private List<LessonExercise> exercises;
    private Boolean keepOrder;
}
