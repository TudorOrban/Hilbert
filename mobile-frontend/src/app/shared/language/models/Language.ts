export enum Language {
    NONE = "NONE",
    ENGLISH = "ENGLISH",
    SPANISH = "SPANISH",
    FRENCH = "FRENCH",
    GERMAN = "GERMAN",
    PORTUGUESE = "PORTUGUESE",
    ITALIAN = "ITALIAN",
    JAPANESE = "JAPANESE",
    CHINESE = "CHINESE",
    RUSSIAN = "RUSSIAN"
}

export interface LanguageUIItem {
    value: Language,
    viewValue: string,
    flagCode: string // for flag icons
}