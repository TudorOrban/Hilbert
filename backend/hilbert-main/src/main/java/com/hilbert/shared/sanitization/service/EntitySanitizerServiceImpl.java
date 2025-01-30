package com.hilbert.shared.sanitization.service;

import com.hilbert.core.user.dto.CreateUserDto;
import com.hilbert.core.user.dto.UpdateUserDto;
import com.hilbert.features.article.dto.CreateArticleCommentDto;
import com.hilbert.features.article.dto.CreateArticleDto;
import com.hilbert.features.article.dto.UpdateArticleDto;
import com.hilbert.features.chat.dto.CreateChatDto;
import com.hilbert.features.chat.dto.CreateChatMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntitySanitizerServiceImpl implements EntitySanitizerService {

    private final SanitizationService sanitizationService;

    @Autowired
    public EntitySanitizerServiceImpl(SanitizationService sanitizationService) {
        this.sanitizationService = sanitizationService;
    }

    public CreateUserDto sanitizeCreateUserDto(CreateUserDto userDto) {
        userDto.setUsername(sanitizationService.sanitize(userDto.getUsername()));
        userDto.setPassword(sanitizationService.sanitize(userDto.getPassword()));
        userDto.setEmail(sanitizationService.sanitize(userDto.getEmail()));

        return userDto;
    }

    public UpdateUserDto sanitizeUpdateUserDto(UpdateUserDto userDto) {
        userDto.setUsername(sanitizationService.sanitize(userDto.getUsername()));
        userDto.setEmail(sanitizationService.sanitize(userDto.getEmail()));

        return userDto;
    }

    public CreateArticleDto sanitizeCreateArticleDto(CreateArticleDto articleDto) {
        articleDto.setTitle(sanitizationService.sanitize(articleDto.getTitle()));
        articleDto.setDescription(sanitizationService.sanitize(articleDto.getDescription()));
        articleDto.setContent(sanitizationService.sanitize(articleDto.getContent()));

        return articleDto;
    }

    public UpdateArticleDto sanitizeUpdateArticleDto(UpdateArticleDto articleDto) {
        articleDto.setTitle(sanitizationService.sanitize(articleDto.getTitle()));
        articleDto.setDescription(sanitizationService.sanitize(articleDto.getDescription()));
        articleDto.setContent(sanitizationService.sanitize(articleDto.getContent()));

        return articleDto;
    }

    public CreateArticleCommentDto sanitizeCreateArticleCommentDto(CreateArticleCommentDto commentDto) {
        commentDto.setContent(sanitizationService.sanitize(commentDto.getContent()));

        return commentDto;
    }

    public CreateChatDto sanitizeCreateChatDto(CreateChatDto chatDto) {
        chatDto.setMessageContent(sanitizationService.sanitize(chatDto.getMessageContent()));

        return chatDto;
    }

    public CreateChatMessageDto sanitizeCreateMessageDto(CreateChatMessageDto messageDto) {
        messageDto.setContent(sanitizationService.sanitize(messageDto.getContent()));

        return messageDto;
    }
}
