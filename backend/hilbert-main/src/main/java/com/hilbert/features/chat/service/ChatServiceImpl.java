package com.hilbert.features.chat.service;

import com.hilbert.features.chat.dto.*;
import com.hilbert.features.chat.model.Chat;
import com.hilbert.features.chat.model.ChatMessage;
import com.hilbert.features.chat.repository.ChatMessageRepository;
import com.hilbert.features.chat.repository.ChatRepository;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import com.hilbert.shared.sanitization.service.EntitySanitizerService;
import com.hilbert.shared.search.models.ChatSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final EntitySanitizerService sanitizationService;

    @Autowired
    public ChatServiceImpl(
            ChatRepository chatRepository,
            ChatMessageRepository chatMessageRepository,
            EntitySanitizerService sanitizationService
    ) {
        this.chatRepository = chatRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.sanitizationService = sanitizationService;
    }

    public PaginatedResults<ChatSearchDto> searchChats(ChatSearchParams searchParams) {
        PaginatedResults<Chat> results = chatRepository.searchChats(searchParams);

        return new PaginatedResults<>(
                results.getResults().stream()
                        .map(this::mapChatToChatSearchDto)
                        .toList(),
                results.getTotalCount()
        );
    }

//    public ChatFullDto getChatWithMessages(Long chatId) {
//        Chat chat = chatRepository.findById(chatId)
//                .orElseThrow(() -> new ResourceNotFoundException(chatId.toString(), ResourceType.CHAT, ResourceIdentifierType.ID));
//
//        PaginatedResults<ChatMessage> results = chatRepository
//    }

    public ChatFullDto createChat(CreateChatDto chatDto) {
        CreateChatDto sanitizedDto = sanitizationService.sanitizeCreateChatDto(chatDto);
        Chat chat = this.mapCreateChatDtoToChat(sanitizedDto);

        Chat savedChat = chatRepository.save(chat);
        ChatFullDto chatFullDto = this.mapChatToChatFullDto(savedChat);

        // Create first message
        ChatMessage message = new ChatMessage();
        message.setUserId(chatDto.getCreatorUserId());
        message.setChatId(savedChat.getId());
        message.setContent(sanitizedDto.getMessageContent());

        ChatMessage savedMessage = this.chatMessageRepository.save(message);
        MessageSearchDto messageDto = this.mapMessageToMessageSearchDto(savedMessage);

        chatFullDto.setMessages(new ArrayList<>(Collections.singleton(messageDto)));

        return chatFullDto;
    }

    public void deleteChat(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException(chatId.toString(), ResourceType.CHAT, ResourceIdentifierType.ID));

        chatRepository.delete(chat);
    }

    private ChatSearchDto mapChatToChatSearchDto(Chat chat) {
        return ChatMapper.INSTANCE.chatToChatSearchDto(chat);
    }

    private MessageSearchDto mapMessageToMessageSearchDto(ChatMessage message) {
        return ChatMapper.INSTANCE.messageToMessageSearchDto(message);
    }

    private Chat mapCreateChatDtoToChat(CreateChatDto chatDto) {
        return ChatMapper.INSTANCE.createChatDtoToChat(chatDto);
    }

    private ChatFullDto mapChatToChatFullDto(Chat chat) {
        return ChatMapper.INSTANCE.chatToChatFullDto(chat);
    }
}
