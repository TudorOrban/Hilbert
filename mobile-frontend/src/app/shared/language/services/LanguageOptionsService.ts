import { Language, LanguageUIItem } from "../models/Language"

const languageOptions: LanguageUIItem[] = [
    { value: Language.NONE, viewValue: 'None', flagCode: '' },
    { value: Language.ENGLISH, viewValue: 'English', flagCode: 'gb' },
    { value: Language.SPANISH, viewValue: 'Spanish', flagCode: 'es' },
    { value: Language.FRENCH, viewValue: 'French', flagCode: 'fr' },
    { value: Language.GERMAN, viewValue: 'German', flagCode: 'de' },
    { value: Language.PORTUGUESE, viewValue: 'Portuguese', flagCode: 'pt' },
    { value: Language.ITALIAN, viewValue: 'Italian', flagCode: 'it' },
    { value: Language.JAPANESE, viewValue: 'Japanese', flagCode: 'jp' },
    { value: Language.CHINESE, viewValue: 'Chinese', flagCode: 'cn' },
    { value: Language.RUSSIAN, viewValue: 'Russian', flagCode: 'ru' },
];

export const LanguageOptionsService = {
    getLanguageOptions(): LanguageUIItem[] {
        return languageOptions;
    },

    getFlagCode(language: string): string | undefined {
        return languageOptions.find(o => o.value === language)?.flagCode;
    }
}