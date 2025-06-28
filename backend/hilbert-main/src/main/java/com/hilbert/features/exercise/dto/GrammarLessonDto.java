package com.hilbert.features.exercise.dto;

import com.hilbert.features.exercise.model.LessonData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrammarLessonDto {
    private Long id;
    private Long creatorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LessonData lessonData;
    private List<ExerciseFullDto> exercises;
}
