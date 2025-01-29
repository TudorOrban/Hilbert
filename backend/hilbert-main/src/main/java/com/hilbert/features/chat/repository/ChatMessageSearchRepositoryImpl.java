package com.hilbert.features.chat.repository;

import com.hilbert.features.chat.model.ChatMessage;
import com.hilbert.shared.search.models.ChatMessageSearchParams;
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
public class ChatMessageSearchRepositoryImpl implements ChatMessageSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public PaginatedResults<ChatMessage> searchChatMessages(ChatMessageSearchParams searchParams) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ChatMessage> query = builder.createQuery(ChatMessage.class);
        Root<ChatMessage> root = query.from(ChatMessage.class);

        Predicate conditions = getConditions(builder, root, searchParams);
        query.where(conditions);

        if (searchParams.getIsAscending()) {
            query.orderBy(builder.asc(root.get(searchParams.getSortBy())));
        } else {
            query.orderBy(builder.desc(root.get(searchParams.getSortBy())));
        }

        List<ChatMessage> chatMessages = entityManager.createQuery(query)
                .setFirstResult((searchParams.getPage() - 1) * searchParams.getItemsPerPage())
                .setMaxResults(searchParams.getItemsPerPage())
                .getResultList();

        // Query total count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<ChatMessage> countRoot = countQuery.from(ChatMessage.class);
        Predicate countConditions = getConditions(countBuilder, countRoot, searchParams);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(countConditions);

        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(chatMessages, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<ChatMessage> root, ChatMessageSearchParams searchParams) {
        Predicate conditions = builder.conjunction();

        if (searchParams.getChatId() != null) {
            conditions = builder.and(conditions, builder.equal(root.get("chatId"), searchParams.getChatId()));
        }

        return conditions;
    }
}
