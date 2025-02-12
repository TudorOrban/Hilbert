export interface ChatMessageSearchDto {
    id: number;
    userId: number;
    chatId: number;
    content: string;
    createdAt: string;
    updatedAt: string;
}

export interface CreateChatMessageDto {
    userId: number;
    chatId: number;
    content: string;
}