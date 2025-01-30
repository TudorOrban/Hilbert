package com.hilbert.features.botchat.dto;

import com.hilbert.features.botchat.model.BotChat;
import com.hilbert.features.botchat.model.BotChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BotChatMapper {
    BotChatMapper INSTANCE = Mappers.getMapper(BotChatMapper.class);

    @Mapping(source = "chat.id", target = "id")
    @Mapping(source = "chat.userId", target = "userId")
    @Mapping(source = "chat.language", target = "language")
    @Mapping(source = "chat.createdAt", target = "createdAt")
    @Mapping(source = "chat.updatedAt", target = "updatedAt")
    @Mapping(source = "chat.lastMessageFromUser", target = "lastMessageFromUser")
    @Mapping(source = "chat.lastMessageContent", target = "lastMessageContent")
    @Mapping(source = "chat.lastMessageDate", target = "lastMessageDate")
    BotChatFullDto botChatToBotChatFullDto(BotChat chat);

    @Mapping(source = "chat.id", target = "id")
    @Mapping(source = "chat.userId", target = "userId")
    @Mapping(source = "chat.language", target = "language")
    @Mapping(source = "chat.createdAt", target = "createdAt")
    @Mapping(source = "chat.updatedAt", target = "updatedAt")
    @Mapping(source = "chat.lastMessageFromUser", target = "lastMessageFromUser")
    @Mapping(source = "chat.lastMessageContent", target = "lastMessageContent")
    @Mapping(source = "chat.lastMessageDate", target = "lastMessageDate")
    BotChatSearchDto botChatToBotChatSearchDto(BotChat chat);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "language", target = "language")
    @Mapping(source = "messageContent", target = "lastMessageContent")
    BotChat createBotChatDtoToBotChat(CreateBotChatDto chatDto);

    @Mapping(source = "message.id", target = "id")
    @Mapping(source = "message.botChatId", target = "botChatId")
    @Mapping(source = "message.isUser", target = "isUser")
    @Mapping(source = "message.content", target = "content")
    @Mapping(source = "message.translation", target = "translation")
    @Mapping(source = "message.createdAt", target = "createdAt")
    BotChatMessageSearchDto messageToMessageSearchDto(BotChatMessage message);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "botChatId", target = "botChatId")
    @Mapping(source = "isUser", target = "isUser")
    @Mapping(source = "content", target = "content")
    BotChatMessage createMessageDtoToChatMessage(CreateBotChatMessageDto messageDto);
}
