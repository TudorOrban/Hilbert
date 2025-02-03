import { Component, Input } from '@angular/core';
import { UserSmallDto } from '../../../../../core/user/models/User';

@Component({
  selector: 'app-new-chat-user-card',
  imports: [],
  templateUrl: './new-chat-user-card.component.html',
  styleUrl: './new-chat-user-card.component.css'
})
export class NewChatUserCardComponent {
    @Input() user?: UserSmallDto;
}
