package com.hilbert.features.article.service;

import com.hilbert.features.article.model.TranslatedContent;
import com.hilbert.shared.common.enums.Language;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

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

        HashMap<String, String> expectedTranslation = new HashMap<>();
        expectedTranslation.put("Bonjour", "Hello");
        expectedTranslation.put("je", "I");
        expectedTranslation.put("suis", "am");
        expectedTranslation.put("heureux", "happy");

        TranslatedContent expectedTranslatedContent = new TranslatedContent(expectedTranslation, srcLanguage, destLanguage);

        // Act
        TranslatedContent resultContent = articleTranslatorService.translateContent(content, srcLanguage, destLanguage);

        // Assert
        assertThat(resultContent).isNotNull();
        assertThat(resultContent.getTranslationMap()).isEqualTo(expectedTranslatedContent.getTranslationMap());
    }
}
