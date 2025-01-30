package com.hilbert.features.botchat.repository;

import com.hilbert.features.botchat.model.BotChatMessage;
import com.hilbert.shared.search.models.BotChatMessageSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;

public interface BotChatMessageSearchRepository {

    PaginatedResults<BotChatMessage> searchChatMessages(BotChatMessageSearchParams searchParams);
}
