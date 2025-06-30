import { Language } from "../../../shared/language/models/Language";

export interface ExerciseFullDto {
    id: number;
    creatorId: number;
    createdAt: Date;
    updatedAt: Date;
    exerciseData: ExerciseData;
}

export interface ExerciseData {
    srcLanguage: Language;
    destLanguage: Language;
    
    exerciseType: ExerciseType;
    answerType: ExerciseAnswerType;

    // Translation
    srcSentence?: string[];
    destSentence?: string[];
    destMissingWordIndices?: number[];
    translateFull?: boolean;
    destWordOptions?: string[];
    wordTranslations?: Record<string, string>;

    // Completion
    sentence?: string[];
    missingWordIndices?: number[];
    completeFull?: boolean;
    wordOptions?: string[];

    // Answer Question
    text?: string[];
    question?: string;
    answerOptions?: string[];
    correctAnswer?: string;
}

export enum ExerciseType {
    TARGET_SRC_TRANSLATION = "TARGET_SRC_TRANSLATION",
    SRC_TARGET_TRANSLATION = "SRC_TARGET_TRANSLATION",
    SENTENCE_COMPLETION = "SENTENCE_COMPLETION",
    ANSWER_QUESTION = "ANSWER_QUESTION"
}

export enum ExerciseAnswerType {
    PICK_WORDS = "PICK_WORDS",
    WRITE = "WRITE",
    SAY = "SAY",
    PICK_ANSWER = "PICK_ANSWER"
}

export interface CreateExerciseDto {
    id: number;
    creatorId: number;
    exerciseData: ExerciseData;
}

export interface ExerciseAnswerDto {
    id: number;
    userId: number;
    exerciseData: ExerciseAnswerData;
}

export interface ExerciseAnswerData {
    destIndexWordMap?: Record<number, string>; // for translation
    indexWordMap?: Record<number, string>; // for completion
    questionAnswer?: string; // for Q&A
}
