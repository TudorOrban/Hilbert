package com.hilbert.features.article.repository;

import com.hilbert.features.article.model.ArticleComment;
import com.hilbert.shared.search.models.ArticleCommentSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;

public interface ArticleCommentSearchRepository {

    PaginatedResults<ArticleComment> searchComments(ArticleCommentSearchParams searchParams);
}
