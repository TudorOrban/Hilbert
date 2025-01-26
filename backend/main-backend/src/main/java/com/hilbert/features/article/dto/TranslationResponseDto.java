package com.hilbert.features.article.dto;

import com.hilbert.shared.common.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslationResponseDto {

    private HashMap<String, Set<String>> translation;
    private Language srcLanguage;
    private Language destLanguage;
}
