import { ExerciseData, ExerciseFullDto } from "./Exercise";


export interface GrammarLessonDto {
    id: number;
    creatorId: number;
    createdAt: Date;
    updatedAt: Date;
    lessonData: LessonData;
    exercises: ExerciseFullDto[];
}

export interface LessonData {
    exerciseIds: number[];
    keepOrder: boolean;
}

export interface CreateLessonDto {
    creatorId: number;
    exercises: LessonExercise[];
    keepOrder: boolean;
}

export interface LessonExercise {
    exerciseType: LessonExerciseType;
    existingExerciseId: number;
    newExerciseData: ExerciseData;
}

export enum LessonExerciseType {
    NEW = "NEW",
    EXISTING = "EXISTING"
}