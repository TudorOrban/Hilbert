import { Component, EventEmitter, Output } from "@angular/core";
import { Language } from "../../models/Language";
import { DifficultyLevel } from "../../../../features/articles/models/Article";
import { LanguageLevels } from "../../../search/models/Search";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { CommonModule } from "@angular/common";
import { LanguageSelectorComponent } from "../language-selector/language-selector.component";
import { EnumSelectorComponent } from "../../../common/components/enum-selector/enum-selector.component";

@Component({
    selector: "app-language-levels-selector",
    imports: [
        CommonModule,
        FontAwesomeModule,
        LanguageSelectorComponent,
        EnumSelectorComponent,
    ],
    templateUrl: "./language-levels-selector.component.html",
    styleUrl: "./language-levels-selector.component.css",
})
export class LanguageLevelsSelectorComponent {
    languageLevels: LanguageLevels = { ENGLISH: DifficultyLevel.B1 };
    temporaryLanguage?: Language;
    languageLevelEntries: [Language, DifficultyLevel][] = [];

    @Output() onAddLevel = new EventEmitter<LanguageLevels>();

    addLanguage(language: Language) {
        this.temporaryLanguage = language;
    }

    addLevel(language: Language, level: DifficultyLevel) {
        if (language != this.temporaryLanguage) {
            return;
        }

        this.languageLevels[language] = level;
        this.onAddLevel.emit(this.languageLevels);
    }

    onSelectedLanguageChange(language: Language) {}

    onDifficultyLevelChange(
        language: Language,
        level: string | DifficultyLevel
    ): void {
        const difficultyLevel = this.parseDifficultyLevel(level);
        if (difficultyLevel !== undefined) {
            this.languageLevels[language] = difficultyLevel;
            this.updateLanguageLevelEntries();
        }
    }

    updateLanguageLevelEntries(): void {
        this.languageLevelEntries = Object.entries(this.languageLevels).map(
            ([key, value]) => [key as Language, value]
        );
    }

    // Util
    private parseDifficultyLevel(
        value: string | DifficultyLevel
    ): DifficultyLevel | undefined {
        if (typeof value === "string") {
            const parsedValue = value as unknown as DifficultyLevel;
            if (Object.values(DifficultyLevel).includes(parsedValue)) {
                return parsedValue;
            }
        } else if (Object.values(DifficultyLevel).includes(value)) {
            return value;
        }
        return undefined;
    }

    Object = Object;
    Language = Language;
    DifficultyLevel = DifficultyLevel;
    faPlus = faPlus;
}
