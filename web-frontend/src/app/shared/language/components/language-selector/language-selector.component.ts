import { Component, EventEmitter, Output } from "@angular/core";
import { Language, LanguageUIItem } from "../../../language/models/Language";
import { LanguageOptionsService } from "../../../language/services/language-options.service";
import { CommonModule } from "@angular/common";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { faCaretDown } from "@fortawesome/free-solid-svg-icons";

@Component({
    selector: "app-language-selector",
    imports: [CommonModule, FontAwesomeModule],
    templateUrl: "./language-selector.component.html",
    styleUrl: "./language-selector.component.css",
})
export class LanguageSelectorComponent {
    languageOptions: LanguageUIItem[] = [];
    selectedLanguage: Language = Language.FRENCH;
    isDropdownOpen = false;

    @Output() selectedLanguageChange = new EventEmitter<Language>();

    constructor(private languageService: LanguageOptionsService) {
        this.languageOptions = this.languageService.getLanguageOptions();
        this.selectedLanguage = this.languageService.getSelectedLanguage();
    }

    toggleDropdown() {
        this.isDropdownOpen = !this.isDropdownOpen;
    }

    selectLanguage(language: Language) {
        this.languageService.setSelectedLanguage(language);
        this.selectedLanguage = language;
        this.isDropdownOpen = false;
        this.selectedLanguageChange.emit(language);
    }

    getFlagImage(language: Language): string {
        if (language === Language.NONE) return "";
        const flagCode = this.languageService.getFlagCode(language);
        return `assets/flags/${flagCode}.svg`;
    }

    getLanguageName(language: Language): string {
        const option = this.languageOptions.find((o) => o.value === language);
        return option ? option.viewValue : "None";
    }

    Language = Language;
    faCaretDown = faCaretDown;
}
