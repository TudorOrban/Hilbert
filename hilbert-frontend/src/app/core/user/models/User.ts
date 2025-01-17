export interface UserDataDto {
    id: number;
    username: string;
    email: string;
    passwordHash: string;
    createdAt: string;
    updatedAt: string;
    lastLogin?: string;
    isActive?: boolean;
    role?: string;
}

export interface CreateUserDto {
    username: string;
    email: string;
    passwordHash: string;
}

export interface UpdateUserDto {
    id: number;
    username: string;
    email: string;
}
