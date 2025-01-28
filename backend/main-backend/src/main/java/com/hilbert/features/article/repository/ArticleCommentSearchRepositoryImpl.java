package com.hilbert.features.article.repository;

import com.hilbert.features.article.model.ArticleComment;
import com.hilbert.shared.search.models.PaginatedResults;
import com.hilbert.shared.search.models.SearchParams;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleCommentSearchRepositoryImpl implements ArticleCommentSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public PaginatedResults<ArticleComment> searchComments(SearchParams searchParams) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ArticleComment> query = builder.createQuery(ArticleComment.class);
        Root<ArticleComment> root = query.from(ArticleComment.class);

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
        Root<Long> countRoot = countQuery.from(Long.class);
        countQuery.select(countBuilder.count(countRoot));

        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(
                comments, totalCount
        );
    }
}
