package com.hilbert.features.article.model;

import com.hilbert.shared.common.enums.ArticleStatus;
import com.hilbert.shared.common.enums.DifficultyLevel;
import com.hilbert.shared.common.enums.Language;

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
