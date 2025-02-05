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
import java.util.Collections;


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

        LearningProfileData learningProfileData = learningProfile.getLearningData();
        this.updateGeneralDataOnReadArticle(learningProfileData, article);
        this.updateLanguageDataOnReadArticle(learningProfileData, article);

        learningProfile.setLearningData(learningProfileData);
        profileRepository.save(learningProfile);
    }

    private void updateGeneralDataOnReadArticle(LearningProfileData learningProfileData, Article article) {
        LocalDateTime now = LocalDateTime.now();

        boolean isLastActiveDateToday = learningProfileData.getLastActiveDate() != null &&
                now.toLocalDate().equals(learningProfileData.getLastActiveDate().toLocalDate());
        if (!isLastActiveDateToday) {
            learningProfileData.setLastActiveDate(now);
            long totalLearningDays = learningProfileData.getTotalLearningDays() != null ?
                    learningProfileData.getTotalLearningDays() : 0;
            learningProfileData.setTotalLearningDays(totalLearningDays + 1);
        }

        if (learningProfileData.getCurrentStreakEndDate() == null) {
            LocalDateTime streakStartDate = learningProfileData.getCurrentStreakStartDate() != null ?
                    learningProfileData.getCurrentStreakStartDate() : now;
            Long streakDays = ChronoUnit.DAYS.between(streakStartDate, now);
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
                now, now, 0L, 0.0, new ArrayList<>(), 0L
            );
        }

        boolean isLastActiveDateToday = languageData.getLastActiveDate() != null &&
                now.toLocalDate().equals(languageData.getLastActiveDate().toLocalDate());
        if (!isLastActiveDateToday) {
            languageData.setLastActiveDate(now);
            long totalLearningDays = learningProfileData.getTotalLearningDays() != null ?
                    learningProfileData.getTotalLearningDays() : 0;
            languageData.setTotalLearningDays(totalLearningDays);
        }

        Double gainedXP = this.getGainedXPByReadArticle(article);
        languageData.setTotalXp(gainedXP);

        if (languageData.getReadArticleIds() != null) {
            languageData.getReadArticleIds().add(article.getId());
        } else {
            languageData.setReadArticleIds(new ArrayList<>(Collections.singletonList(article.getId())));
        }

        // TODO: Update vocabulary count

        learningProfileData.getLanguageData().put(article.getLanguage(), languageData);
    }

    private Double getGainedXPByReadArticle(Article article) {
        // To be expanded once (word -> DifficultyLevel) mapping is implemented
        Double multiplier = 0.2;
        Long wordCount = article.getWordCount() != null ? article.getWordCount() : 10L;

        return multiplier * wordCount;
    }
}
