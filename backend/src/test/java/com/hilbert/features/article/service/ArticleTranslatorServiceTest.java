package com.hilbert.features.article.service;

import com.hilbert.features.article.model.TranslatedContent;
import com.hilbert.shared.common.enums.Language;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class ArticleTranslatorServiceTest {

    @InjectMocks
    private ArticleTranslatorServiceImpl articleTranslatorService;

    @Test
    public void whenReceivedContent_thenCorrectTranslationShouldBeReturned() {
        // Arrange
        Language srcLanguage = Language.FRENCH;
        Language destLanguage = Language.ENGLISH;
        String content = "Bonjour, je suis heureux";

        HashMap<String, List<String>> expectedTranslationMap = new HashMap<>();
        expectedTranslationMap.put("Bonjour", new ArrayList<String>(List.of("Hello")));
        expectedTranslationMap.put("je", new ArrayList<String>(List.of("I")));
        expectedTranslationMap.put("suis", new ArrayList<String>(List.of("am")));
        expectedTranslationMap.put("heureux", new ArrayList<String>(List.of("heureux")));

        // Act
        TranslatedContent resultContent = articleTranslatorService.translateContent(content, srcLanguage, destLanguage);

        // Assert
        assertThat(resultContent).isNotNull();
        assertThat(resultContent.getTranslationMap()).isEqualTo(expectedTranslationMap);
    }
}
