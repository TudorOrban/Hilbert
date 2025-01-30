package com.hilbert.features.botchat.service;

import com.hilbert.features.botchat.dto.BotChatInputDto;
import com.hilbert.features.botchat.dto.BotChatMapper;
import com.hilbert.features.botchat.dto.BotChatMessageSearchDto;
import com.hilbert.features.botchat.dto.CreateBotChatMessageDto;
import com.hilbert.features.botchat.model.BotChat;
import com.hilbert.features.botchat.model.BotChatMessage;
import com.hilbert.features.botchat.repository.BotChatMessageRepository;
import com.hilbert.features.botchat.repository.BotChatRepository;
import com.hilbert.shared.common.enums.Language;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import com.hilbert.shared.error.types.ValidationException;
import com.hilbert.shared.sanitization.service.EntitySanitizerService;
import com.hilbert.shared.search.models.BotChatMessageSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class BotChatMessageServiceImpl implements BotChatMessageService {

    private final BotChatMessageRepository chatMessageRepository;
    private final BotChatRepository chatRepository;
    private final BotChatResponseService botChatResponseService;
    private final EntitySanitizerService sanitizationService;

    @Autowired
    public BotChatMessageServiceImpl(
            BotChatMessageRepository chatMessageRepository,
            BotChatRepository chatRepository,
            BotChatResponseService botChatResponseService,
            EntitySanitizerService sanitizationService
    ) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatRepository = chatRepository;
        this.botChatResponseService = botChatResponseService;
        this.sanitizationService = sanitizationService;
    }

    public PaginatedResults<BotChatMessageSearchDto> searchMessages(BotChatMessageSearchParams searchParams) {
        PaginatedResults<BotChatMessage> results = chatMessageRepository.searchChatMessages(searchParams);
        return new PaginatedResults<>(
                results.getResults().stream().map(this::mapMessageToMessageSearchDto).toList(),
                results.getTotalCount()
        );
    }

    public String createMessageAndResponse(CreateBotChatMessageDto messageDto) {
        BotChatMessageSearchDto savedMessageDto = this.createMessage(messageDto);

        BotChatInputDto inputDto = new BotChatInputDto(
                savedMessageDto.getContent(), new ArrayList<>(), messageDto.getLanguage(), Language.ENGLISH, false
        );
        String response = botChatResponseService.respondToUser(inputDto);
        System.out.println("Response: " + response);

        return response;
    }

    public BotChatMessageSearchDto createMessage(CreateBotChatMessageDto messageDto) {
        CreateBotChatMessageDto sanitizedDto = sanitizationService.sanitizeCreateBotChatMessageDto(messageDto);

        BotChat chat = chatRepository.findById(sanitizedDto.getBotChatId())
                .orElseThrow(() -> new ResourceNotFoundException(sanitizedDto.getBotChatId().toString(), ResourceType.CHAT, ResourceIdentifierType.ID));
        if (!Objects.equals(chat.getUserId(), sanitizedDto.getUserId())) {
            throw new ValidationException("The user with ID: " + sanitizedDto.getUserId() + " is not allowed to create messages in the chat with ID: " + sanitizedDto.getBotChatId());
        }

        BotChatMessage message = this.mapCreateMessageDtoToChatMessage(sanitizedDto);
        BotChatMessage savedMessage = chatMessageRepository.save(message);
        return this.mapMessageToMessageSearchDto(savedMessage);
    }

    public void deleteMessage(Long messageId) {
        BotChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException(messageId.toString(), ResourceType.MESSAGE, ResourceIdentifierType.ID));

        chatMessageRepository.delete(message);
    }

    private BotChatMessageSearchDto mapMessageToMessageSearchDto(BotChatMessage message) {
        return BotChatMapper.INSTANCE.messageToMessageSearchDto(message);
    }

    private BotChatMessage mapCreateMessageDtoToChatMessage(CreateBotChatMessageDto messageDto) {
        return BotChatMapper.INSTANCE.createMessageDtoToChatMessage(messageDto);
    }
}
