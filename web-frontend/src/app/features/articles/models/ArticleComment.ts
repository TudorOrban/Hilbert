export interface ArticleCommentDto {
    id: number;
    articleId: number;
    userId: number;
    username?: string;
    content: string;
    createdAt?: string;
}

export interface CreateCommentDto {
    articleId: number;
    userId: number;
    content: string;
}