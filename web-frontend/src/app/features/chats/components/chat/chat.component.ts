import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../../core/user/services/auth.service';
import { ChatService } from '../../services/chat.service';
import { ChatFullDto } from '../../models/Chat';
import { MessageSearchDto } from '../../models/ChatMessage';

@Component({
  selector: 'app-chat',
  imports: [],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})
export class ChatComponent {
    chatId?: number;
    userId?: number;
    chat?: ChatFullDto;
    messages?: MessageSearchDto[];
    totalCount?: number;

    constructor(
        private readonly chatService: ChatService,
        private readonly authService: AuthService,
        private readonly route: ActivatedRoute,
    ) {}

    ngOnInit(): void {
        this.route.paramMap.subscribe((params) => {
            this.chatId = Number(params.get("chatId"));

            this.loadChat();
        });
        this.authService.getCurrentUser().subscribe((user) => {
            this.userId = user?.id;
        });
    }

    loadChat(): void {
        if (!this.chatId) {
            console.error("Chat ID not available");
            return;
        }

        this.chatService.getChat(this.chatId, true).subscribe(
            (data) => {
                console.log("Data: ", data);
                this.chat = data;
                this.messages = data.messages?.results;
                this.totalCount = data.messages?.totalCount;
            },
            (error) => {
                console.error("Failed to load chat: ", error);
            }
        )
    }
}
