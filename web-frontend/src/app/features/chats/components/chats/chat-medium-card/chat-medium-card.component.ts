import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ChatSearchDto } from '../../../models/Chat';
import { faCheck } from '@fortawesome/free-solid-svg-icons';
import { UiUtilService } from '../../../../../shared/common/services/ui-util.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-chat-medium-card',
  imports: [CommonModule, FontAwesomeModule],
  templateUrl: './chat-medium-card.component.html',
  styleUrl: './chat-medium-card.component.css'
})
export class ChatMediumCardComponent {
    @Input() chat?: ChatSearchDto;
    @Input() userId?: number;

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
