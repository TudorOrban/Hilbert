import { Component } from '@angular/core';
import { SearchInputComponent } from "../../../../../shared/common/components/search-input/search-input.component";
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faArrowUpWideShort, faMessage } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-chats-header',
  imports: [CommonModule, FontAwesomeModule, SearchInputComponent],
  templateUrl: './chats-header.component.html',
  styleUrl: './chats-header.component.css'
})
export class ChatsHeaderComponent {

    faArrowUpWideShort = faArrowUpWideShort;
    faMessage = faMessage;

    handleAddChat(): void {

    }
}
