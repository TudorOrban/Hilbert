package com.hilbert.features.article.service;

import com.hilbert.features.article.dto.ArticleFullDto;
import com.hilbert.features.article.dto.ArticleSearchDto;
import com.hilbert.features.article.dto.CreateArticleDto;
import com.hilbert.features.article.dto.UpdateArticleDto;
import com.hilbert.shared.search.models.ArticleSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;

public interface ArticleService {

    ArticleFullDto getArticleById(Long id);
    PaginatedResults<ArticleSearchDto> searchArticles(ArticleSearchParams searchParams);
    ArticleFullDto createArticle(CreateArticleDto articleDto);
    ArticleFullDto updateArticle(UpdateArticleDto articleDto);
    void deleteArticle(Long id);
}
