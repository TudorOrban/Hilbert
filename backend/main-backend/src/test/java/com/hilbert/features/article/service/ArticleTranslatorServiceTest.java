package com.hilbert.features.article.service;

import com.hilbert.features.article.dto.TranslationRequestDto;
import com.hilbert.features.article.dto.TranslationResponseDto;
import com.hilbert.features.article.model.TranslatedContent;
import com.hilbert.shared.common.enums.Language;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class ArticleTranslatorServiceTest {

    @InjectMocks
    private ArticleTranslatorServiceOldImpl articleTranslatorService;

    @Test
    public void whenReceivedContent_thenCorrectTranslationShouldBeReturned() {
        // Arrange
        Language srcLanguage = Language.FRENCH;
        Language destLanguage = Language.ENGLISH;
        String content = "Bonjour, je suis heureux";

        HashMap<String, List<String>> expectedTranslationMap = new HashMap<>();
        expectedTranslationMap.put("Bonjour", new ArrayList<>(List.of("Hello")));
        expectedTranslationMap.put("je", new ArrayList<>(List.of("I")));
        expectedTranslationMap.put("suis", new ArrayList<>(List.of("am")));
        expectedTranslationMap.put("heureux", new ArrayList<>(List.of("heureux")));

        // Act
        TranslationResponseDto resultContent = articleTranslatorService.translateContent(new TranslationRequestDto(content, srcLanguage, destLanguage));

        // Assert
        assertThat(resultContent).isNotNull();
        assertThat(resultContent.getTranslation()).isEqualTo(expectedTranslationMap);
    }
}
