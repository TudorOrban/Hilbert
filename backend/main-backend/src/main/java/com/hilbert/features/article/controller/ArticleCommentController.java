package com.hilbert.features.article.controller;

import com.hilbert.features.article.dto.ArticleCommentDto;
import com.hilbert.features.article.dto.CreateArticleCommentDto;
import com.hilbert.features.article.service.ArticleCommentService;
import com.hilbert.shared.search.models.ArticleCommentSearchParams;
import com.hilbert.shared.search.models.PaginatedResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/article-comments")
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @Autowired
    public ArticleCommentController(ArticleCommentService articleCommentService) {
        this.articleCommentService = articleCommentService;
    }

    @GetMapping("/search")
    public ResponseEntity<PaginatedResults<ArticleCommentDto>> searchComments(
            @RequestParam(value = "articleId", required = false) Integer articleId,
            @RequestParam(value = "searchQuery", required = false, defaultValue = "") String searchQuery,
            @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "isAscending", required = false, defaultValue = "true") Boolean isAscending,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "itemsPerPage", defaultValue = "10") Integer itemsPerPage
    ) {
        ArticleCommentSearchParams searchParams = new ArticleCommentSearchParams(
                articleId, searchQuery, sortBy, isAscending, page, itemsPerPage
        );

        PaginatedResults<ArticleCommentDto> results = articleCommentService.searchComments(searchParams);
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<ArticleCommentDto> createComment(@RequestBody CreateArticleCommentDto commentDto) {
        ArticleCommentDto savedCommentDto = articleCommentService.createComment(commentDto);
        return ResponseEntity.ok(savedCommentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        articleCommentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
