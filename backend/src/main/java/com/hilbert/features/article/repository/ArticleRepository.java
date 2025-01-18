package com.hilbert.features.article.repository;

import com.hilbert.features.article.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleSearchRepository {

}
