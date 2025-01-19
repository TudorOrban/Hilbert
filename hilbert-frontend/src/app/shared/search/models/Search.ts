import { DifficultyLevel } from "../../../features/articles/models/Article";
import { Language } from "../../language/models/Language";

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
