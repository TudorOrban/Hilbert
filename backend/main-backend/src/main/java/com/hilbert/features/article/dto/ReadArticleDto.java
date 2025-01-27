package com.hilbert.features.article.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadArticleDto {
    private Long userId;
    private Long articleId;
}
