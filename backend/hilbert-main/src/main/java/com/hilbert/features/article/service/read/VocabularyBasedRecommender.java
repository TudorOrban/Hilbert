package com.hilbert.features.article.service.read;

import com.hilbert.features.article.model.Article;
import com.hilbert.features.vocabulary.model.Vocabulary;

import java.util.List;

public interface VocabularyBasedRecommender {

    List<Article> recommendArticles(Vocabulary vocabulary, int numberOfRecommendations);
}
