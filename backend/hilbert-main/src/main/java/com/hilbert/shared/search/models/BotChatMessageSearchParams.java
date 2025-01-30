package com.hilbert.shared.search.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BotChatMessageSearchParams extends SearchParams {
    private Long chatId;

    public BotChatMessageSearchParams(Long chatId,
                                      String searchQuery, String sortBy, Boolean isAscending,
                                      Integer page, Integer itemsPerPage) {
        super(searchQuery, sortBy, isAscending, page, itemsPerPage);
        this.chatId = chatId;
    }
}

