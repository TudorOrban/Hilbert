import axios from "axios";
import { LoginResponseDto, UserDataDto } from "../models/User";
import axiosInstance from "../../main/config/AxiosInstance";

const RESOURCE_PATH = '/auth';

export const AuthService = {
    login: async (username: string, password: string): Promise<LoginResponseDto> => {
        const response = await axiosInstance.post<LoginResponseDto>(`${RESOURCE_PATH}/login`, {
            username, password
        });
        return response.data;
    },
    getUserFromToken: async (token: string): Promise<UserDataDto> => {
        const response = await axiosInstance.post<UserDataDto>(`${RESOURCE_PATH}/get-user-from-token`, token);
        return response.data;
    }
}