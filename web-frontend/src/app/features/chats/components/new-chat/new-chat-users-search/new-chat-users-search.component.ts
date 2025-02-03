import { Component, EventEmitter, Output } from '@angular/core';
import { SearchInputComponent } from "../../../../../shared/common/components/search-input/search-input.component";
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faArrowUpWideShort } from '@fortawesome/free-solid-svg-icons';
import { LanguageLevelsSelectorComponent } from "../../../../../shared/language/components/language-levels-selector/language-levels-selector.component";
import { LanguageLevels } from '../../../../../shared/search/models/Search';

@Component({
  selector: 'app-new-chat-users-search',
  imports: [FontAwesomeModule, SearchInputComponent, LanguageLevelsSelectorComponent],
  templateUrl: './new-chat-users-search.component.html',
  styleUrl: './new-chat-users-search.component.css'
})
export class NewChatUsersSearchComponent {

    @Output() selectedLanguageLevelsChange = new EventEmitter<LanguageLevels>();

    onSelectedLanguageLevelsChange(languageLevels: LanguageLevels) {
        this.selectedLanguageLevelsChange.emit(languageLevels);
    }

    faArrowUpWideShort = faArrowUpWideShort;
}
