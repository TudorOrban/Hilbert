package com.hilbert.features.vocabulary.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Column(name = "read_words", columnDefinition = "TEXT")
    private String readWordsJson;

    @Transient
    private ReadWords readWords;

    public ReadWords getReadWords() {
        if (this.readWords != null || this.readWordsJson == null) {
            return this.readWords;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.readWords = objectMapper.readValue(this.readWordsJson, ReadWords.class);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Read Words found: " + e.getMessage());
        }
        return this.readWords;
    }

    public void setReadWords(ReadWords readWords) {
        this.readWords = readWords;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.readWordsJson = objectMapper.writeValueAsString(readWords);
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
