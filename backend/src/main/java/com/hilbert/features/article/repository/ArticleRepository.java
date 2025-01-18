package com.hilbert.features.article.repository;

import com.hilbert.features.article.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleSearchRepository {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM Article a WHERE a.userId = :userId AND a.title =:title")
    boolean hasUniqueTitle(@Param("userId") Long userId, @Param("title") String title);
}
