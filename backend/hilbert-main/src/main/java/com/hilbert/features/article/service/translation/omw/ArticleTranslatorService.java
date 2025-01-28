package com.hilbert.features.article.service.translation.omw;

import com.hilbert.features.article.dto.TranslationRequestDto;
import com.hilbert.features.article.dto.TranslationResponseDto;

public interface ArticleTranslatorService {

    TranslationResponseDto translateContent(TranslationRequestDto translationRequestDto);
}
