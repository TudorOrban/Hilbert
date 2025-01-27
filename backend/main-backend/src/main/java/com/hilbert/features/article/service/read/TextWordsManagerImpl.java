package com.hilbert.features.article.service.read;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextWordsManagerImpl implements TextWordsManager{

    public List<String> getTextWords(String text) {
        String[] preWords = text.split(" ");

        List<String> words = new ArrayList<>();
        for (String preWord : preWords) {
            String word = preWord.replaceAll("[.,?!;:'\"]*", "");

            if (!words.contains(word)) {
                words.add(word);
            }
        }

        return words;
    }
}
