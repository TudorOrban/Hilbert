package com.hilbert.features.chat.service;

import com.hilbert.features.chat.dto.ChatMapper;
import com.hilbert.features.chat.dto.CreateMessageDto;
import com.hilbert.features.chat.dto.MessageSearchDto;
import com.hilbert.features.chat.model.Chat;
import com.hilbert.features.chat.model.ChatMessage;
import com.hilbert.features.chat.repository.ChatMessageRepository;
import com.hilbert.features.chat.repository.ChatRepository;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import com.hilbert.shared.error.types.ValidationException;
import com.hilbert.shared.sanitization.service.EntitySanitizerService;
import com.hilbert.shared.search.models.ChatMessageSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRepository chatRepository;
    private final EntitySanitizerService sanitizationService;

    @Autowired
    public ChatMessageServiceImpl(
            ChatMessageRepository chatMessageRepository,
            ChatRepository chatRepository,
            EntitySanitizerService sanitizationService
    ) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatRepository = chatRepository;
        this.sanitizationService = sanitizationService;
    }

    public PaginatedResults<MessageSearchDto> searchMessages(ChatMessageSearchParams searchParams) {
        PaginatedResults<ChatMessage> results = chatMessageRepository.searchChatMessages(searchParams);
        return new PaginatedResults<>(
                results.getResults().stream().map(this::mapMessageToMessageSearchDto).toList(),
                results.getTotalCount()
        );
    }

    public MessageSearchDto createMessage(CreateMessageDto messageDto) {
        CreateMessageDto sanitizedDto = sanitizationService.sanitizeCreateMessageDto(messageDto);

        Chat chat = chatRepository.findById(sanitizedDto.getChatId())
                .orElseThrow(() -> new ResourceNotFoundException(sanitizedDto.getChatId().toString(), ResourceType.CHAT, ResourceIdentifierType.ID));
        if (!Objects.equals(chat.getFirstUserId(), sanitizedDto.getUserId()) &&
            !Objects.equals(chat.getSecondUserId(), sanitizedDto.getUserId())) {
            throw new ValidationException("The user with ID: " + sanitizedDto.getUserId() + " is not allowed to create messages in the chat with ID: " + sanitizedDto.getChatId());
        }

        ChatMessage message = this.mapCreateMessageDtoToChatMessage(sanitizedDto);

        ChatMessage savedMessage = chatMessageRepository.save(message);
        return this.mapMessageToMessageSearchDto(savedMessage);
    }

    public void deleteMessage(Long messageId) {
        ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException(messageId.toString(), ResourceType.MESSAGE, ResourceIdentifierType.ID));

        chatMessageRepository.delete(message);
    }

    private MessageSearchDto mapMessageToMessageSearchDto(ChatMessage message) {
        return ChatMapper.INSTANCE.messageToMessageSearchDto(message);
    }

    private ChatMessage mapCreateMessageDtoToChatMessage(CreateMessageDto messageDto) {
        return ChatMapper.INSTANCE.createMessageDtoToChatMessage(messageDto);
    }
}
