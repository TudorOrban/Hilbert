package com.hilbert.features.article.controller;

import com.hilbert.features.article.dto.TemporaryTranslateDto;
import com.hilbert.features.article.model.TranslatedContent;
import com.hilbert.features.article.service.ArticleTranslatorService;
import com.hilbert.shared.common.enums.Language;
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
    public ResponseEntity<TranslatedContent> translateContent(@RequestBody TemporaryTranslateDto translateDto) {
        TranslatedContent translatedContent = articleTranslatorService.translateContent(translateDto.getContent(), translateDto.getSrcLanguage(), translateDto.getDestLanguage());

        return ResponseEntity.ok(translatedContent);
    }
}
