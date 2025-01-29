package com.hilbert.features.learningprofile.model;

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
@Table(name = "learning_profiles")
public class LearningProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    // Manual (de)serialization and caching of JSON columns
    @Column(name = "learning_data", columnDefinition = "TEXT")
    private String learningDataJson;

    @Transient
    private LearningProfileData learningData;

    public LearningProfileData getLearningData() {
        if (this.learningData != null || this.learningDataJson == null) {
            return this.learningData;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            this.learningData = mapper.readValue(this.learningDataJson, LearningProfileData.class);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Learning Data found: " + e.getMessage());
        }
        return this.learningData;
    }

    public void setLearningData(LearningProfileData data) {
        this.learningData = data;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.learningDataJson = objectMapper.writeValueAsString(this.learningData);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Learning Data received: " + e.getMessage());
        }
    }


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
