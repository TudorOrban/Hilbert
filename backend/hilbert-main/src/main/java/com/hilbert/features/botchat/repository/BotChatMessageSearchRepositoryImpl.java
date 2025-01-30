package com.hilbert.features.botchat.repository;

import com.hilbert.features.botchat.model.BotChatMessage;
import com.hilbert.shared.search.models.BotChatMessageSearchParams;
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
public class BotChatMessageSearchRepositoryImpl implements BotChatMessageSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public PaginatedResults<BotChatMessage> searchChatMessages(BotChatMessageSearchParams searchParams) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BotChatMessage> query = builder.createQuery(BotChatMessage.class);
        Root<BotChatMessage> root = query.from(BotChatMessage.class);

        Predicate conditions = getConditions(builder, root, searchParams);
        query.where(conditions);

        if (searchParams.getIsAscending()) {
            query.orderBy(builder.asc(root.get(searchParams.getSortBy())));
        } else {
            query.orderBy(builder.desc(root.get(searchParams.getSortBy())));
        }

        List<BotChatMessage> botChatMessages = entityManager.createQuery(query)
                .setFirstResult((searchParams.getPage() - 1) * searchParams.getItemsPerPage())
                .setMaxResults(searchParams.getItemsPerPage())
                .getResultList();

        // Query total count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<BotChatMessage> countRoot = countQuery.from(BotChatMessage.class);
        Predicate countConditions = getConditions(countBuilder, countRoot, searchParams);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(countConditions);

        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(botChatMessages, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<BotChatMessage> root, BotChatMessageSearchParams searchParams) {
        Predicate conditions = builder.conjunction();

        if (searchParams.getChatId() != null) {
            conditions = builder.and(conditions, builder.equal(root.get("botChatId"), searchParams.getChatId()));
        }

        return conditions;
    }
}
