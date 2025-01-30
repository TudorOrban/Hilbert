export interface BotChatMessageSearchDto {
    id: number;
    userId: number;
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