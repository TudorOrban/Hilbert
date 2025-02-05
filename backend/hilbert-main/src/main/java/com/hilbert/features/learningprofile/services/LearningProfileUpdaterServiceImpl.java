package com.hilbert.features.learningprofile.services;

import com.hilbert.features.learningprofile.model.LearningProfile;
import com.hilbert.features.learningprofile.repository.LearningProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LearningProfileUpdaterServiceImpl implements LearningProfileUpdaterService {

    private final LearningProfileRepository profileRepository;

    @Autowired
    public LearningProfileUpdaterServiceImpl(
            LearningProfileRepository profileRepository
    ) {
        this.profileRepository = profileRepository;
    }

    public void updateLearningDataOnReadArticle(Long userId, Integer articleId) {
        Optional<LearningProfile> learningProfileOpt = profileRepository.findByUserId(userId);

    }
}
