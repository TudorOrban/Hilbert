package com.hilbert.features.article.controller;

import com.hilbert.features.article.dto.TranslationRequestDto;
import com.hilbert.features.article.dto.TranslationResponseDto;
import com.hilbert.features.article.service.ArticleTranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/temporary")
public class TemporaryController {

    private final ArticleTranslatorService articleTranslatorService;

    @Autowired
    public TemporaryController(ArticleTranslatorService articleTranslatorService) {
        this.articleTranslatorService = articleTranslatorService;
    }

    @GetMapping
    public ResponseEntity<TranslationResponseDto> translateContent(@RequestBody TranslationRequestDto translateDto) {
        TranslationResponseDto translatedContent = articleTranslatorService.translateContent(translateDto);

        return ResponseEntity.ok(translatedContent);
    }
}
