import { Component, EventEmitter, Output } from '@angular/core';
import { SearchInputComponent } from "../../../../../shared/common/components/search-input/search-input.component";
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faArrowUpWideShort } from '@fortawesome/free-solid-svg-icons';
import { LanguageSelectorComponent } from "../../../../../shared/common/components/language-selector/language-selector.component";
import { Language } from '../../../../../shared/language/models/Language';

@Component({
  selector: 'app-new-chat-users-search',
  imports: [FontAwesomeModule, SearchInputComponent, LanguageSelectorComponent],
  templateUrl: './new-chat-users-search.component.html',
  styleUrl: './new-chat-users-search.component.css'
})
export class NewChatUsersSearchComponent {


    @Output() selectedLanguageChange = new EventEmitter<Language>();

    onSelectedLanguageChange(language: Language) {
        this.selectedLanguageChange.emit(language);
    }

    faArrowUpWideShort = faArrowUpWideShort;
}
