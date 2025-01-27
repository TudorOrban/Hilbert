package com.hilbert.features.article.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hilbert.shared.common.enums.ArticleStatus;
import com.hilbert.shared.common.enums.DifficultyLevel;
import com.hilbert.shared.common.enums.Language;

import com.hilbert.shared.error.types.ValidationException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DifficultyLevel level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArticleStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "word_count")
    private Integer wordCount;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "number_of_ratings")
    private Integer numberOfRatings;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "read_count")
    private Integer readCount;

    @Column(name = "comment_count")
    private Integer commentCount;

    @Column(name = "bookmark_count")
    private Integer bookmarkCount;


    // Manual (de)serialization and caching of JSON column
    @Column(name = "translated_content", columnDefinition = "TEXT")
    private String translatedContentJson;

    @Transient
    private TranslatedContent translatedContent;

    public TranslatedContent getTranslatedContent() {
        if (this.translatedContent != null || this.translatedContentJson == null) {
            return this.translatedContent;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            this.translatedContent = mapper.readValue(this.translatedContentJson, TranslatedContent.class);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Translated Content Data found: " + e.getMessage());
        }
        return this.translatedContent;
    }

    public void setTranslatedContent(TranslatedContent content) {
        this.translatedContent = content;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.translatedContentJson = objectMapper.writeValueAsString(translatedContent);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Translated Content Data received: " + e.getMessage());
        }
    }


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
