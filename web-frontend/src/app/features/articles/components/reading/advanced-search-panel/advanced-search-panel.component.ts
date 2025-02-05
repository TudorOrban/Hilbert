import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgSelectModule } from '@ng-select/ng-select';
import { Language } from '../../../../../shared/language/models/Language';
import { DifficultyLevel } from '../../../models/Article';
import { LanguageOptionsService } from '../../../../../shared/language/services/language-options.service';
import { LanguageSelectorComponent } from '../../../../../shared/language/components/language-selector/language-selector.component';
import { EnumSelectorComponent } from "../../../../../shared/common/components/enum-selector/enum-selector.component";

@Component({
  selector: 'app-advanced-search-panel',
  imports: [CommonModule, FormsModule, NgSelectModule, LanguageSelectorComponent, EnumSelectorComponent],
  templateUrl: './advanced-search-panel.component.html',
  styleUrl: './advanced-search-panel.component.css'
})
export class AdvancedSearchPanelComponent {
    Language = Language;
    Level = DifficultyLevel;

    selectedUserId?: number;
    selectedLanguage: Language = Language.ENGLISH;
    selectedLevel?: DifficultyLevel = DifficultyLevel.NONE;
    
    languageService: LanguageOptionsService;

    constructor(
        languageService: LanguageOptionsService
    ) {
        this.languageService = languageService;
    }

    onSelectedLevelChange(level?: DifficultyLevel): void {
        this.selectedLevel = level;
    }

    DifficultyLevel = DifficultyLevel;
}
