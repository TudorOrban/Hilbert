package com.hilbert.features.article.service;

import com.hilbert.features.article.dto.TranslationRequestDto;
import com.hilbert.features.article.dto.TranslationResponseDto;
import com.hilbert.features.article.model.TranslatedContent;
import com.hilbert.shared.common.enums.Language;

public interface ArticleTranslatorService {

    TranslationResponseDto translateContent(TranslationRequestDto translationRequestDto);
}
