package com.hilbert.features.chat.service;

import com.hilbert.features.chat.dto.CreateMessageDto;
import com.hilbert.features.chat.dto.MessageSearchDto;
import com.hilbert.shared.search.models.ChatMessageSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;

public interface ChatMessageService {

    PaginatedResults<MessageSearchDto> searchMessages(ChatMessageSearchParams searchParams);
    MessageSearchDto createMessage(CreateMessageDto messageDto);
    void deleteMessage(Long messageId);
}
