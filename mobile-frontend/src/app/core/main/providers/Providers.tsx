
import { ReactNode } from "react";
import { CurrentUserProvider } from "../../user/contexts/CurrentUserContext";
import { TailwindProvider } from "tailwind-rn";
import utilities from "../../../../../tailwind.json";

export const Providers = ({ children }: { children: ReactNode }) => {

    return (
        <TailwindProvider utilities={utilities}>
            <CurrentUserProvider>
                {children}
            </CurrentUserProvider>
        </TailwindProvider>
    );
}