package com.hilbert.features.article.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCommentDto {
    private Long id;
    private Long articleId;
    private Long userId;
    private String username;
    private String content;
    private LocalDateTime createdAt;
}
