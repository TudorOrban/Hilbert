
import { ReactNode } from "react";
import { CurrentUserProvider } from "../../user/contexts/CurrentUserContext";

export const Providers = ({ children }: { children: ReactNode }) => {

    return (
        <CurrentUserProvider>
            {children}
        </CurrentUserProvider>
    );
}