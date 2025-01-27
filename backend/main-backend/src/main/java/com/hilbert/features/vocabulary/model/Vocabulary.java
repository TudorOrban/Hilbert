package com.hilbert.features.vocabulary.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
@Table(name = "vocabularies")
public class Vocabulary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Manual (de)serialization and caching of JSON column
    @Column(name = "vocabulary_data", columnDefinition = "TEXT")
    private String vocabularyDataJson;

    @Transient
    private VocabularyData vocabularyData;

    public VocabularyData getVocabularyData() {
        if (this.vocabularyData != null || this.vocabularyDataJson == null) {
            return this.vocabularyData;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            this.vocabularyData = objectMapper.readValue(this.vocabularyDataJson, VocabularyData.class);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Read Words found: " + e.getMessage());
        }
        return this.vocabularyData;
    }

    public void setVocabularyData(VocabularyData vocabularyData) {
        this.vocabularyData = vocabularyData;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            this.vocabularyDataJson = objectMapper.writeValueAsString(vocabularyData);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Read Words received: " + e.getMessage());
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
