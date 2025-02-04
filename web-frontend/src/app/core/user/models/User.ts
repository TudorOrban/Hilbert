import { LearningProfileDto } from "../../../features/profile/models/LearningProfile";

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

    profileDto: LearningProfileDto;
}

export interface UserSearchDto {
    id: number;
    username: string;
    email: string;
    createdAt: string;
    isOnline?: boolean;
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
