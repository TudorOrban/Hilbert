import { DifficultyLevel, Language } from "../../../features/articles/models/Article";

export interface SearchParams {
    searchQuery?: string;
    sortBy?: string;
    isAscending?: boolean;
    page?: number;
    itemsPerPage?: number;
}

export interface ArticleSearchParams extends SearchParams {
    userId?: number;
    language?: Language;
    level?: DifficultyLevel;
}

export interface PaginatedResults<T> {
    results: T[];
    totalCount: number;
}
