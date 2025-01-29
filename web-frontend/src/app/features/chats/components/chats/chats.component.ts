import { Component, OnInit } from '@angular/core';
import { ChatService } from '../../services/chat.service';
import { AuthService } from '../../../../core/user/services/auth.service';
import { PaginatedResults } from '../../../../shared/search/models/Search';
import { ChatSearchDto } from '../../models/Chat';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-chats',
  imports: [CommonModule],
  templateUrl: './chats.component.html',
  styleUrl: './chats.component.css'
})
export class ChatsComponent implements OnInit {
    private userId?: number;
    chats?: PaginatedResults<ChatSearchDto>;

    constructor (
        private readonly chatService: ChatService,
        private readonly authService: AuthService
    ) {}

    ngOnInit(): void {
        this.authService.getCurrentUser().subscribe(
            (user) => {
                this.userId = user?.id;
                this.loadUserChats();
            }
        );
    }

    loadUserChats(): void {
        if (!this.userId) {
            console.error("You are not logged in");
            return;
        }

        this.chatService.searchChats({}, true).subscribe(
            (data) => {
                console.log("Chats: ", data);
                this.chats = data;
            },
            (error) => {
                console.error("Failed to search for chats: ", error);
            }
        );
    }
}
