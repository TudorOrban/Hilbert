import { Language } from "../../../shared/language/models/Language";

export interface BotChatMessageSearchDto {
    id: number;
    isUser: boolean;
    chatId: number;
    content: string;
    createdAt: string;
    updatedAt: string;
}

export interface CreateBotChatMessageDto {
    userId: number;
    isUser: boolean;
    botChatId: number;
    content: string;
    language: Language;
}