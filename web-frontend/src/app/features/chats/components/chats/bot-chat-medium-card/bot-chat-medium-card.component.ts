import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faCheck } from '@fortawesome/free-solid-svg-icons';
import { UiUtilService } from '../../../../../shared/common/services/ui-util.service';
import { Router } from '@angular/router';
import { LanguageOptionsService } from '../../../../../shared/language/services/language-options.service';
import { Language } from '../../../../../shared/language/models/Language';
import { BotChatSearchDto } from '../../../models/BotChat';

@Component({
  selector: 'app-bot-chat-medium-card',
  imports: [CommonModule, FontAwesomeModule],
  templateUrl: './bot-chat-medium-card.component.html',
  styleUrl: './bot-chat-medium-card.component.css'
})
export class BotChatMediumCardComponent {
    @Input() chat?: BotChatSearchDto;

    constructor(
        private readonly languageService: LanguageOptionsService,
        private readonly uiUtilService: UiUtilService,
        private readonly router: Router
    ) {}

    navigateToChat(chatId?: number): void {
        if (!chatId) {
            console.error("Chat ID unavailable: ", chatId);
            return;
        }

        this.router.navigate([`/bot-chat/${chatId}`]);
    }


    getLanguageFlagCode(language?: Language): string | undefined {
        return this.languageService.getFlagCode(language);
    }
    
    formatDate(dateString?: string): string {
        return this.uiUtilService.formatDate(dateString);
    }

    faCheck = faCheck;
}
