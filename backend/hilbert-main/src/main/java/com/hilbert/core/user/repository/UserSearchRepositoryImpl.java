package com.hilbert.core.user.repository;

import com.hilbert.core.user.model.User;
import com.hilbert.core.user.model.UserLanguage;
import com.hilbert.shared.common.enums.DifficultyLevel;
import com.hilbert.shared.common.enums.Language;
import com.hilbert.shared.search.models.PaginatedResults;
import com.hilbert.shared.search.models.UserSearchParams;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.List;
import java.util.Map;

public class UserSearchRepositoryImpl implements UserSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public PaginatedResults<User> searchUsers(UserSearchParams searchParams) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        Predicate conditions = getConditions(builder, root, searchParams);
        query.where(conditions);

        if (searchParams.getIsAscending()) {
            query.orderBy(builder.asc(root.get(searchParams.getSortBy())));
        } else {
            query.orderBy(builder.desc(root.get(searchParams.getSortBy())));
        }

        List<User> users = entityManager.createQuery(query)
                .setFirstResult((searchParams.getPage() - 1) * searchParams.getItemsPerPage())
                .setMaxResults(searchParams.getItemsPerPage())
                .getResultList();

        // Query total count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        Predicate countConditions = getConditions(countBuilder, countRoot, searchParams);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(countConditions);

        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(users, totalCount);
    }


    private Predicate getConditions(CriteriaBuilder builder, Root<User> root, UserSearchParams searchParams) {
        Predicate conditions = builder.conjunction();

        if (searchParams.getSearchQuery() != null && !searchParams.getSearchQuery().isEmpty()) {
            conditions = builder.and(conditions, builder.like(root.get("username"), "%" + searchParams.getSearchQuery() + "%"));
        }

        if (searchParams.getLanguages() != null && !searchParams.getLanguages().isEmpty()) {
            Join<User, UserLanguage> languagesJoin = root.join("userLanguages");
            Predicate languageConditions = builder.disjunction();

            for (Map.Entry<Language, DifficultyLevel> entry : searchParams.getLanguages().entrySet()) {
                Language language = entry.getKey();
                DifficultyLevel level = entry.getValue();
                Predicate languageCondition = builder.and(
                        builder.equal(languagesJoin.get("language"), language),
                        builder.equal(languagesJoin.get("level"), level)
                );
                languageConditions = builder.or(languageConditions, languageCondition);
            }

            conditions = builder.and(conditions, languageConditions);
        }

        return conditions;
    }
}
