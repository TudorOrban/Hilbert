package com.hilbert.shared.search.models;

import com.hilbert.shared.common.enums.DifficultyLevel;
import com.hilbert.shared.common.enums.Language;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleSearchParams extends SearchParams {
    private Integer userId;
    private Language language;
    private DifficultyLevel difficultyLevel;

    public ArticleSearchParams(Integer userId, Language language, DifficultyLevel difficultyLevel,
                               String searchQuery, String sortBy, Boolean isAscending,
                               Integer page, Integer itemsPerPage) {
        super(searchQuery, sortBy, isAscending, page, itemsPerPage);
        this.userId = userId;
        this.language = language;
        this.difficultyLevel = difficultyLevel;
    }
}
