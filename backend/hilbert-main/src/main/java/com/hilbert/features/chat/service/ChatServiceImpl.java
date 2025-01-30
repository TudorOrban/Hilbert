package com.hilbert.features.chat.service;

import com.hilbert.core.user.dto.UserSmallDto;
import com.hilbert.core.user.service.UserService;
import com.hilbert.features.chat.dto.*;
import com.hilbert.features.chat.model.Chat;
import com.hilbert.features.chat.model.ChatMessage;
import com.hilbert.features.chat.repository.ChatMessageRepository;
import com.hilbert.features.chat.repository.ChatRepository;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import com.hilbert.shared.sanitization.service.EntitySanitizerService;
import com.hilbert.shared.search.models.ChatMessageSearchParams;
import com.hilbert.shared.search.models.ChatSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;
    private final EntitySanitizerService sanitizationService;

    @Autowired
    public ChatServiceImpl(
            ChatRepository chatRepository,
            ChatMessageRepository chatMessageRepository,
            UserService userService,
            EntitySanitizerService sanitizationService
    ) {
        this.chatRepository = chatRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.userService = userService;
        this.sanitizationService = sanitizationService;
    }

    public ChatFullDto getChatFullDto(Long chatId, Boolean includeUsers, Boolean includeMessages) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException(chatId.toString(), ResourceType.CHAT, ResourceIdentifierType.ID));
        ChatFullDto chatFullDto = this.mapChatToChatFullDto(chat);

        if (includeUsers) {
            this.findAndAttachUsersToFullDto(chatFullDto);
        }
        if (includeMessages) {
            this.findAndAttachMessagesToFullDto(chatFullDto);
        }

        return chatFullDto;
    }

    public PaginatedResults<ChatSearchDto> searchChats(ChatSearchParams searchParams, Boolean includeUsers) {
        PaginatedResults<Chat> results = chatRepository.searchChats(searchParams);
        PaginatedResults<ChatSearchDto> resultDtos = new PaginatedResults<>(
                results.getResults().stream()
                        .map(this::mapChatToChatSearchDto)
                        .toList(),
                results.getTotalCount()
        );

        if (includeUsers) {
            findAndAttachUsersToSearchDto(resultDtos, searchParams.getUserId());
        }

        return resultDtos;
    }

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
        ChatMessageSearchDto messageDto = this.mapMessageToMessageSearchDto(savedMessage);

        chatFullDto.setMessages(new PaginatedResults<>(new ArrayList<>(Collections.singleton(messageDto)), 1L));

        return chatFullDto;
    }

    public void deleteChat(Long chatId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException(chatId.toString(), ResourceType.CHAT, ResourceIdentifierType.ID));

        chatRepository.delete(chat);
    }


    private void findAndAttachMessagesToFullDto(ChatFullDto chatFullDto) {
        Integer numberOfMessages = 10;
        ChatMessageSearchParams searchParams = new ChatMessageSearchParams(chatFullDto.getId(), "", "createdAt", false, 1, numberOfMessages);

        PaginatedResults<ChatMessage> results = chatMessageRepository.searchChatMessages(searchParams);
        PaginatedResults<ChatMessageSearchDto> dtoResults = new PaginatedResults<>(
                results.getResults().stream().map(this::mapMessageToMessageSearchDto).toList(),
                results.getTotalCount()
        );

        chatFullDto.setMessages(dtoResults);
    }

    private void findAndAttachUsersToFullDto(ChatFullDto chatFullDto) {
        List<UserSmallDto> users = userService.getByIds(new ArrayList<>(Arrays.asList(chatFullDto.getFirstUserId(), chatFullDto.getSecondUserId())));
        Optional<UserSmallDto> firstUserOpt = users.stream().filter(u -> Objects.equals(u.getId(), chatFullDto.getFirstUserId())).findFirst();
        firstUserOpt.ifPresent(chatFullDto::setFirstUser);
        Optional<UserSmallDto> secondUserOpt = users.stream().filter(u -> Objects.equals(u.getId(), chatFullDto.getSecondUserId())).findFirst();
        secondUserOpt.ifPresent(chatFullDto::setFirstUser);
    }

    private void findAndAttachUsersToSearchDto(PaginatedResults<ChatSearchDto> resultDtos, Long requestUserId) {
        Set<Long> userIds = resultDtos.getResults().stream().map(ChatSearchDto::getFirstUserId).collect(Collectors.toSet());
        userIds.addAll(resultDtos.getResults().stream().map(ChatSearchDto::getSecondUserId).toList());

        List<UserSmallDto> users = userService.getByIds(userIds.stream().toList());

        resultDtos.getResults().forEach(r -> {
            Optional<UserSmallDto> firstUserOpt = users.stream()
                    .filter(u -> Objects.equals(u.getId(), r.getFirstUserId()) &&
                            !Objects.equals(u.getId(), requestUserId)).findFirst(); // Prevent attaching for request user (not necessary)
            firstUserOpt.ifPresent(r::setFirstUser);
            Optional<UserSmallDto> secondUserOpt = users.stream()
                    .filter(u -> Objects.equals(u.getId(), r.getFirstUserId()) &&
                            !Objects.equals(u.getId(), requestUserId)).findFirst();
            secondUserOpt.ifPresent(r::setSecondUser);
        });
    }

    private ChatSearchDto mapChatToChatSearchDto(Chat chat) {
        return ChatMapper.INSTANCE.chatToChatSearchDto(chat);
    }

    private ChatMessageSearchDto mapMessageToMessageSearchDto(ChatMessage message) {
        return ChatMapper.INSTANCE.messageToMessageSearchDto(message);
    }

    private Chat mapCreateChatDtoToChat(CreateChatDto chatDto) {
        return ChatMapper.INSTANCE.createChatDtoToChat(chatDto);
    }

    private ChatFullDto mapChatToChatFullDto(Chat chat) {
        return ChatMapper.INSTANCE.chatToChatFullDto(chat);
    }
}
