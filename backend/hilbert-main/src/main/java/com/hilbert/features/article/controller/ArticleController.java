package com.hilbert.features.article.controller;

import com.hilbert.features.article.dto.*;
import com.hilbert.shared.common.enums.DifficultyLevel;
import com.hilbert.shared.common.enums.Language;
import com.hilbert.features.article.service.ArticleService;
import com.hilbert.shared.search.models.ArticleSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(
        ArticleService articleService
    ) {
        this.articleService = articleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleFullDto> getArticleById(@PathVariable Long id) {
        ArticleFullDto articleDto = articleService.getArticleById(id);
        return ResponseEntity.ok(articleDto);
    }

    @GetMapping("/search")
    public ResponseEntity<PaginatedResults<ArticleSearchDto>> searchArticles(
        @RequestParam(value = "userId", required = false) Integer userId,
        @RequestParam(value = "searchQuery", required = false, defaultValue = "") String searchQuery,
        @RequestParam(value = "language", required = false) Language language,
        @RequestParam(value = "level", required = false) DifficultyLevel difficultyLevel,
        @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
        @RequestParam(value = "isAscending", required = false, defaultValue = "true") Boolean isAscending,
        @RequestParam(value = "page", defaultValue = "1") Integer page,
        @RequestParam(value = "itemsPerPage", defaultValue = "10") Integer itemsPerPage
    ) {
        ArticleSearchParams searchParams = new ArticleSearchParams(
                userId, language, difficultyLevel, searchQuery, sortBy, isAscending, page, itemsPerPage
        );

        PaginatedResults<ArticleSearchDto> results = articleService.searchArticles(searchParams);
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<ArticleFullDto> createArticle(@RequestBody CreateArticleDto articleDto) {
        ArticleFullDto createdArticle = articleService.createArticle(articleDto);
        return ResponseEntity.ok(createdArticle);
    }

    @PutMapping
    public ResponseEntity<ArticleFullDto> updateArticle(@RequestBody UpdateArticleDto articleDto) {
        ArticleFullDto updatedArticle = articleService.updateArticle(articleDto);
        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.ok().build();
    }
}
