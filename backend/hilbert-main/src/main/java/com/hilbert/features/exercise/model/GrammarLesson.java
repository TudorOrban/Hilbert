package com.hilbert.features.exercise.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@Table(name = "grammar_lessons")
public class GrammarLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "lesson_data", columnDefinition = "TEXT")
    private String lessonDataJson;

    @Transient
    private LessonData lessonData;

    public LessonData getLessonData() {
        if (this.lessonDataJson == null || this.lessonData != null) {
            return this.lessonData;
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            this.lessonData = mapper.readValue(this.lessonDataJson, LessonData.class);
            return this.lessonData;
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Exercise Data on read: " + e);
        }
    }

    public void setLessonData(LessonData lessonData) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            this.lessonDataJson = mapper.writeValueAsString(lessonData);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Exercise Data on write: " + e);
        }
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt  = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
