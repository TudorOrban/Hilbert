package com.hilbert.features.botchat.service;

import com.hilbert.features.botchat.dto.BotChatFullDto;
import com.hilbert.features.botchat.dto.BotChatSearchDto;
import com.hilbert.features.botchat.dto.CreateBotChatDto;
import com.hilbert.shared.search.models.ChatSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;

public interface BotChatService {

    BotChatFullDto getChatFullDto(Long chatId, Boolean includeUsers, Boolean includeMessages);
    PaginatedResults<BotChatSearchDto> searchChats(ChatSearchParams searchParams, Boolean includeUsers);
    BotChatFullDto createChat(CreateBotChatDto chatDto);
    void deleteChat(Long chatId);
}
