package com.hilbert.features.article.service.read;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextWordsManagerImpl implements TextWordsManager {

    public List<String> getTextWords(String text, Boolean allowDuplicates) {
        String[] preWords = text.split(" ");

        List<String> words = new ArrayList<>();
        for (String preWord : preWords) {
            String word = preWord.replaceAll("[.,?!;:'\"]*", "");

            boolean shouldAddWord = !words.contains(word) || allowDuplicates;
            if (shouldAddWord) {
                words.add(word);
            }
        }

        return words;
    }
}
