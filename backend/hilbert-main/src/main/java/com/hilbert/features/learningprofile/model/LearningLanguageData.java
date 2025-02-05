package com.hilbert.features.learningprofile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearningLanguageData {

    private LocalDateTime startDate;
    private LocalDateTime lastActiveDate;
    private Long totalLearningDays;
    private Double totalXp;

    private List<Long> readArticleIds;
    private Long vocabularySize;
}
