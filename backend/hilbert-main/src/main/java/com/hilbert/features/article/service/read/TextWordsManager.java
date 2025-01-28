package com.hilbert.features.article.service.read;

import java.util.List;

public interface TextWordsManager {

    List<String> getTextWords(String text, Boolean allowDuplicates);
}
