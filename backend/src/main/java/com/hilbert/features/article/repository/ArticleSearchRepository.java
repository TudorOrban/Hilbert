package com.hilbert.features.article.repository;

import com.hilbert.features.article.model.Article;
import com.hilbert.shared.search.models.ArticleSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;

public interface ArticleSearchRepository {

    PaginatedResults<Article> searchArticles(ArticleSearchParams searchParams);
}
