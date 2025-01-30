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
    chatId: number;
    content: string;
}