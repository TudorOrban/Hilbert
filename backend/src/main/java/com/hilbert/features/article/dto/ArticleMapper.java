package com.hilbert.features.article.dto;

import com.hilbert.features.article.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    @Mapping(source = "article.id", target = "id")
    @Mapping(source = "article.userId", target = "userId")
    @Mapping(source = "article.title", target = "title")
    @Mapping(source = "article.description", target = "description")
    @Mapping(source = "article.content", target = "content")
    @Mapping(source = "article.language", target = "language")
    @Mapping(source = "article.level", target = "level")
    @Mapping(source = "article.status", target = "status")
    @Mapping(source = "article.wordCount", target = "wordCount")
    @Mapping(source = "article.createdAt", target = "createdAt")
    @Mapping(source = "article.updatedAt", target = "updatedAt")
    @Mapping(source = "article.averageRating", target = "averageRating")
    @Mapping(source = "article.numberOfRatings", target = "numberOfRatings")
    @Mapping(source = "article.readCount", target = "readCount")
    @Mapping(source = "article.bookmarkCount", target = "bookmarkCount")
    ArticleFullDto articleToArticleFullDto(Article article);

    @Mapping(source = "article.id", target = "id")
    @Mapping(source = "article.title", target = "title")
    @Mapping(source = "article.description", target = "description")
    @Mapping(source = "article.language", target = "language")
    @Mapping(source = "article.level", target = "level")
    @Mapping(source = "article.status", target = "status")
    @Mapping(source = "article.wordCount", target = "wordCount")
    @Mapping(source = "article.createdAt", target = "createdAt")
    @Mapping(source = "article.averageRating", target = "averageRating")
    @Mapping(source = "article.numberOfRatings", target = "numberOfRatings")
    @Mapping(source = "article.readCount", target = "readCount")
    @Mapping(source = "article.bookmarkCount", target = "bookmarkCount")
    ArticleSearchDto articleToArticleSearchDto(Article article);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "title", source = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "language", source = "language")
    @Mapping(target = "level", source = "level")
    @Mapping(source = "status", target = "status")
    Article createArticleDtoToArticle(CreateArticleDto createArticleDto);


}
