package com.hilbert.features.article.repository;

import com.hilbert.features.article.model.ArticleComment;
import com.hilbert.shared.search.models.PaginatedResults;
import com.hilbert.shared.search.models.SearchParams;

public interface ArticleCommentSearchRepository {

    PaginatedResults<ArticleComment> searchComments(SearchParams searchParams);
}
