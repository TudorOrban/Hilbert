import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgSelectModule } from '@ng-select/ng-select';
import { Language } from '../../../../../shared/language/models/Language';
import { DifficultyLevel } from '../../../models/Article';
import { LanguageOptionsService } from '../../../../../shared/language/services/language-options.service';
import { LanguageSelectorComponent } from '../../../../../shared/language/components/language-selector/language-selector.component';

@Component({
  selector: 'app-advanced-search-panel',
  imports: [CommonModule, FormsModule, NgSelectModule, LanguageSelectorComponent],
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

    levelOptions = [
        { value: DifficultyLevel.A1, viewValue: 'A1 - Beginner' },
        { value: DifficultyLevel.A2, viewValue: 'A2 - Elementary' },
        { value: DifficultyLevel.B1, viewValue: 'B1 - Intermediate' },
        { value: DifficultyLevel.B2, viewValue: 'B2 - Upper Intermediate' },
        { value: DifficultyLevel.C1, viewValue: 'C1 - Advanced' },
        { value: DifficultyLevel.C2, viewValue: 'C2 - Proficient' }
    ];
}
