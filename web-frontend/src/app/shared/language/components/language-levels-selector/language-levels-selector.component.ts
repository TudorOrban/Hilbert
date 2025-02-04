import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import { Language } from "../../models/Language";
import { DifficultyLevel } from "../../../../features/articles/models/Article";
import { LanguageLevels } from "../../../search/models/Search";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { faCheck, faPlus } from "@fortawesome/free-solid-svg-icons";
import { CommonModule } from "@angular/common";
import { LanguageSelectorComponent } from "../language-selector/language-selector.component";
import { EnumSelectorComponent } from "../../../common/components/enum-selector/enum-selector.component";
import { Pair } from "../../../common/types/common";

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
export class LanguageLevelsSelectorComponent implements OnInit {
    languageLevels: LanguageLevels = {};
    languageLevelEntries: [Language, DifficultyLevel][] = [];
    temporaryLanguage: Language = Language.NONE;
    temporaryLevel: DifficultyLevel = DifficultyLevel.NONE;

    @Output() onAddLevel = new EventEmitter<LanguageLevels>();

    ngOnInit(): void {
        this.updateLanguageLevelEntries();
    }

    addLanguage(language: Language) {
        this.temporaryLanguage = language;
    }

    onDifficultyLevelChange(level?: string): void {
        const difficultyLevel = this.parseDifficultyLevel(level);
        if (!difficultyLevel) {
            return;
        }
        this.temporaryLevel = difficultyLevel;
    }

    onSelectedLanguageChange(language: Language) {
        this.temporaryLanguage = language;
    }

    confirmLanguageLevel(): void {
        this.languageLevels[this.temporaryLanguage] = this.temporaryLevel;
        this.updateLanguageLevelEntries();
    }

    // Utils
    updateLanguageLevelEntries(): void {
        this.languageLevelEntries = Object.entries(this.languageLevels).map(
            ([key, value]) => [key as Language, value]
        );
    }

    private parseDifficultyLevel(value?: string): DifficultyLevel | undefined {
        if (!value) {
            return;
        }
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
    faCheck = faCheck;
}
