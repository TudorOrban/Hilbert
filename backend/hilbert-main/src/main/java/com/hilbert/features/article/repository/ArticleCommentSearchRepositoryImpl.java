package com.hilbert.features.article.repository;

import com.hilbert.features.article.model.ArticleComment;
import com.hilbert.shared.search.models.ArticleCommentSearchParams;
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
public class ArticleCommentSearchRepositoryImpl implements ArticleCommentSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public PaginatedResults<ArticleComment> searchComments(ArticleCommentSearchParams searchParams) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ArticleComment> query = builder.createQuery(ArticleComment.class);
        Root<ArticleComment> root = query.from(ArticleComment.class);

        Predicate conditions = getConditions(builder, root, searchParams);
        query.where(conditions);

        if (searchParams.getIsAscending()) {
            query.orderBy(builder.asc(root.get(searchParams.getSortBy())));
        } else {
            query.orderBy(builder.desc(root.get(searchParams.getSortBy())));
        }

        List<ArticleComment> comments = entityManager.createQuery(query)
                .setFirstResult((searchParams.getPage() - 1) * searchParams.getItemsPerPage())
                .setMaxResults(searchParams.getItemsPerPage())
                .getResultList();

        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<ArticleComment> countRoot = countQuery.from(ArticleComment.class);
        Predicate countConditions = getConditions(countBuilder, countRoot, searchParams);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(countConditions);

        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(
                comments, totalCount
        );
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<ArticleComment> root, ArticleCommentSearchParams searchParams) {
        Predicate conditions = builder.conjunction();

        if (searchParams.getArticleId() != null) {
            conditions = builder.and(conditions, builder.equal(root.get("articleId"), searchParams.getArticleId()));
        }

        return conditions;
    }
}
