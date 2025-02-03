import { Component } from '@angular/core';
import { SearchInputComponent } from "../../../../../shared/common/components/search-input/search-input.component";
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faArrowUpWideShort, faMessage } from '@fortawesome/free-solid-svg-icons';
import { Router } from '@angular/router';

@Component({
  selector: 'app-chats-header',
  imports: [CommonModule, FontAwesomeModule, SearchInputComponent],
  templateUrl: './chats-header.component.html',
  styleUrl: './chats-header.component.css'
})
export class ChatsHeaderComponent {

    constructor(
        private router: Router
    ) {}

    faArrowUpWideShort = faArrowUpWideShort;
    faMessage = faMessage;

    handleNewChat(): void {
        this.router.navigate(["chat/new-chat"]);
    }
}
