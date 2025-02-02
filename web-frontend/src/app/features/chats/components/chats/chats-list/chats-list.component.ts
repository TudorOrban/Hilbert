import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faCheck } from '@fortawesome/free-solid-svg-icons';
import { PaginatedResults } from '../../../../../shared/search/models/Search';
import { ChatSearchDto } from '../../../models/Chat';
import { UiUtilService } from '../../../../../shared/common/services/ui-util.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-chats-list',
  imports: [CommonModule, FontAwesomeModule],
  templateUrl: './chats-list.component.html',
  styleUrl: './chats-list.component.css'
})
export class ChatsListComponent {
    @Input() userId?: number;
    @Input() chats?: PaginatedResults<ChatSearchDto>;

    constructor(
        private readonly uiUtilService: UiUtilService,
        private readonly router: Router
    ) {}




    navigateToChat(chatId?: number): void {
        if (!chatId) {
            console.error("Chat ID unavailable: ", chatId);
            return;
        }

        this.router.navigate([`/chat/${chatId}`]);
    }

    formatDate(dateString?: string): string {
        return this.uiUtilService.formatDate(dateString);
    }

    faCheck = faCheck;
}
