import { Component, Input } from '@angular/core';
import { UserSearchDto, UserSmallDto } from '../../../../../core/user/models/User';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-new-chat-user-card',
  imports: [CommonModule],
  templateUrl: './new-chat-user-card.component.html',
  styleUrl: './new-chat-user-card.component.css'
})
export class NewChatUserCardComponent {
    @Input() user?: UserSearchDto;
}
