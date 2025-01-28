package com.hilbert.features.article.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleCommentDto {
    private Long articleId;
    private Long userId;
    private String content;
}
