package com.hilbert.features.learningprofile.services;

import com.hilbert.features.learningprofile.dto.LearningProfileFullDto;
import com.hilbert.features.learningprofile.dto.LearningProfileMapper;
import com.hilbert.features.learningprofile.model.LearningProfile;
import com.hilbert.features.learningprofile.model.LearningProfileData;
import com.hilbert.features.learningprofile.repository.LearningProfileRepository;
import com.hilbert.shared.common.enums.Language;
import com.hilbert.shared.error.types.ResourceAlreadyExistsException;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Service
public class LearningProfileServiceImpl implements LearningProfileService {

    private final LearningProfileRepository learningProfileRepository;

    @Autowired
    public LearningProfileServiceImpl(LearningProfileRepository learningProfileRepository) {
        this.learningProfileRepository = learningProfileRepository;
    }

    public LearningProfileFullDto getByUserId(Long userId, Boolean createIfMissing) {
        Optional<LearningProfile> learningProfileOpt = learningProfileRepository.findByUserId(userId);
        if (learningProfileOpt.isEmpty()) {
            if (createIfMissing) {
                return this.createLearningProfile(userId);
            } else {
                throw new ResourceNotFoundException(userId.toString(), ResourceType.USER_PROFILE, ResourceIdentifierType.USER_ID);
            }
        }

        return this.mapProfileToProfileFullDto(learningProfileOpt.get());
    }

    public LearningProfileFullDto createLearningProfile(Long userId) {
        Optional<LearningProfile> existingProfileOpt = learningProfileRepository.findByUserId(userId);
        if (existingProfileOpt.isPresent()) {
            throw new ResourceAlreadyExistsException(userId.toString(), ResourceType.USER_PROFILE, ResourceIdentifierType.USER_ID);
        }

        LearningProfile learningProfile = new LearningProfile();
        learningProfile.setUserId(userId);

        LocalDateTime now = LocalDateTime.now();
        LearningProfileData learningData = new LearningProfileData(
                now, now, 0L, now, null, 0L, 0.0, Language.NONE, new HashMap<>()
        );
        learningProfile.setLearningData(learningData);

        LearningProfile savedLearningProfile = learningProfileRepository.save(learningProfile);

        return this.mapProfileToProfileFullDto(savedLearningProfile);
    }

    private LearningProfileFullDto mapProfileToProfileFullDto(LearningProfile profile) {
        return LearningProfileMapper.INSTANCE.profileToProfileFullDto(profile);
    }
}
