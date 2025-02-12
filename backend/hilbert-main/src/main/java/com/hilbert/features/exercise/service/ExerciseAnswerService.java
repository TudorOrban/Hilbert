package com.hilbert.features.exercise.service;

import com.hilbert.features.exercise.model.Exercise;
import com.hilbert.features.exercise.model.ExerciseAnswerData;

public interface ExerciseAnswerService {

    boolean verifyExerciseAnswer(Exercise exercise, ExerciseAnswerData answerData);
}
