package com.hilbert.features.article.controller;

import com.hilbert.features.article.dto.ReadArticleDto;
import com.hilbert.features.article.dto.ReadArticleSummaryDto;
import com.hilbert.features.article.model.Article;
import com.hilbert.features.article.service.read.ArticleRecommenderService;
import com.hilbert.features.article.service.read.ReadArticleService;
import com.hilbert.features.vocabulary.model.Vocabulary;
import com.hilbert.features.vocabulary.service.VocabularyService;
import com.hilbert.shared.common.enums.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/articles/reading")
public class ArticleReadingController {

    private final ReadArticleService readArticleService;
    private final ArticleRecommenderService articleRecommenderService;
    private final VocabularyService vocabularyService;

    @Autowired
    public ArticleReadingController(
            ReadArticleService readArticleService,
            ArticleRecommenderService articleRecommenderService,
            VocabularyService vocabularyService
    ) {
        this.readArticleService = readArticleService;
        this.articleRecommenderService = articleRecommenderService;
        this.vocabularyService = vocabularyService;
    }

    @GetMapping("/recommend/{userId}")
    public ResponseEntity<List<Article>> recommendArticles(
            @PathVariable Long userId,
            @RequestParam(value = "language", defaultValue = "NONE") Language language,
            @RequestParam(value = "numberOfRecommendations", required = false, defaultValue = "50") Integer numberOfRecommendations
    ) {
        Vocabulary vocabulary = vocabularyService.findOrCreateVocabulary(userId, language);
        List<Article> recommendedArticles = articleRecommenderService.recommendArticles(vocabulary, numberOfRecommendations);
        return ResponseEntity.ok(recommendedArticles);
    }

    @PostMapping("/read")
    public ResponseEntity<ReadArticleSummaryDto> readArticle(@RequestBody ReadArticleDto readArticleDto) {
        ReadArticleSummaryDto summaryDto = readArticleService.readArticle(readArticleDto);
        return ResponseEntity.ok(summaryDto);
    }
}
