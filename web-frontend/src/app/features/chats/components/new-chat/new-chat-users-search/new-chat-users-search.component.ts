import { Component } from '@angular/core';
import { SearchInputComponent } from "../../../../../shared/common/components/search-input/search-input.component";
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faArrowUpWideShort } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-new-chat-users-search',
  imports: [FontAwesomeModule, SearchInputComponent],
  templateUrl: './new-chat-users-search.component.html',
  styleUrl: './new-chat-users-search.component.css'
})
export class NewChatUsersSearchComponent {


    faArrowUpWideShort = faArrowUpWideShort;
}
