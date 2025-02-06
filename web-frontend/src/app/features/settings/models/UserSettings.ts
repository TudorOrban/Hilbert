
export interface UserSettingsDto {
    id: number;
    userId: number;
    settings: SettingsData;
}

export interface SettingsData {
    notificationsOn?: boolean;
    isProfilePublic?: boolean;
}