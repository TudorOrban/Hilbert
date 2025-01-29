import { UserSmallDto } from "../../../core/user/models/User";
import { PaginatedResults } from "../../../shared/search/models/Search";
import { MessageSearchDto } from "./ChatMessage";


export interface ChatFullDto {
    id: number;
    firstUserId: number;
    secondUserId: number;
    createdAt: string;
    updatedAt: string;
    lastMessageUserId: number;
    lastMessageContent: number;
    messages?: PaginatedResults<MessageSearchDto>;
}

export interface ChatSearchDto {
    id: number;
    firstUserId: number;
    secondUserId: number;
    createdAt: string;
    updatedAt: string;
    lastMessageUserId: number;
    lastMessageContent: number;

    firstUser?: UserSmallDto;
    secondUser?: UserSmallDto;
}

export interface CreateChatDto {
    firstUserId: number;
    secondUserId: number;
    creatorUserId: number;
    messageContent: string;
}