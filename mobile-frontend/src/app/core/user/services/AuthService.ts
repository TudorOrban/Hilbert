import axios from "axios";
import { LoginResponseDto, UserDataDto } from "../models/User";

const API_URL = 'http://172.17.0.1:8080/api/v1/auth';

export const AuthService = {
    login: async (username: string, password: string): Promise<LoginResponseDto> => {
        const response = await axios.post<LoginResponseDto>(`${API_URL}/login`, {
            username, password
        });
        return response.data;
    },
    getUserFromToken: async (token: string): Promise<UserDataDto> => {
        const response = await axios.post<UserDataDto>(`${API_URL}/get-user-from-token`, token);
        return response.data;
    }
}