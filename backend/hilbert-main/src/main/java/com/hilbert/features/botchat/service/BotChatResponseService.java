package com.hilbert.features.botchat.service;

import com.hilbert.features.botchat.dto.BotChatInputDto;

public interface BotChatResponseService {

    String respondToUser(BotChatInputDto inputDto);
}
