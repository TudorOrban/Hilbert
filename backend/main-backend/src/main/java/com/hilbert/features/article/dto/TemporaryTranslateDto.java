package com.hilbert.features.article.dto;

import com.hilbert.shared.common.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemporaryTranslateDto {
    String content;
    Language srcLanguage;
    Language destLanguage;
}
