package com.hilbert.shared.search.models;

import com.hilbert.features.article.model.DifficultyLevel;
import com.hilbert.features.article.model.Language;
import lombok.Data;

@Data
public class ArticleSearchParams extends SearchParams {
    private Integer userId;
    private Language language;
    private DifficultyLevel difficultyLevel;
}
