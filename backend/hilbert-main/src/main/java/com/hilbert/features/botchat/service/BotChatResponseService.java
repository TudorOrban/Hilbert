package com.hilbert.features.botchat.service;

import com.hilbert.features.botchat.dto.BotChatInputDto;
import reactor.core.publisher.Flux;

public interface BotChatResponseService {

    Flux<String> respondToUser(BotChatInputDto inputDto);
}
