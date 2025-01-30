package com.hilbert.features.chat.repository;

import com.hilbert.features.chat.model.BotChat;
import com.hilbert.shared.search.models.BotChatSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;

public interface BotChatSearchRepository {

    PaginatedResults<BotChat> searchChats(BotChatSearchParams searchParams);
}
