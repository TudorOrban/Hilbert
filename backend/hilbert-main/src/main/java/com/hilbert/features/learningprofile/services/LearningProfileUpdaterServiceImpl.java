package com.hilbert.features.learningprofile.services;

import com.hilbert.features.article.model.Article;
import com.hilbert.features.article.repository.ArticleRepository;
import com.hilbert.features.learningprofile.model.LearningLanguageData;
import com.hilbert.features.learningprofile.model.LearningProfile;
import com.hilbert.features.learningprofile.model.LearningProfileData;
import com.hilbert.features.learningprofile.repository.LearningProfileRepository;
import com.hilbert.shared.error.types.ResourceIdentifierType;
import com.hilbert.shared.error.types.ResourceNotFoundException;
import com.hilbert.shared.error.types.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


@Service
public class LearningProfileUpdaterServiceImpl implements LearningProfileUpdaterService {

    private final LearningProfileRepository profileRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public LearningProfileUpdaterServiceImpl(
            LearningProfileRepository profileRepository,
            ArticleRepository articleRepository
    ) {
        this.profileRepository = profileRepository;
        this.articleRepository = articleRepository;
    }

    public void updateLearningDataOnReadArticle(Long userId, Long articleId) {
        LearningProfile learningProfile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(userId.toString(), ResourceType.USER_PROFILE, ResourceIdentifierType.USER_ID));
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException(articleId.toString(), ResourceType.ARTICLE, ResourceIdentifierType.ID));

        this.updateGeneralDataOnReadArticle(learningProfile.getLearningData(), article);
        this.updateLanguageDataOnReadArticle(learningProfile.getLearningData(), article);

        profileRepository.save(learningProfile);
    }

    private void updateGeneralDataOnReadArticle(LearningProfileData learningProfileData, Article article) {
        LocalDateTime now = LocalDateTime.now();

        if (!now.toLocalDate().equals(learningProfileData.getLastActiveDate().toLocalDate())) {
            learningProfileData.setLastActiveDate(now);
            learningProfileData.setTotalLearningDays(learningProfileData.getTotalLearningDays() + 1);
        }

        if (learningProfileData.getCurrentStreakEndDate() == null) {
            Long streakDays = ChronoUnit.DAYS.between(learningProfileData.getCurrentStreakStartDate(), now);
            learningProfileData.setCurrentStreakDays(streakDays);
        } else {
            // Start new streak
            learningProfileData.setCurrentStreakStartDate(now);
            learningProfileData.setCurrentStreakEndDate(null);
            learningProfileData.setCurrentStreakDays(1L);
        }

        Double gainedXP = this.getGainedXPByReadArticle(article);
        learningProfileData.setTotalXp(gainedXP);
    }

    private void updateLanguageDataOnReadArticle(LearningProfileData learningProfileData, Article article) {
        LocalDateTime now = LocalDateTime.now();

        LearningLanguageData languageData = learningProfileData.getLanguageData().get(article.getLanguage());
        if (languageData == null) {
            languageData = new LearningLanguageData(
                now, now, 0, 0.0, new ArrayList<>(), 0
            );
        }

        if (!now.toLocalDate().equals(languageData.getLastActiveDate().toLocalDate())) {
            languageData.setLastActiveDate(now);
            languageData.setTotalLearningDays(languageData.getTotalLearningDays() + 1);
        }

        Double gainedXP = this.getGainedXPByReadArticle(article);
        languageData.setTotalXp(gainedXP);

        languageData.getReadArticleIds().add(article.getId());

        // TODO: Update vocabulary count

        learningProfileData.getLanguageData().put(article.getLanguage(), languageData);
    }

    private Double getGainedXPByReadArticle(Article article) {
        // To be expanded once (word -> DifficultyLevel) mapping is implemented
        Double multiplier = 0.2;

        return multiplier * article.getWordCount();
    }
}
