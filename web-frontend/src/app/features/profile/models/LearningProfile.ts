import { Language } from "../../../shared/language/models/Language";
import { DifficultyLevel } from "../../articles/models/Article";

export interface LearningProfileDto {
    id: number;
    userId: number;
    createdAt: string;
    learningData: LearningProfileData;
}

export interface LearningProfileData {
    startDate: string;
    lastActiveDate: string;
    totalLearningDays: number;
    currentStreakStartDate?: string;
    currentStreakDays?: number;
    totalXp: number;

    nativeLanguage?: Language;
    languageData?: Partial<Record<Language, LanguageProfileData>>;
}

export interface LanguageProfileData {
    startDate: string;
    lastActiveDate: string;
    totalLearningDays: number;
    totalXp: number;
    
    currentStatedLevel?: DifficultyLevel;
    openToChatInLang?: boolean;
    vocabularySize?: number;
    readArticleIds?: number[];
}
