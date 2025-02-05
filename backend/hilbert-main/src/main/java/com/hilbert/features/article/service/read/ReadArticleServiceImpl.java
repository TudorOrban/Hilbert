package com.hilbert.features.article.service.read;

import com.hilbert.core.user.repository.UserRepository;
import com.hilbert.features.article.dto.ReadArticleDto;
import com.hilbert.features.article.dto.ReadArticleSummaryDto;
import com.hilbert.features.article.model.Article;
import com.hilbert.features.article.repository.ArticleRepository;
import com.hilbert.features.learningprofile.services.LearningProfileUpdaterService;
import com.hilbert.features.vocabulary.model.VocabularyData;
import com.hilbert.features.vocabulary.model.Vocabulary;
import com.hilbert.features.vocabulary.service.VocabularyService;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ReadArticleServiceImpl implements ReadArticleService {

    private final ArticleRepository articleRepository;
    private final VocabularyService vocabularyService;
    private final UserRepository userRepository;
    private final LearningProfileUpdaterService profileUpdaterService;
    private final TextWordsManager textWordsManager;

    @Autowired
    public ReadArticleServiceImpl(
            ArticleRepository articleRepository,
            VocabularyService vocabularyService,
            UserRepository userRepository,
            LearningProfileUpdaterService profileUpdaterService,
            TextWordsManager textWordsManager
    ) {
        this.articleRepository = articleRepository;
        this.vocabularyService = vocabularyService;
        this.userRepository = userRepository;
        this.profileUpdaterService = profileUpdaterService;
        this.textWordsManager = textWordsManager;
    }

    @Transactional
    public ReadArticleSummaryDto readArticle(ReadArticleDto readArticleDto) {
        Long userId = readArticleDto.getUserId();
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(userId.toString(), ResourceType.USER, ResourceIdentifierType.ID);
        }

        Article article = articleRepository.findById(readArticleDto.getArticleId())
                .orElseThrow(() -> new ResourceNotFoundException(readArticleDto.getArticleId().toString(), ResourceType.ARTICLE, ResourceIdentifierType.ID));

        Vocabulary vocabulary = vocabularyService.findOrCreateVocabulary(userId, article.getLanguage());

        Pair<VocabularyData, ReadArticleSummaryDto> result = this.determineUpdatedVocabularyData(vocabulary, article);

        vocabularyService.updateVocabularyData(vocabulary.getId(), result.getFirst());

        profileUpdaterService.updateLearningDataOnReadArticle(userId, article.getId());

        return result.getSecond();
    }

    private Pair<VocabularyData, ReadArticleSummaryDto> determineUpdatedVocabularyData(Vocabulary vocabulary, Article article) {
        List<String> words = textWordsManager.getTextWords(article.getContent(), true);
        VocabularyData vocabularyData = vocabulary.getVocabularyData();
        if (vocabularyData == null || vocabularyData.getWordsReadDates() == null) {
            vocabularyData = new VocabularyData(new HashMap<>(), new ArrayList<>());
        }
        ReadArticleSummaryDto readArticleSummary = new ReadArticleSummaryDto(new HashMap<>());
        LocalDateTime now = LocalDateTime.now();

        // Add newly read words to vocabulary and summary
        for (String word : words) {
            vocabularyData.getWordsReadDates().computeIfAbsent(word, k -> new ArrayList<>()).add(now);
            readArticleSummary.getNewWords().merge(word, 1, Integer::sum);
        }

        // Register article as read
        if (vocabularyData.getReadArticles().isEmpty()) {
            vocabularyData.setReadArticles(new ArrayList<>(List.of(article.getId())));
        } else {
            vocabularyData.getReadArticles().add(article.getId());
        }

        return Pair.of(vocabularyData, readArticleSummary);
    }
}
