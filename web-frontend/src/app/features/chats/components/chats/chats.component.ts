import { Component, OnInit } from '@angular/core';
import { ChatService } from '../../services/chat.service';
import { AuthService } from '../../../../core/user/services/auth.service';
import { PaginatedResults } from '../../../../shared/search/models/Search';
import { ChatSearchDto } from '../../models/Chat';
import { CommonModule } from '@angular/common';
import { UIItem } from '../../../../shared/common/types/UIItem';
import { BotChatService } from '../../services/bot-chat.service';
import { BotChatSearchDto } from '../../models/BotChat';
import { ChatsHeaderComponent } from "./chats-header/chats-header.component";
import { ChatsListComponent } from "./chats-list/chats-list.component";
import { BotChatsListComponent } from "./bot-chats-list/bot-chats-list.component";

@Component({
  selector: 'app-chats',
  imports: [CommonModule, ChatsHeaderComponent, ChatsListComponent, BotChatsListComponent],
  templateUrl: './chats.component.html',
  styleUrl: './chats.component.css'
})
export class ChatsComponent implements OnInit {
    userId?: number;
    selectedTab: string = "UserChats";
    tabs: UIItem[] = [
        { label: "User Chats", value: "UserChats" },
        { label: "Bot Chats", value: "BotChats" },
    ];

    chats?: PaginatedResults<ChatSearchDto>;
    botChats?: PaginatedResults<BotChatSearchDto>;
    areChatsLoading: boolean = false;
    areBotChatsLoading: boolean = false;

    constructor (
        private readonly chatService: ChatService,
        private readonly botChatService: BotChatService,
        private readonly authService: AuthService,
    ) {}

    ngOnInit(): void {
        this.authService.getCurrentUser().subscribe(
            (user) => {
                this.userId = user?.id;
                this.loadUserChats();
                this.loadBotChats();
            }
        );
    }

    // Data loading
    loadUserChats(): void {
        if (!this.userId) {
            console.error("You are not logged in");
            return;
        }

        this.areChatsLoading = true;
        this.chatService.searchChats({}, true).subscribe(
            (data) => {
                this.chats = data;
                this.areChatsLoading = false;
            },
            (error) => {
                console.error("Failed to search for chats: ", error);
                this.areChatsLoading = false;
            }
        );
    }

    loadBotChats(): void {
        if (!this.userId) {
            console.error("You are not logged in");
            return;
        }

        this.areBotChatsLoading = true;
        this.botChatService.searchChats({}, this.userId, true).subscribe(
            (data) => {
                this.botChats = data;
                this.areBotChatsLoading = false;
            },
            (error) => {
                console.error("Failed to search for chats: ", error);
                this.areBotChatsLoading = false;
            }
        );
    }

    // Handlers
    selectTab(tabValue: string) {
        this.selectedTab = tabValue;
    }

}
