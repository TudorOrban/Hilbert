package com.hilbert.features.learningprofile.repository;

import com.hilbert.features.learningprofile.model.LearningProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LearningProfileRepository extends JpaRepository<LearningProfile, Long> {

    Optional<LearningProfile> findByUserId(Long userId);
}
