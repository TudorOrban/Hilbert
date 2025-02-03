import { Component, Input } from '@angular/core';
import { PaginatedResults } from '../../../../../shared/search/models/Search';
import { UserSearchDto, UserSmallDto } from '../../../../../core/user/models/User';
import { faSpinner } from '@fortawesome/free-solid-svg-icons';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NewChatUserCardComponent } from "../new-chat-user-card/new-chat-user-card.component";

@Component({
  selector: 'app-new-chat-users-list',
  imports: [CommonModule, FontAwesomeModule, NewChatUserCardComponent],
  templateUrl: './new-chat-users-list.component.html',
  styleUrl: './new-chat-users-list.component.css'
})
export class NewChatUsersListComponent {
    @Input() userId?: number;
    @Input() users?: PaginatedResults<UserSearchDto>;
    @Input() isLoading?: boolean = false;

    faSpinner = faSpinner;

}
