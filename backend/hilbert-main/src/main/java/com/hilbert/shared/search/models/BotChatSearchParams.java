package com.hilbert.shared.search.models;

import com.hilbert.shared.common.enums.Language;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BotChatSearchParams extends SearchParams {
    private Long userId;
    private Language language;

    public BotChatSearchParams(Long userId, Language language,
                               String searchQuery, String sortBy, Boolean isAscending,
                               Integer page, Integer itemsPerPage) {
        super(searchQuery, sortBy, isAscending, page, itemsPerPage);
        this.userId = userId;
        this.language = language;
    }
}

