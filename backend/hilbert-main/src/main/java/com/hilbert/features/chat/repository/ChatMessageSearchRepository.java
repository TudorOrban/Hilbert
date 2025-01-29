package com.hilbert.features.chat.repository;

import com.hilbert.features.chat.model.ChatMessage;
import com.hilbert.shared.search.models.ChatMessageSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;

public interface ChatMessageSearchRepository {

    PaginatedResults<ChatMessage> searchChatMessages(ChatMessageSearchParams searchParams);
}
