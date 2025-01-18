package com.hilbert.features.article.dto;

import com.hilbert.features.article.model.DifficultyLevel;
import com.hilbert.features.article.model.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArticleDto {
    private Long userId;
    private String title;
    private String content;
    private Language language;
    private DifficultyLevel level;
}
