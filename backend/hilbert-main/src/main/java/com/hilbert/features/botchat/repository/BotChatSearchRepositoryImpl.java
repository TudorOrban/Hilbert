package com.hilbert.features.botchat.repository;

import com.hilbert.features.botchat.model.BotChat;
import com.hilbert.shared.common.enums.Language;
import com.hilbert.shared.search.models.BotChatSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BotChatSearchRepositoryImpl implements BotChatSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public PaginatedResults<BotChat> searchChats(BotChatSearchParams searchParams) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BotChat> query = builder.createQuery(BotChat.class);
        Root<BotChat> root = query.from(BotChat.class);

        Predicate conditions = getConditions(builder, root, searchParams);
        query.where(conditions);

        if (searchParams.getIsAscending()) {
            query.orderBy(builder.asc(root.get(searchParams.getSortBy())));
        } else {
            query.orderBy(builder.desc(root.get(searchParams.getSortBy())));
        }

        List<BotChat> botChats = entityManager.createQuery(query)
                .setFirstResult((searchParams.getPage() - 1) * searchParams.getItemsPerPage())
                .setMaxResults(searchParams.getItemsPerPage())
                .getResultList();

        // Query total count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<BotChat> countRoot = countQuery.from(BotChat.class);
        Predicate countConditions = getConditions(countBuilder, countRoot, searchParams);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(countConditions);

        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(botChats, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<BotChat> root, BotChatSearchParams searchParams) {
        Predicate conditions = builder.conjunction();

        if (searchParams.getUserId() != null) {
            conditions = builder.and(conditions, builder.equal(root.get("userId"), searchParams.getUserId()));
        }
        if (searchParams.getLanguage() != null && !Objects.equals(searchParams.getLanguage(), Language.NONE)) {
            conditions = builder.and(conditions, builder.equal(root.get("language"), searchParams.getLanguage()));
        }

        return conditions;
    }
}
