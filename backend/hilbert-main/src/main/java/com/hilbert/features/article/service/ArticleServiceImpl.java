package com.hilbert.features.article.service;

import com.hilbert.core.user.repository.UserRepository;
import com.hilbert.features.article.dto.*;
import com.hilbert.features.article.model.Article;
import com.hilbert.features.article.model.TranslatedContent;
import com.hilbert.features.article.repository.ArticleRepository;
import com.hilbert.features.article.service.translation.omw.ArticleTranslatorService;
import com.hilbert.shared.common.enums.Language;
import com.hilbert.shared.error.types.ResourceAlreadyExistsException;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import com.hilbert.shared.sanitization.service.EntitySanitizerService;
import com.hilbert.shared.search.models.ArticleSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleTranslatorService articleTranslatorService;
    private final UserRepository userRepository;
    private final EntitySanitizerService sanitizationService;

    @Autowired
    public ArticleServiceImpl(
        ArticleRepository articleRepository,
        ArticleTranslatorService articleTranslatorService,
        UserRepository userRepository,
        EntitySanitizerService sanitizationService
    ) {
        this.articleRepository = articleRepository;
        this.articleTranslatorService = articleTranslatorService;
        this.userRepository = userRepository;
        this.sanitizationService = sanitizationService;
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
        CreateArticleDto sanitizedArticleDto = sanitizationService.sanitizeCreateArticleDto(articleDto);

        if (!userRepository.existsById(sanitizedArticleDto.getUserId())) {
            throw new ResourceNotFoundException(sanitizedArticleDto.getUserId().toString(), ResourceType.USER, ResourceIdentifierType.ID);
        }
        if (articleRepository.hasNonUniqueTitle(sanitizedArticleDto.getUserId(), sanitizedArticleDto.getTitle())) {
            throw new ResourceAlreadyExistsException(sanitizedArticleDto.getTitle(), ResourceType.ARTICLE, ResourceIdentifierType.TITLE);
        }

        Article article = this.mapCreateArticleDtoToArticle(sanitizedArticleDto);

        TranslationResponseDto translationResponseDto = articleTranslatorService.translateContent(
                new TranslationRequestDto(article.getContent(), article.getLanguage(), Language.ENGLISH)); // TODO: Get destLanguage from user instead of ENGLISH
        TranslatedContent translatedContent = this.mapTranslationResponseDtoToTranslatedContent(translationResponseDto);
        article.setTranslatedContent(translatedContent);

        Article savedArticle = articleRepository.save(article);

        return this.mapArticleToArticleFullDto(savedArticle);
    }

    public ArticleFullDto updateArticle(UpdateArticleDto articleDto) {
        UpdateArticleDto sanitizedArticleDto = sanitizationService.sanitizeUpdateArticleDto(articleDto);

        Article existingArticle = articleRepository.findById(sanitizedArticleDto.getId())
            .orElseThrow(() -> new ResourceNotFoundException(sanitizedArticleDto.getId().toString(), ResourceType.ARTICLE, ResourceIdentifierType.ID));

        existingArticle.setTitle(sanitizedArticleDto.getTitle());
        existingArticle.setDescription(sanitizedArticleDto.getDescription());
        existingArticle.setContent(sanitizedArticleDto.getContent());
        existingArticle.setLanguage(sanitizedArticleDto.getLanguage());
        existingArticle.setLevel(sanitizedArticleDto.getLevel());

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

    private TranslatedContent mapTranslationResponseDtoToTranslatedContent(TranslationResponseDto translationDto) {
        return ArticleMapper.INSTANCE.translationResponseDtoToTranslatedContent(translationDto);
    }
}
