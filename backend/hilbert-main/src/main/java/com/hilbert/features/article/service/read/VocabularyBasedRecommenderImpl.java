package com.hilbert.features.article.service.read;

import com.hilbert.features.article.model.Article;
import com.hilbert.features.vocabulary.model.VocabularyData;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VocabularyBasedRecommenderImpl implements VocabularyBasedRecommender {

    private List<Article> recommendArticles(VocabularyData data) {
        HashMap<String, List<LocalDateTime>> wordsToBeRefreshed = new HashMap<>();

        HashMap<String, Float> recencyScores = this.determineRecencyScores(data);


        return new ArrayList<>();
    }

    private HashMap<String, Float> determineRecencyScores(VocabularyData data) {
        HashMap<String, Float> recencyScores = new HashMap<>();

        LocalDate now = LocalDate.now();

        int TOO_MANY_THRESHOLD = 50;
        int TOO_SOON_THRESHOLD = 10; // 10 refreshes of a word per day should be enough

        for (Map.Entry<String, List<LocalDateTime>> entry : data.getWordsReadDates().entrySet()) {
            String word = entry.getKey();
            List<LocalDateTime> wordDates = entry.getValue();
            if (wordDates.isEmpty()) {
                continue;
            }
            int wordCount = wordDates.size();

            if (wordCount > TOO_MANY_THRESHOLD) {
                continue; // No need for refresh
            }

            List<LocalDateTime> sortedDates = wordDates.stream().sorted().toList();
            LocalDate firstDate = sortedDates.getLast().toLocalDate();
            int totalRecency = firstDate.compareTo(now);

            for (LocalDateTime date : sortedDates) {
                int recency = date.toLocalDate().compareTo(now);
                float recencyRatio = (float) recency / totalRecency;

                recencyScores.merge(word, recencyRatio, Float::sum);
            }

        }

        return recencyScores;
    }
}
