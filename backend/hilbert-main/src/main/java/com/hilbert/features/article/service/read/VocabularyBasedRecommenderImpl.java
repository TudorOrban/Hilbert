package com.hilbert.features.article.service.read;

import com.hilbert.features.article.model.Article;
import com.hilbert.features.vocabulary.model.VocabularyData;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VocabularyBasedRecommenderImpl implements VocabularyBasedRecommender {

    private List<Article> recommendArticles(VocabularyData data) {
        HashMap<String, List<LocalDateTime>> wordsToBeRefreshed = new HashMap<>();

        int TOO_MANY_THRESHOLD = 50;
        int TOO_SOON_THRESHOLD = 10; // 10 refreshes of a word per day should be enough

        for (Map.Entry<String, List<LocalDateTime>> entry : data.getWordsReadDates().entrySet()) {
            List<LocalDateTime> wordDates = entry.getValue();
            if (wordDates.isEmpty()) {
                continue;
            }
            int wordCount = wordDates.size();

            if (wordCount > TOO_MANY_THRESHOLD) {
                continue; // No need for refresh
            }

            int dayCounter = 0;
            LocalDateTime lastDate = wordDates.getFirst();

            for (LocalDateTime date : wordDates) {
                if (lastDate.toLocalDate().equals(date.toLocalDate())) {
                    dayCounter++;
                } else {
                    dayCounter = 0;
                }
                lastDate = date;

            }

        }


        return new ArrayList<>();
    }
}
