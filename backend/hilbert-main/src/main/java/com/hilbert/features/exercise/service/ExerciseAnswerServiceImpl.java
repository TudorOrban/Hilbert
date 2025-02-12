package com.hilbert.features.exercise.service;

import com.hilbert.features.exercise.model.Exercise;
import com.hilbert.features.exercise.model.ExerciseAnswerData;
import com.hilbert.features.exercise.model.ExerciseType;
import com.hilbert.shared.error.types.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class ExerciseAnswerServiceImpl implements ExerciseAnswerService {

    public boolean verifyExerciseAnswer(Exercise exercise, ExerciseAnswerData answerData) {
        return switch (exercise.getExerciseData().getExerciseType()) {
            case ExerciseType.SRC_TARGET_TRANSLATION, ExerciseType.TARGET_SRC_TRANSLATION -> this.verifyTranslationAnswer(exercise, answerData);
            case ExerciseType.SENTENCE_COMPLETION -> this.verifySentenceCompletionAnswer(exercise, answerData);
            case ExerciseType.ANSWER_QUESTION -> this.verifyAnswerQuestionAnswer(exercise, answerData);
        };
    }

    private boolean verifyTranslationAnswer(Exercise exercise, ExerciseAnswerData answerData) {
        for (Map.Entry<Integer, String> entry : answerData.getDestIndexWordMap().entrySet()) {
            Integer wordIndex = entry.getKey();
            String wordAnswer = entry.getValue();

            if (exercise.getExerciseData().getDestMissingWordIndices().get(wordIndex) == null) {
                throw new ValidationException("Invalid Answer Data");
            }
            if (!exercise.getExerciseData().getDestWordOptions().contains(wordAnswer)) {
                throw new ValidationException("Invalid Answer Data");
            }

            boolean isWordCorrect = Objects.equals(
                    exercise.getExerciseData().getDestSentence().get(wordIndex), wordAnswer
            );
            if (!isWordCorrect) {
                return false;
            }
        }

        return true;
    }

    private boolean verifySentenceCompletionAnswer(Exercise exercise, ExerciseAnswerData answerData) {
        for (Map.Entry<Integer, String> entry : answerData.getIndexWordMap().entrySet()) {
            Integer wordIndex = entry.getKey();
            String wordAnswer = entry.getValue();

            if (exercise.getExerciseData().getMissingWordIndices().get(wordIndex) == null) {
                throw new ValidationException("Invalid Answer Data");
            }
            if (!exercise.getExerciseData().getWordOptions().contains(wordAnswer)) {
                throw new ValidationException("Invalid Answer Data");
            }

            boolean isWordCorrect = Objects.equals(
                    exercise.getExerciseData().getSentence().get(wordIndex), wordAnswer
            );
            if (!isWordCorrect) {
                return false;
            }
        }

        return true;
    }

    private boolean verifyAnswerQuestionAnswer(Exercise exercise, ExerciseAnswerData answerData) {
        return Objects.equals(exercise.getExerciseData().getCorrectAnswer(), answerData.getQuestionAnswer());
    }
}