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

export interface UserSmallDto {
    id: number;
    username: string;
    email: string;
    avatarUrl?: string;
}

export interface LoginDto {
    username: string;
    password: string;
}

export interface LoginResponseDto {
    accessToken: string;
    tokenType: string;
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
