package com.hilbert.features.chat.repository;

import com.hilbert.features.chat.model.Chat;
import com.hilbert.shared.search.models.ChatSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;

public interface ChatSearchRepository {

    PaginatedResults<Chat> searchChats(ChatSearchParams searchParams);
}
