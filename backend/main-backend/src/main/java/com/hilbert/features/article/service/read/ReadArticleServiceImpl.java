package com.hilbert.features.article.service.read;

import com.hilbert.core.user.repository.UserRepository;
import com.hilbert.features.article.dto.ReadArticleDto;
import com.hilbert.features.article.model.Article;
import com.hilbert.features.article.repository.ArticleRepository;
import com.hilbert.features.vocabulary.model.ReadWords;
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

        ReadWords readWords = this.determineUpdatedReadWords(vocabulary, article);

        Vocabulary savedVocabulary = vocabularyService.updateReadWords(vocabulary.getId(), readWords);

        // TODO: Mark article as read for user

        return savedVocabulary;
    }

    private ReadWords determineUpdatedReadWords(Vocabulary vocabulary, Article article) {
        List<String> words = textWordsManager.getTextWords(article.getContent(), true);
        ReadWords readWords = vocabulary.getReadWords();
        LocalDateTime now = LocalDateTime.now();

        for (String word : words) {
            readWords.getWordsReadDates().computeIfAbsent(word, k -> new ArrayList<>()).add(now);
        }

        return readWords;
    }
}
