package com.hilbert.features.article.dto;

import com.hilbert.features.article.model.ArticleComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleCommentMapper {
    ArticleCommentMapper INSTANCE = Mappers.getMapper(ArticleCommentMapper.class);

    @Mapping(source = "comment.id", target = "id")
    @Mapping(source = "comment.articleId", target = "articleId")
    @Mapping(source = "comment.userId", target = "userId")
    @Mapping(source = "comment.username", target = "username")
    @Mapping(source = "comment.content", target = "content")
    @Mapping(source = "comment.createdAt", target = "createdAt")
    ArticleCommentDto commentToCommentSearchDto(ArticleComment comment);

    @Mapping(source = "articleId", target = "articleId")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "content", target = "content")
    ArticleComment createCommentDtoToComment(CreateArticleCommentDto commentDto);

}
