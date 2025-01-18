package com.hilbert.features.article.service;

import com.hilbert.features.article.dto.*;
import com.hilbert.features.article.model.Article;
import com.hilbert.features.article.repository.ArticleRepository;
import com.hilbert.shared.error.types.ResourceAlreadyExistsException;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import com.hilbert.shared.search.models.ArticleSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public ArticleFullDto getArticleById(Long id) {
        Article foundArticle = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.USER, ResourceIdentifierType.ID));

        return this.mapArticleToArticleFullDto(foundArticle);
    }

    public PaginatedResults<ArticleSearchDto> searchArticles(ArticleSearchParams searchParams) {
        PaginatedResults<Article> results = articleRepository.searchArticles(searchParams);

        return new PaginatedResults<>(
            results.getResults().stream()
                    .map(this::mapArticleToArticleSearchDto)
                    .toList(),
            results.getTotalCount()
        );
    }

    public ArticleFullDto createArticle(CreateArticleDto articleDto) {
        if (!articleRepository.hasUniqueTitle(articleDto.getUserId(), articleDto.getTitle())) {
            throw new ResourceAlreadyExistsException(articleDto.getTitle(), ResourceType.ARTICLE, ResourceIdentifierType.TITLE);
        }

        Article article = this.mapCreateArticleDtoToArticle(articleDto);
        Article savedArticle = articleRepository.save(article);

        return this.mapArticleToArticleFullDto(savedArticle);
    }

    public ArticleFullDto updateArticle(UpdateArticleDto articleDto) {
        Article existingArticle = articleRepository.findById(articleDto.getId())
            .orElseThrow(() -> new ResourceNotFoundException(articleDto.getId().toString(), ResourceType.ARTICLE, ResourceIdentifierType.ID));

        existingArticle.setTitle(articleDto.getTitle());
        existingArticle.setContent(articleDto.getContent());
        existingArticle.setLanguage(articleDto.getLanguage());
        existingArticle.setLevel(articleDto.getLevel());

        Article updatedArticle = articleRepository.save(existingArticle);

        return this.mapArticleToArticleFullDto(updatedArticle);
    }

    public void deleteArticle(Long id) {
        Article existingArticle = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), ResourceType.ARTICLE, ResourceIdentifierType.ID));

        articleRepository.delete(existingArticle);
    }

    private ArticleFullDto mapArticleToArticleFullDto(Article article) {
        return ArticleMapper.INSTANCE.articleToArticleFullDto(article);
    }

    private ArticleSearchDto mapArticleToArticleSearchDto(Article article) {
        return ArticleMapper.INSTANCE.articleToArticleSearchDto(article);
    }

    private Article mapCreateArticleDtoToArticle(CreateArticleDto articleDto) {
        return ArticleMapper.INSTANCE.createArticleDtoToArticle(articleDto);
    }
}
