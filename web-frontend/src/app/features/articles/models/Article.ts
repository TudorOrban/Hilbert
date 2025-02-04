import { Language } from "../../../shared/language/models/Language";

export interface ArticleFullDto {
    id: number;
    userId: number;
    title: string;
    description?: string;
    content: string;
    translatedContent: TranslatedContent;
    language: Language;
    level: DifficultyLevel;
    status: ArticleStatus;
    wordCount?: number;
    createdAt: string;
    updatedAt: string;
    averageRating?: number;
    numberOfRatings?: number;
    readCount?: number;
    bookmarkCount?: number;
}

export interface ArticleSearchDto {
    id: number;
    userId: number;
    title: string;
    description?: string;
    language: Language;
    level: DifficultyLevel;
    status: ArticleStatus;
    wordCount?: number;
    createdAt: string;
    averageRating?: number;
    numberOfRatings?: number;
    readCount?: number;
    bookmarkCount?: number;
}

export interface CreateArticleDto {
    userId: number;
    title: string;
    description?: string;
    content: string;
    language: Language;
    level: DifficultyLevel;
    status: ArticleStatus;
}

export interface UpdateArticleDto {
    id: number;
    userId: number;
    title: string;
    description?: string;
    content: string;
    language: Language;
    level: DifficultyLevel;
    status: ArticleStatus;
}

export interface ReadArticleSummaryDto {
    newWords: Record<string, number>;
}

export enum DifficultyLevel {
    NONE = "None",
    A1 = "A1",
    A2 = "A2",
    B1 = "B1",
    B2 = "B2",
    C1 = "C1",
    C2 = "C2"
};

export enum ArticleStatus {
    PUBLIC = "PUBLIC",
    PRIVATE = "PRIVATE",
    DRAFT = "DRAFT"
};

export interface TranslatedContent {
    translationMap: Record<string, string[]>;
    srcLanguage: Language;
    destLanguage: Language;
}