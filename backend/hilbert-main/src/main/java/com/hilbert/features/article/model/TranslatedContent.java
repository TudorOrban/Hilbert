package com.hilbert.features.article.model;

import com.hilbert.shared.common.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslatedContent {

    private HashMap<String, Set<String>> translationMap;
    private Language srcLanguage;
    private Language destLanguage;
}
