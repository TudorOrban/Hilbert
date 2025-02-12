package com.hilbert.features.exercise.service;

import com.hilbert.features.exercise.model.Exercise;
import com.hilbert.features.exercise.model.ExerciseAnswerData;
import com.hilbert.features.exercise.model.ExerciseType;
import com.hilbert.shared.error.types.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ExerciseAnswerServiceImpl implements ExerciseAnswerService {

    public boolean verifyExerciseAnswer(Exercise exercise, ExerciseAnswerData answerData) {
        return switch (exercise.getExerciseData().getExerciseType()) {
            case ExerciseType.SRC_TARGET_TRANSLATION -> this.verifySrcTargetTranslationAnswer(exercise, answerData);
            case ExerciseType.TARGET_SRC_TRANSLATION -> this.verifyTargetSrcTranslationAnswer(exercise, answerData);
            case ExerciseType.SENTENCE_COMPLETION -> this.verifySentenceCompletionAnswer(exercise, answerData);
            case ExerciseType.ANSWER_QUESTION -> this.verifyAnswerQuestionAnswer(exercise, answerData);
        };
    }

    public boolean verifySrcTargetTranslationAnswer(Exercise exercise, ExerciseAnswerData answerData) {
        for (Map.Entry<Integer, String> entry : answerData.getDestIndexWordMap().entrySet()) {
            if (exercise.getExerciseData().getDestMissingWordIndices().get(entry.getKey()) == null) {
                throw new ValidationException("Invalid Answer Data");
            }
        }


        return false;
    }

    public boolean verifyTargetSrcTranslationAnswer(Exercise exercise, ExerciseAnswerData answerData) {

        return false;
    }

    public boolean verifySentenceCompletionAnswer(Exercise exercise, ExerciseAnswerData answerData) {

        return false;
    }

    public boolean verifyAnswerQuestionAnswer(Exercise exercise, ExerciseAnswerData answerData) {

        return false;
    }
}