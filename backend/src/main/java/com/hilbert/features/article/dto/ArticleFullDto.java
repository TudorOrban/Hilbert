package com.hilbert.features.article.dto;

import com.hilbert.features.article.model.DifficultyLevel;
import com.hilbert.features.article.model.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleFullDto {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Language language;
    private DifficultyLevel level;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Double averageRating;
    private Integer numberOfRatings;
    private Integer readCount;
    private Integer bookmarkCount;
}
