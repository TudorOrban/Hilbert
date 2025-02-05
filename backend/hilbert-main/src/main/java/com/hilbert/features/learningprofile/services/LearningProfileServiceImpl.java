package com.hilbert.features.learningprofile.services;

import com.hilbert.features.learningprofile.dto.LearningProfileFullDto;
import com.hilbert.features.learningprofile.dto.LearningProfileMapper;
import com.hilbert.features.learningprofile.model.LearningProfile;
import com.hilbert.features.learningprofile.model.LearningProfileData;
import com.hilbert.features.learningprofile.repository.LearningProfileRepository;
import com.hilbert.shared.common.enums.Language;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class LearningProfileServiceImpl implements LearningProfileService {

    private final LearningProfileRepository learningProfileRepository;

    @Autowired
    public LearningProfileServiceImpl(LearningProfileRepository learningProfileRepository) {
        this.learningProfileRepository = learningProfileRepository;
    }

    public LearningProfileFullDto getByUserId(Long userId) {
        LearningProfile learningProfile = learningProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(userId.toString(), ResourceType.USER_PROFILE, ResourceIdentifierType.ID));

        return this.mapProfileToProfileFullDto(learningProfile);
    }

    public LearningProfileFullDto createLearningProfile(Long userId) {
        LearningProfile learningProfile = new LearningProfile();
        learningProfile.setUserId(userId);

        LocalDateTime now = LocalDateTime.now();
        LearningProfileData learningData = new LearningProfileData(
                now, now, 0, now, 0, 0.0, Language.NONE, new HashMap<>()
        );
        learningProfile.setLearningData(learningData);

        LearningProfile savedLearningProfile = learningProfileRepository.save(learningProfile);

        return this.mapProfileToProfileFullDto(savedLearningProfile);
    }

    private LearningProfileFullDto mapProfileToProfileFullDto(LearningProfile profile) {
        return LearningProfileMapper.INSTANCE.profileToProfileFullDto(profile);
    }
}
