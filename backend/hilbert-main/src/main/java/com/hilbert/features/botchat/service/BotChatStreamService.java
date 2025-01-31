package com.hilbert.features.botchat.service;

import com.hilbert.features.botchat.dto.CreateBotChatMessageDto;
import reactor.core.publisher.Flux;

public interface BotChatStreamService {

    String processMessageAndTriggerResponse(CreateBotChatMessageDto messageDto);
    Flux<String> getResponseStream(String requestId);
}
