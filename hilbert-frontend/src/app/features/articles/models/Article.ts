import { Language } from "../../../shared/language/models/Language";

export interface ArticleFullDto {
    id: number;
    userId: number;
    title: string;
    description?: string;
    content: string;
    language: Language;
    level: DifficultyLevel;
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
}

export interface UpdateArticleDto {
    id: number;
    userId: number;
    title: string;
    description?: string;
    content: string;
    language: Language;
    level: DifficultyLevel;
}

export enum DifficultyLevel {
    NONE,
    A1,
    A2,
    B1,
    B2,
    C1,
    C2
};
