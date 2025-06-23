import AsyncStorage from "@react-native-async-storage/async-storage";
import { UserDataDto } from "../models/User";
import { AuthService } from "./AuthService";

export const AuthManagerService = {
    login: async (username: string, password: string): Promise<void> => {
        try {
            const loginResponse = await AuthService.login(username, password);
            await AsyncStorage.setItem("token", loginResponse.accessToken);
        } catch (error) {
            console.error("Error logging in: ", error);
            throw error;
        }
    },
    logout: async (): Promise<void> => {
        try {
            await AsyncStorage.removeItem("token");
        } catch (error) {
            console.error("Error logging out: ", error);
            throw error;
        }
    },
    getUserFromToken: async (): Promise<UserDataDto | null> => {
        try {
            const token = await AsyncStorage.getItem("token");
            if (!token) {
                return null;
            }

            return await AuthService.getUserFromToken(token);
        } catch (error) {
            console.error("Error fetching current user: ", error);
            throw error;
        }
    },
    getUsernameFromToken: async (): Promise<string | undefined> => {
        try {
            const token = await AsyncStorage.getItem("token");
            if (!token) {
                return undefined;
            }

            return await AuthService.getUsernameFromToken(token);
        } catch(error) {
            console.error("Error fetching current user: ", error);
            throw error;
        }
    }
}