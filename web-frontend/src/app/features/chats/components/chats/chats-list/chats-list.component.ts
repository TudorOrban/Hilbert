import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faCheck, faSpinner } from '@fortawesome/free-solid-svg-icons';
import { PaginatedResults } from '../../../../../shared/search/models/Search';
import { ChatSearchDto } from '../../../models/Chat';
import { UiUtilService } from '../../../../../shared/common/services/ui-util.service';
import { Router } from '@angular/router';
import { ChatMediumCardComponent } from "../chat-medium-card/chat-medium-card.component";

@Component({
  selector: 'app-chats-list',
  imports: [CommonModule, FontAwesomeModule, ChatMediumCardComponent],
  templateUrl: './chats-list.component.html',
  styleUrl: './chats-list.component.css'
})
export class ChatsListComponent {
    @Input() userId?: number;
    @Input() chats?: PaginatedResults<ChatSearchDto>;
    @Input() isLoading?: boolean = false;

    faSpinner = faSpinner;
}
