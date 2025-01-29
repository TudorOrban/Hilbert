export interface MessageSearchDto {
    id: number;
    userId: number;
    chatId: number;
    content: string;
    createdAt: string;
    updatedAt: string;
}

export interface CreateMessageDto {
    userId: number;
    chatId: number;
    content: string;
}