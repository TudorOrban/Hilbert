package com.hilbert.features.exercise.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hilbert.shared.error.types.ValidationException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
 * Types of exercises:
 * 1. target -> src partial translation
 * 2. src -> target partial translation
 * 3. complete sentence (n choices)
 * 4.
 *
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "exercise_data", columnDefinition = "TEXT")
    private String exerciseDataJson;

    @Transient
    private ExerciseData exerciseData;

    public ExerciseData getExerciseData() {
        if (this.exerciseDataJson == null || this.exerciseData != null) {
            return this.exerciseData;
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            this.exerciseData = mapper.readValue(this.exerciseDataJson, ExerciseData.class);
            return this.exerciseData;
        } catch (JsonProcessingException e) {
            throw new ValidationException("Invalid Exercise Data on read: " + e);
        }
    }

    public void setExerciseData(ExerciseData exerciseData) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            this.exerciseDataJson = mapper.writeValueAsString(exerciseData);
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
