import { Component, EventEmitter, Output } from '@angular/core';
import { Language } from '../../models/Language';
import { DifficultyLevel } from '../../../../features/articles/models/Article';
import { LanguageLevels } from '../../../search/models/Search';

@Component({
  selector: 'app-language-levels-selector',
  imports: [],
  templateUrl: './language-levels-selector.component.html',
  styleUrl: './language-levels-selector.component.css'
})
export class LanguageLevelsSelectorComponent {
    languageLevels: LanguageLevels = {};
    temporaryLanguage?: Language;

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

}
