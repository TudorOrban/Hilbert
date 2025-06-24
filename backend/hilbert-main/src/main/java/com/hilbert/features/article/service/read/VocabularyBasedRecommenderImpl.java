package com.hilbert.features.article.service.read;

import com.hilbert.features.article.model.Article;
import com.hilbert.features.article.repository.ArticleRepository;
import com.hilbert.features.vocabulary.model.Vocabulary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class VocabularyBasedRecommenderImpl implements VocabularyBasedRecommender {

    private final ArticleRepository repository;

    private static final int IS_LEARNED_THRESHOLD = 50;

    @Autowired
    public VocabularyBasedRecommenderImpl(ArticleRepository repository) {
        this.repository = repository;
    }

    public List<Article> recommendArticles(Vocabulary vocabulary, int numberOfRecommendations) {
        HashMap<String, Float> wordRecencyScores = this.determineRecencyScores(vocabulary);

        HashMap<Article, Float> articleScores = new HashMap<>();

        List<Article> articles = repository.findAll(); // TODO: Use pagination

        float MIN_KNOWN_WORD_PERCENTAGE = 0.60f; // At least 60% known words for good comprehension
        float MAX_NEW_WORD_PERCENTAGE = 0.10f;  // Max 10% new words to avoid overwhelming

        float BASE_SCORE_WEIGHT = 0.5f; // For general fitness
        float REFRESH_SCORE_WEIGHT = 0.4f; // Strong weight for words needing refresh
        float NEW_WORD_PENALTY_WEIGHT = 1.5f; // Strong penalty for too many new words
        float KNOWN_WORD_FITNESS_WEIGHT = 0.1f; // Slight boost/penalty for known word balance

        for (Article article : articles) {
            float currentArticleScore = 0.0f;
            int totalWordsInArticle = 0;
            int knownWordsInArticle = 0;
            int wordsToRefreshInArticle = 0;
            int newWordsInArticle = 0;

            List<String> articleWords = List.of(article.getContent().split(" "));
            if (articleWords.isEmpty()) continue;

            // First pass: categorize words and sum up basic refresh contribution
            float sumOfRefreshPriorities = 0.0f;

            for (String word : articleWords) {
                totalWordsInArticle++;

                if (vocabulary.getVocabularyData().getWordsReadDates().containsKey(word)) {
                    if (vocabulary.getVocabularyData().getWordsReadDates().get(word).size() >= IS_LEARNED_THRESHOLD) {
                        knownWordsInArticle++;
                    } else {
                        wordsToRefreshInArticle++;
                        Float recency = wordRecencyScores.get(word);
                        if (recency != null) {
                            sumOfRefreshPriorities += (1.0f - recency); // Lower recency means higher priority
                        } else {
                            sumOfRefreshPriorities += 0.5f; // Neutral priority
                        }
                    }
                } else {
                    newWordsInArticle++;
                }
            }

            float knownWordPercentage = (float) knownWordsInArticle / totalWordsInArticle;
            float wordsToRefreshPercentage = (float) wordsToRefreshInArticle / totalWordsInArticle;
            float newWordPercentage = (float) newWordsInArticle / totalWordsInArticle;

            // 1. Base Score (contribution from refresh words)
            float averageRefreshPriority = (wordsToRefreshInArticle > 0) ? sumOfRefreshPriorities / wordsToRefreshInArticle : 0.0f;
            currentArticleScore += REFRESH_SCORE_WEIGHT * wordsToRefreshPercentage * averageRefreshPriority;

            // 2. Known Word Fitness: Ensure enough known words for comfortable reading
            if (knownWordPercentage < MIN_KNOWN_WORD_PERCENTAGE) {
                currentArticleScore -= KNOWN_WORD_FITNESS_WEIGHT * (MIN_KNOWN_WORD_PERCENTAGE - knownWordPercentage);
            } else {
                currentArticleScore += KNOWN_WORD_FITNESS_WEIGHT * (knownWordPercentage - MIN_KNOWN_WORD_PERCENTAGE) * 0.5f;
            }

            // 3. New Word Penalty: Crucial for managing difficulty
            if (newWordPercentage > MAX_NEW_WORD_PERCENTAGE) {
                currentArticleScore -= NEW_WORD_PENALTY_WEIGHT * (newWordPercentage - MAX_NEW_WORD_PERCENTAGE);
            }

             currentArticleScore += (newWordPercentage > 0 && newWordPercentage <= MAX_NEW_WORD_PERCENTAGE) ? 0.05f : 0;

            // 4. Overall Readability/Engagement
            currentArticleScore += BASE_SCORE_WEIGHT;

            currentArticleScore = Math.max(0, currentArticleScore);

            if (currentArticleScore > 0) {
                articleScores.put(article, currentArticleScore);
            }
        }

        // Extract article list sorted by score
        List<Map.Entry<Article, Float>> sortedArticles = new ArrayList<>(articleScores.entrySet());
        sortedArticles.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        List<Article> recommendedArticles = new ArrayList<>();
        for (Map.Entry<Article, Float> entry : sortedArticles) {
            recommendedArticles.add(entry.getKey());
        }

        return recommendedArticles.stream()
                .limit(numberOfRecommendations)
                .toList();
    }

    private HashMap<String, Float> determineRecencyScores(Vocabulary vocabulary) {
        HashMap<String, Float> recencyScores = new HashMap<>();
        LocalDate now = LocalDate.now();

        float DECAY_CONSTANT = 0.05f;

        for (Map.Entry<String, List<LocalDateTime>> entry : vocabulary.getVocabularyData().getWordsReadDates().entrySet()) {
            String word = entry.getKey();
            List<LocalDateTime> wordDates = entry.getValue();
            if (wordDates.isEmpty()) {
                continue;
            }
            int wordCount = wordDates.size();

            if (wordCount > IS_LEARNED_THRESHOLD) {
                continue; // No need for refresh
            }

            LocalDateTime lastReadDate = wordDates.stream()
                    .max(LocalDateTime::compareTo)
                    .orElse(null);

            long hoursSinceLastRead = Duration.between(lastReadDate, now).toHours();
            float recencyScore = (float) Math.exp(-DECAY_CONSTANT * hoursSinceLastRead);

            recencyScore *= (float) (1 + ((double) wordCount / IS_LEARNED_THRESHOLD) * 0.1);

            recencyScores.put(word, recencyScore);
        }

        return recencyScores;
    }
}
