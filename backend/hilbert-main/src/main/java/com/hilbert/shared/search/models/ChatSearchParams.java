package com.hilbert.shared.search.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ChatSearchParams extends SearchParams {
    private Integer userId;

    public ChatSearchParams(Integer userId,
                               String searchQuery, String sortBy, Boolean isAscending,
                               Integer page, Integer itemsPerPage) {
        super(searchQuery, sortBy, isAscending, page, itemsPerPage);
        this.userId = userId;
    }
}

