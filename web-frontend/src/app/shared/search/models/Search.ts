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

export interface CommentSearchParams extends SearchParams {
    articleId?: number;
}

export interface ChatSearchParams extends SearchParams {
    userId?: number;
}

export interface ChatMessageSearchParams extends SearchParams {
    chatId?: number;
}

export interface BotChatSearchParams extends SearchParams {
    userId?: number;
    language?: Language;
}

export interface BotChatMessageSearchParams extends SearchParams {
    chatId?: number;
}

export interface PaginatedResults<T> {
    results: T[];
    totalCount: number;
}
