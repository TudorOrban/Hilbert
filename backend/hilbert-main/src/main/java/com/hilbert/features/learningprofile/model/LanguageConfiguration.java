package com.hilbert.features.learningprofile.model;

import com.hilbert.shared.common.enums.DifficultyLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageConfiguration {

    private LanguageStatus status;
    private DifficultyLevel level;
}
