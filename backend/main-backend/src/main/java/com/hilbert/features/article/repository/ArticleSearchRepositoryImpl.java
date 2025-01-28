package com.hilbert.features.article.repository;


import com.hilbert.features.article.model.Article;
import com.hilbert.shared.search.models.ArticleSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleSearchRepositoryImpl implements ArticleSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public PaginatedResults<Article> searchArticles(ArticleSearchParams searchParams) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Article> query = builder.createQuery(Article.class);
        Root<Article> root = query.from(Article.class);

        Predicate conditions = getConditions(builder, root, searchParams);
        query.where(conditions);

        if (searchParams.getIsAscending()) {
            query.orderBy(builder.asc(root.get(searchParams.getSortBy())));
        } else {
            query.orderBy(builder.desc(root.get(searchParams.getSortBy())));
        }

        List<Article> articles = entityManager.createQuery(query)
                .setFirstResult((searchParams.getPage() - 1) * searchParams.getItemsPerPage())
                .setMaxResults(searchParams.getItemsPerPage())
                .getResultList();

        // Query total count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<Article> countRoot = countQuery.from(Article.class);
        Predicate countConditions = getConditions(countBuilder, countRoot, searchParams);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(countConditions);

        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(articles, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<Article> root, ArticleSearchParams searchParams) {
        Predicate conditions = builder.conjunction();

        if (searchParams.getUserId() != null) {
            conditions = builder.and(conditions, builder.equal(root.get("userId"), searchParams.getUserId()));
        }
        if (searchParams.getLanguage() != null) {
            conditions = builder.and(conditions, builder.equal(root.get("language"), searchParams.getLanguage()));
        }
        if (searchParams.getDifficultyLevel() != null) {
            conditions = builder.and(conditions, builder.equal(root.get("level"), searchParams.getDifficultyLevel()));
        }
        if (searchParams.getSearchQuery() != null) {
            conditions = builder.and(conditions, builder.like(root.get("title"), "%" + searchParams.getSearchQuery() + "%"));
        }

        return conditions;
    }
}
