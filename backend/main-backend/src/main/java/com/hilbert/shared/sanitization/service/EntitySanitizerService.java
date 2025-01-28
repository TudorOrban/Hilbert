package com.hilbert.shared.sanitization.service;

import com.hilbert.core.user.dto.CreateUserDto;
import com.hilbert.core.user.dto.UpdateUserDto;
import com.hilbert.features.article.dto.CreateArticleCommentDto;
import com.hilbert.features.article.dto.CreateArticleDto;
import com.hilbert.features.article.dto.UpdateArticleDto;

public interface EntitySanitizerService {


    CreateUserDto sanitizeCreateUserDto(CreateUserDto userDto);
    UpdateUserDto sanitizeUpdateUserDto(UpdateUserDto userDto);

    CreateArticleDto sanitizeCreateArticleDto(CreateArticleDto articleDto);
    UpdateArticleDto sanitizeUpdateArticleDto(UpdateArticleDto articleDto);

    CreateArticleCommentDto sanitizeCreateArticleCommentDto(CreateArticleCommentDto commentDto);
}
