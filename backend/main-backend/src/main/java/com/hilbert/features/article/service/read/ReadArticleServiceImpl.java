package com.hilbert.features.article.service.read;

import com.hilbert.core.user.repository.UserRepository;
import com.hilbert.features.article.dto.ReadArticleDto;
import com.hilbert.features.article.model.Article;
import com.hilbert.features.article.repository.ArticleRepository;
import com.hilbert.features.vocabulary.model.VocabularyData;
import com.hilbert.features.vocabulary.model.Vocabulary;
import com.hilbert.features.vocabulary.service.VocabularyService;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadArticleServiceImpl implements ReadArticleService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final VocabularyService vocabularyService;
    private final TextWordsManager textWordsManager;

    @Autowired
    public ReadArticleServiceImpl(
            UserRepository userRepository,
            ArticleRepository articleRepository,
            VocabularyService vocabularyService,
            TextWordsManager textWordsManager
    ) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.vocabularyService = vocabularyService;
        this.textWordsManager = textWordsManager;
    }

    public Vocabulary readArticle(ReadArticleDto readArticleDto) {
        Long userId = readArticleDto.getUserId();
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(userId.toString(), ResourceType.USER, ResourceIdentifierType.ID);
        }

        Article article = articleRepository.findById(readArticleDto.getArticleId())
                .orElseThrow(() -> new ResourceNotFoundException(readArticleDto.getArticleId().toString(), ResourceType.ARTICLE, ResourceIdentifierType.ID));

        Vocabulary vocabulary = vocabularyService.findOrCreateVocabulary(userId, article.getLanguage());

        VocabularyData vocabularyData = this.determineUpdatedVocabularyData(vocabulary, article);

        Vocabulary savedVocabulary = vocabularyService.updateVocabularyData(vocabulary.getId(), vocabularyData);

        // TODO: Register article as read in future User Learning data as well

        return savedVocabulary;
    }

    private VocabularyData determineUpdatedVocabularyData(Vocabulary vocabulary, Article article) {
        List<String> words = textWordsManager.getTextWords(article.getContent(), true);
        VocabularyData vocabularyData = vocabulary.getVocabularyData();
        LocalDateTime now = LocalDateTime.now();

        // Add newly read words to vocabulary
        for (String word : words) {
            vocabularyData.getWordsReadDates().computeIfAbsent(word, k -> new ArrayList<>()).add(now);
        }

        // Register article as read
        if (vocabularyData.getReadArticles().isEmpty()) {
            vocabularyData.setReadArticles(new ArrayList<>(List.of(article.getId())));
        } else {
            vocabularyData.getReadArticles().add(article.getId());
        }

        return vocabularyData;
    }
}
