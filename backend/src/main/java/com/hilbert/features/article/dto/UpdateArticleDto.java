package com.hilbert.features.article.dto;

import com.hilbert.shared.common.enums.DifficultyLevel;
import com.hilbert.shared.common.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArticleDto {
    private Long id;
    private String title;
    private String content;
    private Language language;
    private DifficultyLevel level;
}
