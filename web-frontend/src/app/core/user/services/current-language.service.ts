import { Injectable } from "@angular/core";
import { Language } from "../../../shared/language/models/Language";

@Injectable({
    providedIn: 'root'
})
export class CurrentLanguageService {
    private language: Language = Language.ENGLISH;


    getLanguage(): Language {
        return this.language;
    }

    setLanguage(language: Language): void {
        this.language = language;
    }
}