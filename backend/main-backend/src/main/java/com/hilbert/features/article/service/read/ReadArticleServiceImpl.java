package com.hilbert.features.article.service.read;

import com.hilbert.core.user.repository.UserRepository;
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

    public void readArticle(Long userId, Long articleId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(userId.toString(), ResourceType.USER, ResourceIdentifierType.ID);
        }

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(articleId.toString(), ResourceType.ARTICLE, ResourceIdentifierType.ID));

        Vocabulary vocabulary = vocabularyService.findOrCreateVocabulary(userId, article.getLanguage());

        ReadWords readWords = this.determineUpdatedReadWords(vocabulary, article);

        Vocabulary savedVocabulary = vocabularyService.updateReadWords(vocabulary.getId(), readWords);

        // TODO: Mark article as read for user
    }

    private ReadWords determineUpdatedReadWords(Vocabulary vocabulary, Article article) {
        return null;
    }
}
