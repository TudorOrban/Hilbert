package com.hilbert.features.article.service.read;

import com.hilbert.core.user.repository.UserRepository;
import com.hilbert.features.article.model.Article;
import com.hilbert.features.article.repository.ArticleRepository;
import com.hilbert.features.vocabulary.model.Vocabulary;
import com.hilbert.features.vocabulary.repository.VocabularyRepository;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReadArticleServiceImpl implements ReadArticleService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final VocabularyRepository vocabularyRepository;

    @Autowired
    public ReadArticleServiceImpl(
            UserRepository userRepository,
            ArticleRepository articleRepository,
            VocabularyRepository vocabularyRepository
    ) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.vocabularyRepository = vocabularyRepository;
    }

    public void readArticle(Long userId, Long articleId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(userId.toString(), ResourceType.USER, ResourceIdentifierType.ID);
        }

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(articleId.toString(), ResourceType.ARTICLE, ResourceIdentifierType.ID));



        List<Vocabulary> vocabularies = vocabularyRepository.findByUserId(userId);

    }
}
