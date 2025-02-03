import { Component, EventEmitter, Output } from '@angular/core';
import { LanguageLevelsSelectorComponent } from "../../../../../shared/language/components/language-levels-selector/language-levels-selector.component";
import { LanguageLevels } from '../../../../../shared/search/models/Search';

@Component({
  selector: 'app-new-chat-users-search-panel',
  imports: [LanguageLevelsSelectorComponent],
  templateUrl: './new-chat-users-search-panel.component.html',
  styleUrl: './new-chat-users-search-panel.component.css'
})
export class NewChatUsersSearchPanelComponent {

    @Output() selectedLanguageLevelsChange = new EventEmitter<LanguageLevels>();

    onSelectedLanguageLevelsChange(languageLevels: LanguageLevels) {
        this.selectedLanguageLevelsChange.emit(languageLevels);
    }
}
