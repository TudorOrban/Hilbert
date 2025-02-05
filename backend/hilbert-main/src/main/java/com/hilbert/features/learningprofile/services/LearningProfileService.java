package com.hilbert.features.learningprofile.services;

import com.hilbert.features.learningprofile.dto.LearningProfileFullDto;

public interface LearningProfileService {

    LearningProfileFullDto getByUserId(Long userId);
    LearningProfileFullDto createLearningProfile(Long userId);
}
