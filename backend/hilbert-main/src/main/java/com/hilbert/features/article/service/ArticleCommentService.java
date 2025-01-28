package com.hilbert.features.article.service;

import com.hilbert.features.article.dto.ArticleCommentDto;
import com.hilbert.features.article.dto.CreateArticleCommentDto;
import com.hilbert.shared.search.models.ArticleCommentSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;

public interface ArticleCommentService {

    PaginatedResults<ArticleCommentDto> searchComments(ArticleCommentSearchParams searchParams);
    ArticleCommentDto createComment(CreateArticleCommentDto commentDto);
    void deleteComment(Long id);
}
