package com.hilbert.features.article.service;

import com.hilbert.features.article.model.TranslatedContent;
import com.hilbert.shared.common.enums.Language;
import org.springframework.stereotype.Service;

@Service
public class ArticleTranslatorServiceImpl implements ArticleTranslatorService {

    public TranslatedContent translateContent(String content, Language srcLanguage, Language destLanguage) {
        return null;
    }
}
