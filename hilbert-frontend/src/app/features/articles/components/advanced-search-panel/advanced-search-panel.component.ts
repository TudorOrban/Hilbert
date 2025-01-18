import { Component } from '@angular/core';
import { DifficultyLevel, Language } from '../../models/Article';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-advanced-search-panel',
  imports: [CommonModule, FormsModule],
  templateUrl: './advanced-search-panel.component.html',
  styleUrl: './advanced-search-panel.component.css'
})
export class AdvancedSearchPanelComponent {
    Language = Language;
    Level = DifficultyLevel;

    selectedUserId?: number;
    selectedLanguage: Language = Language.NONE;
    selectedLevel: DifficultyLevel = DifficultyLevel.NONE;

    languageOptions = [
        { value: Language.NONE, viewValue: 'None' },
        { value: Language.ENGLISH, viewValue: 'English' },
        { value: Language.SPANISH, viewValue: 'Spanish' },
        { value: Language.FRENCH, viewValue: 'French' },
        { value: Language.GERMAN, viewValue: 'German' },
        { value: Language.PORTUGUESE, viewValue: 'Portuguese' },
        { value: Language.ITALIAN, viewValue: 'Italian' },
        { value: Language.JAPANESE, viewValue: 'Japanese' },
        { value: Language.CHINESE, viewValue: 'Chinese' },
        { value: Language.RUSSIAN, viewValue: 'Russian' }
    ];

    levelOptions = [
        { value: DifficultyLevel.A1, viewValue: 'A1 - Beginner' },
        { value: DifficultyLevel.A2, viewValue: 'A2 - Elementary' },
        { value: DifficultyLevel.B1, viewValue: 'B1 - Intermediate' },
        { value: DifficultyLevel.B2, viewValue: 'B2 - Upper Intermediate' },
        { value: DifficultyLevel.C1, viewValue: 'C1 - Advanced' },
        { value: DifficultyLevel.C2, viewValue: 'C2 - Proficient' }
    ];
}
