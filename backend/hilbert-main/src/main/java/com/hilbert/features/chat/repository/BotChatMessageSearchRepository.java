package com.hilbert.features.chat.repository;

import com.hilbert.features.chat.model.BotChatMessage;
import com.hilbert.shared.search.models.BotChatMessageSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;

public interface BotChatMessageSearchRepository {

    PaginatedResults<BotChatMessage> searchChatMessages(BotChatMessageSearchParams searchParams);
}
