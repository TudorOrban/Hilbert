
export interface UserSettingsDto {
    id: number;
    userId: number;
    settings: UserSettingsData;
}

export interface UserSettingsData {
    notificationsOn?: boolean;
    profilePublic?: boolean;
    darkMode?: boolean;
}