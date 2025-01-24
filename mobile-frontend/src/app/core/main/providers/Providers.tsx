
import { ReactNode } from "react";
import { CurrentUserProvider } from "../../user/contexts/CurrentUserContext";
import { TailwindProvider } from "tailwind-rn";
import utilities from "../../../../../tailwind.json";


export const Providers = ({ children }: { children: ReactNode }) => {
    const TailwindProviderWithChildren = TailwindProvider as any; // Use as any, as plain TailwindProvider complains it doesn't have a children prop (even though setup works fine)

    return (
        <TailwindProviderWithChildren utilities={utilities}>
            <CurrentUserProvider>
                {children}
            </CurrentUserProvider>
        </TailwindProviderWithChildren>
    );
}