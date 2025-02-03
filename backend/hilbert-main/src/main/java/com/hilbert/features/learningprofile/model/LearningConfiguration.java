package com.hilbert.features.learningprofile.model;

import com.hilbert.shared.common.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearningConfiguration {

    private HashMap<Language, LanguageConfiguration> languageData;
}
