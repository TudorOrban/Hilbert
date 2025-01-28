package com.hilbert.features.article.service.read;

import com.hilbert.features.article.dto.ReadArticleDto;
import com.hilbert.features.article.dto.ReadArticleSummaryDto;

public interface ReadArticleService {

    ReadArticleSummaryDto readArticle(ReadArticleDto readArticleDto);
}
