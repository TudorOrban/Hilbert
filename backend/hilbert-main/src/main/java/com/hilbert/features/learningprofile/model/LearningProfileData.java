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
    private Integer totalLearningDays;
    private LocalDateTime currentStreakStartDate;
    private LocalDateTime currentStreakDays;
    private Double totalXp;

    private Language nativeLanguage;
    private HashMap<Language, LanguageProfileData> languageData;
}
