package com.hilbert.features.exercise.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonData {
    private List<Long> exerciseIds;
    private Boolean keepOrder;
}
