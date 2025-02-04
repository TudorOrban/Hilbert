import { UserSmallDto } from "../../../core/user/models/User";
import { Language } from "../../../shared/language/models/Language";
import { PaginatedResults } from "../../../shared/search/models/Search";
import { DifficultyLevel } from "../../articles/models/Article";
import { BotChatMessageSearchDto } from "./BotChatMessage";


export interface BotChatFullDto {
    id: number;
    userId: number;
    language: Language;
    createdAt: string;
    updatedAt: string;
    lastMessageIsUser: number;
    lastMessageContent: number;
    lastMessageDate?: string;
    lastMessageSeen?: string;
    messages?: PaginatedResults<BotChatMessageSearchDto>;

    user?: UserSmallDto;
}

export interface BotChatSearchDto {
    id: number;
    userId: number;
    language: Language;
    createdAt: string;
    updatedAt: string;
    lastMessageIsUser: number;
    lastMessageContent: number;
    lastMessageDate?: string;
    lastMessageSeen?: string;

    user?: UserSmallDto;
}

export interface CreateBotChatDto {
    userId: number;
    language: Language;
    level?: DifficultyLevel;
    messageContent: string;
}