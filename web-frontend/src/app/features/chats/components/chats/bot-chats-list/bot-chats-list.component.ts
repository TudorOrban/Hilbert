import { Component, Input } from '@angular/core';
import { BotChatMediumCardComponent } from "../bot-chat-medium-card/bot-chat-medium-card.component";
import { CommonModule } from '@angular/common';
import { PaginatedResults } from '../../../../../shared/search/models/Search';
import { BotChatSearchDto } from '../../../models/BotChat';
import { faSpinner } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-bot-chats-list',
  imports: [CommonModule, FontAwesomeModule, BotChatMediumCardComponent],
  templateUrl: './bot-chats-list.component.html',
  styleUrl: './bot-chats-list.component.css'
})
export class BotChatsListComponent {
    @Input() botChats?: PaginatedResults<BotChatSearchDto>;
    @Input() isLoading?: boolean = false;

    faSpinner = faSpinner;
}
