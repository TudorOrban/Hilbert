import { createContext, ReactNode, useContext, useEffect, useState } from "react";
import { UserDataDto } from "../models/User";
import { AuthManagerService } from "../services/AuthManagerService";

interface CurrentUserContextType {
    currentUser: UserDataDto | null;
    loading: boolean;
    login: (username: string, password: string) => Promise<void>;
    logout: () => Promise<void>;
}

const CurrentUserContext = createContext<CurrentUserContextType | undefined>(undefined);

export const CurrentUserProvider = ({ children }: { children: ReactNode }) => {
    const [currentUser, setCurrentUser] = useState<UserDataDto | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchAndSetUser();
    }, []);

    const fetchAndSetUser = async () => {
        try {
            const user = await AuthManagerService.getUserFromToken();
            setCurrentUser(user);
        } catch (error) {
            console.error("Error fetching current user: ", error);
        } finally {
            setLoading(false);
        }
    }

    const login = async (username: string, password: string) => {
        try {
            await AuthManagerService.login(username, password);
            fetchAndSetUser();
        } catch (error) {
            console.error("Error logging in: ", error);
            throw error;
        }
    }

    const logout = async () => {
        try {
            await AuthManagerService.logout();
            setCurrentUser(null);
        } catch (error) {
            console.error("Error logging out: ", error);
            throw error;
        }
    }

    return (
        <CurrentUserContext.Provider value={{ currentUser, loading, login, logout }}>
            {children}
        </CurrentUserContext.Provider>
    )
}

export const useCurrentUser = (): CurrentUserContextType => {
    const context = useContext(CurrentUserContext);
    if (!context) {
        throw new Error("Current User context used out of Provider scope");
    }

    return context;
}