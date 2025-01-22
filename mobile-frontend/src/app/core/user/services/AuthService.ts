import axios from "axios";

const API_URL = 'http://172.17.0.1:8080/api/v1/auth';

export const AuthService = {
    login: async (username: string, password: string) => {
        try {
            const response = await axios.post(`${API_URL}/login`, {
                username, password
            });
            return response.data;
        } catch (error) {
            throw error;
        }
    }
}