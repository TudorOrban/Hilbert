package com.hilbert.features.learningprofile.model;

import com.hilbert.shared.common.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearningProfileData {

    private LocalDateTime startDate;
    private LocalDateTime lastActiveDate;
    private Long totalLearningDays;
    private LocalDateTime currentStreakStartDate;
    private LocalDateTime currentStreakEndDate;
    private Long currentStreakDays;
    private Double totalXp;

    private Language nativeLanguage;
    private HashMap<Language, LearningLanguageData> languageData;
}
