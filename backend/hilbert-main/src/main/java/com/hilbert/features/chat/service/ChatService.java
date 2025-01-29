package com.hilbert.features.chat.service;

import com.hilbert.features.chat.dto.ChatFullDto;
import com.hilbert.features.chat.dto.ChatSearchDto;
import com.hilbert.features.chat.dto.CreateChatDto;
import com.hilbert.shared.search.models.ChatSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;

public interface ChatService {

    ChatFullDto getChatFullDto(Long chatId, Boolean includeUsers, Boolean includeMessages);
    PaginatedResults<ChatSearchDto> searchChats(ChatSearchParams searchParams, Boolean includeUsers);
    ChatFullDto createChat(CreateChatDto chatDto);
    void deleteChat(Long chatId);
}
