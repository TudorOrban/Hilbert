package com.hilbert.features.article.service;

import com.hilbert.features.article.model.TranslatedContent;
import com.hilbert.shared.common.enums.Language;

public interface ArticleTranslatorService {

    TranslatedContent translateContent(String content, Language srcLanguage, Language destLanguage);
}
