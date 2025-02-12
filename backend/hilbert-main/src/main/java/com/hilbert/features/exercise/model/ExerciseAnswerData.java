package com.hilbert.features.exercise.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseAnswerData {
    private HashMap<Integer, String> destIndexWordMap; // for translation
    private HashMap<Integer, String> indexWordMap; // for completion
    private String questionAnswer; // for Q&A

}
