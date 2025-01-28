package com.hilbert.shared.search.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ArticleCommentSearchParams extends SearchParams {
    private Integer articleId;

    public ArticleCommentSearchParams(Integer articleId,
                                      String searchQuery, String sortBy, Boolean isAscending,
                                      Integer page, Integer itemsPerPage) {
        super(searchQuery, sortBy, isAscending, page, itemsPerPage);
        this.articleId = articleId;
    }
}
