package com.hilbert.shared.search.models;

import com.hilbert.shared.common.enums.DifficultyLevel;
import com.hilbert.shared.common.enums.Language;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserSearchParams extends SearchParams {
    private Map<Language, DifficultyLevel> languages;

    public UserSearchParams(Map<Language, DifficultyLevel> languages,
                            String searchQuery, String sortBy, Boolean isAscending,
                            Integer page, Integer itemsPerPage) {
        super(searchQuery, sortBy, isAscending, page, itemsPerPage);
        this.languages = languages;
    }
}

