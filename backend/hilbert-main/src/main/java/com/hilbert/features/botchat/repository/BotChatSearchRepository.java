package com.hilbert.features.botchat.repository;

import com.hilbert.features.botchat.model.BotChat;
import com.hilbert.shared.search.models.BotChatSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;

public interface BotChatSearchRepository {

    PaginatedResults<BotChat> searchChats(BotChatSearchParams searchParams);
}
