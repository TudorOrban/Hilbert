import { Injectable } from "@angular/core";
import { Language, LanguageUIItem } from "../models/Language";


@Injectable({
    providedIn: 'root'
})
export class LanguageOptionsService {
    selectedLanguage: Language = Language.NONE;
    
    languageOptions: LanguageUIItem[] = [
        { value: Language.NONE, viewValue: 'None', flagCode: '' },
        { value: Language.ENGLISH, viewValue: 'English', flagCode: 'gb' },
        { value: Language.SPANISH, viewValue: 'Spanish', flagCode: 'es' },
        { value: Language.FRENCH, viewValue: 'French', flagCode: 'fr' },
        { value: Language.GERMAN, viewValue: 'German', flagCode: 'de' },
        { value: Language.PORTUGUESE, viewValue: 'Portuguese', flagCode: 'pt' },
        { value: Language.ITALIAN, viewValue: 'Italian', flagCode: 'it' },
        { value: Language.JAPANESE, viewValue: 'Japanese', flagCode: 'jp' },
        { value: Language.CHINESE, viewValue: 'Chinese', flagCode: 'cn' },
        { value: Language.RUSSIAN, viewValue: 'Russian', flagCode: 'ru' }
    ];

    getLanguageOptions(): LanguageUIItem[] {
        return this.languageOptions;
    }

    getFlagCode(language?: Language): string | undefined {
        return this.languageOptions.find(o => o.value == language)?.flagCode;
    }

    getSelectedLanguage(): Language {
        return this.selectedLanguage;
    }

    setSelectedLanguage(language: Language): void {
        this.selectedLanguage = language;
    }
}