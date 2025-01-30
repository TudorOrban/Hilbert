package com.hilbert.features.botchat.service;

import com.hilbert.features.botchat.dto.BotChatMessageSearchDto;
import com.hilbert.features.botchat.dto.CreateBotChatMessageDto;
import com.hilbert.shared.search.models.BotChatMessageSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;

public interface BotChatMessageService {

    PaginatedResults<BotChatMessageSearchDto> searchMessages(BotChatMessageSearchParams searchParams);
    BotChatMessageSearchDto createMessage(CreateBotChatMessageDto messageDto);
    void deleteMessage(Long messageId);
}
