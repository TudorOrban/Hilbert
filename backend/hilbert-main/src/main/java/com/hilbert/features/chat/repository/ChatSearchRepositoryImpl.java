package com.hilbert.features.chat.repository;

import com.hilbert.features.chat.model.Chat;
import com.hilbert.shared.search.models.ChatSearchParams;
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
public class ChatSearchRepositoryImpl implements ChatSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public PaginatedResults<Chat> searchChats(ChatSearchParams searchParams) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Chat> query = builder.createQuery(Chat.class);
        Root<Chat> root = query.from(Chat.class);

        Predicate conditions = getConditions(builder, root, searchParams);
        query.where(conditions);

        if (searchParams.getIsAscending()) {
            query.orderBy(builder.asc(root.get(searchParams.getSortBy())));
        } else {
            query.orderBy(builder.desc(root.get(searchParams.getSortBy())));
        }

        List<Chat> chats = entityManager.createQuery(query)
                .setFirstResult((searchParams.getPage() - 1) * searchParams.getItemsPerPage())
                .setMaxResults(searchParams.getItemsPerPage())
                .getResultList();

        // Query total count
        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<Chat> countRoot = countQuery.from(Chat.class);
        Predicate countConditions = getConditions(countBuilder, countRoot, searchParams);
        countQuery.select(countBuilder.count(countRoot));
        countQuery.where(countConditions);

        long totalCount = entityManager.createQuery(countQuery).getSingleResult();

        return new PaginatedResults<>(chats, totalCount);
    }

    private Predicate getConditions(CriteriaBuilder builder, Root<Chat> root, ChatSearchParams searchParams) {
        Predicate conditions = builder.conjunction();

        if (searchParams.getUserId() != null) {
            conditions = builder.and(conditions, builder.equal(root.get("userId"), searchParams.getUserId()));
        }

        return conditions;
    }
}
