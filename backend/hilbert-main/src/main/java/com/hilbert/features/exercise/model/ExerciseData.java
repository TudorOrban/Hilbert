package com.hilbert.features.exercise.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseData {

    private ExerciseType exerciseType;
    private ExerciseAnswerType answerType;

    // Translation
    private List<String> srcSentence;
    private List<String> destSentence;
    private List<Integer> destMissingWordIndices;
    private Boolean translateFull;
    private List<String> destWordOptions;
    private HashMap<String, String> wordTranslations;

    // Completion
    private List<String> sentence;
    private List<Integer> missingWordIndices;
    private Boolean completeFull;
    private List<String> wordOptions;

    // Answer Question
    private List<String> text;
    private String question;
    private List<String> answerOptions;
    private String correctAnswer;
}
