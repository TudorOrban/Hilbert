package com.hilbert.features.botchat.service;

import com.hilbert.features.botchat.dto.*;
import com.hilbert.features.botchat.model.BotChat;
import com.hilbert.features.botchat.model.BotChatMessage;
import com.hilbert.features.botchat.repository.BotChatMessageRepository;
import com.hilbert.features.botchat.repository.BotChatRepository;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import com.hilbert.shared.sanitization.service.EntitySanitizerService;
import com.hilbert.shared.search.models.BotChatMessageSearchParams;
import com.hilbert.shared.search.models.BotChatSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BotChatServiceImpl implements BotChatService {

    private final BotChatRepository botChatRepository;
    private final BotChatMessageRepository chatMessageRepository;
    private final EntitySanitizerService sanitizationService;

    @Autowired
    public BotChatServiceImpl(
            BotChatRepository botChatRepository,
            BotChatMessageRepository chatMessageRepository,
            EntitySanitizerService sanitizationService
    ) {
        this.botChatRepository = botChatRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.sanitizationService = sanitizationService;
    }

    public BotChatFullDto getChatFullDto(Long chatId, Boolean includeUsers, Boolean includeMessages) {
        BotChat chat = botChatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException(chatId.toString(), ResourceType.CHAT, ResourceIdentifierType.ID));
        BotChatFullDto chatFullDto = this.mapChatToChatFullDto(chat);

        if (includeMessages) {
            this.findAndAttachMessagesToFullDto(chatFullDto);
        }

        return chatFullDto;
    }

    public PaginatedResults<BotChatSearchDto> searchChats(BotChatSearchParams searchParams, Boolean includeUsers) {
        PaginatedResults<BotChat> results = botChatRepository.searchChats(searchParams);
        return new PaginatedResults<>(
                results.getResults().stream()
                        .map(this::mapChatToChatSearchDto)
                        .toList(),
                results.getTotalCount()
        );
    }

    public BotChatFullDto createChat(CreateBotChatDto chatDto) {
        CreateBotChatDto sanitizedDto = sanitizationService.sanitizeCreateBotChatDto(chatDto);
        BotChat chat = this.mapCreateChatDtoToChat(sanitizedDto);

        BotChat savedChat = botChatRepository.save(chat);
        return this.mapChatToChatFullDto(savedChat);
    }

    public void deleteChat(Long chatId) {
        BotChat chat = botChatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException(chatId.toString(), ResourceType.CHAT, ResourceIdentifierType.ID));

        botChatRepository.delete(chat);
    }


    private void findAndAttachMessagesToFullDto(BotChatFullDto chatFullDto) {
        Integer numberOfMessages = 10;
        BotChatMessageSearchParams searchParams = new BotChatMessageSearchParams(chatFullDto.getId(), "", "createdAt", false, 1, numberOfMessages);

        PaginatedResults<BotChatMessage> results = chatMessageRepository.searchChatMessages(searchParams);
        PaginatedResults<BotChatMessageSearchDto> dtoResults = new PaginatedResults<>(
                results.getResults().stream().map(this::mapMessageToMessageSearchDto).toList(),
                results.getTotalCount()
        );

        chatFullDto.setMessages(dtoResults);
    }


    private BotChatSearchDto mapChatToChatSearchDto(BotChat chat) {
        return BotChatMapper.INSTANCE.botChatToBotChatSearchDto(chat);
    }

    private BotChatMessageSearchDto mapMessageToMessageSearchDto(BotChatMessage message) {
        return BotChatMapper.INSTANCE.messageToMessageSearchDto(message);
    }

    private BotChat mapCreateChatDtoToChat(CreateBotChatDto chatDto) {
        return BotChatMapper.INSTANCE.createBotChatDtoToBotChat(chatDto);
    }

    private BotChatFullDto mapChatToChatFullDto(BotChat chat) {
        return BotChatMapper.INSTANCE.botChatToBotChatFullDto(chat);
    }
}
