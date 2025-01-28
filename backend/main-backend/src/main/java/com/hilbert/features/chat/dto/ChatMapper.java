package com.hilbert.features.chat.dto;

import com.hilbert.features.chat.model.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatMapper {
    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    @Mapping(source = "chat.id", target = "id")
    @Mapping(source = "chat.firstUserId", target = "firstUserId")
    @Mapping(source = "chat.secondUserId", target = "secondUserId")
    @Mapping(source = "chat.createdAt", target = "createdAt")
    @Mapping(source = "chat.updatedAt", target = "updatedAt")
    @Mapping(source = "chat.lastMessageUserId", target = "lastMessageUserId")
    @Mapping(source = "chat.lastMessageContent", target = "lastMessageContent")
    ChatFullDto chatToChatFullDto(Chat chat);

    @Mapping(source = "chat.id", target = "id")
    @Mapping(source = "chat.firstUserId", target = "firstUserId")
    @Mapping(source = "chat.secondUserId", target = "secondUserId")
    @Mapping(source = "chat.createdAt", target = "createdAt")
    @Mapping(source = "chat.updatedAt", target = "updatedAt")
    @Mapping(source = "chat.lastMessageUserId", target = "lastMessageUserId")
    @Mapping(source = "chat.lastMessageContent", target = "lastMessageContent")
    ChatSearchDto chatToChatSearchDto(Chat chat);

    @Mapping(source = "firstUserId", target = "firstUserId")
    @Mapping(source = "secondUserId", target = "secondUserId")
    Chat createChatDtoToChat(CreateChatDto chatDto);
}
