package com.hilbert.features.learningprofile.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
        mapper.registerModule(new JavaTimeModule());
        try {
            this.learningData = mapper.readValue(this.learningDataJson, LearningProfileData.class);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Learning Data found: " + e.getMessage());
        }
        return this.learningData;
    }

    public void setLearningData(LearningProfileData data) {
        this.learningData = data;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            this.learningDataJson = mapper.writeValueAsString(this.learningData);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Learning Data received: " + e.getMessage());
        }
    }

    @Column(name = "learning_configuration", columnDefinition = "TEXT")
    private String learningConfigurationJson;

    @Transient
    private LearningConfiguration learningConfiguration;

    public LearningConfiguration getLearningConfiguration() {
        if (this.learningConfiguration != null || this.learningConfigurationJson == null) {
            return this.learningConfiguration;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            this.learningConfiguration = mapper.readValue(this.learningConfigurationJson, LearningConfiguration.class);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Learning Configuration found: " + e.getMessage());
        }
        return this.learningConfiguration;
    }

    public void setLearningConfiguration(LearningConfiguration configuration) {
        this.learningConfiguration = configuration;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            this.learningConfigurationJson = mapper.writeValueAsString(this.learningConfiguration);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Learning Configuration received: " + e.getMessage());
        }
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
