package com.hilbert.features.exercise.repository;

import com.hilbert.features.exercise.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

}
