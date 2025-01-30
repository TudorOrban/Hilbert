package com.hilbert.features.chat.service;

import com.hilbert.features.chat.dto.CreateChatMessageDto;
import com.hilbert.features.chat.dto.ChatMessageSearchDto;
import com.hilbert.shared.search.models.ChatMessageSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;

public interface ChatMessageService {

    PaginatedResults<ChatMessageSearchDto> searchMessages(ChatMessageSearchParams searchParams);
    ChatMessageSearchDto createMessage(CreateChatMessageDto messageDto);
    void deleteMessage(Long messageId);
}
