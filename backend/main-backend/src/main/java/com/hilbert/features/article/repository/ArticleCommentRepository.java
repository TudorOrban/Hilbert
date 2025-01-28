package com.hilbert.features.article.repository;

import com.hilbert.features.article.model.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long>, ArticleCommentSearchRepository {

}
