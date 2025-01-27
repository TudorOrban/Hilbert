package com.hilbert.features.article.service.read;

import com.hilbert.features.article.dto.ReadArticleDto;
import com.hilbert.features.vocabulary.model.Vocabulary;

public interface ReadArticleService {

    Vocabulary readArticle(ReadArticleDto readArticleDto);
}
