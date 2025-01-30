package com.hilbert.features.botchat.service;

import com.hilbert.features.botchat.dto.BotChatMessageSearchDto;
import com.hilbert.features.botchat.dto.CreateBotChatMessageDto;
import com.hilbert.shared.search.models.BotChatMessageSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;
import reactor.core.publisher.Flux;

public interface BotChatMessageService {

    PaginatedResults<BotChatMessageSearchDto> searchMessages(BotChatMessageSearchParams searchParams);
    Flux<String> createMessageAndResponse(CreateBotChatMessageDto messageDto);
    BotChatMessageSearchDto createMessage(CreateBotChatMessageDto messageDto);
    void deleteMessage(Long messageId);
}
