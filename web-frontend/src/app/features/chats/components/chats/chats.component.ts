import { Component, OnInit } from '@angular/core';
import { faArrowUpWideShort, faArrowDownShortWide, faMessage, faCheck } from "@fortawesome/free-solid-svg-icons";
import { ChatService } from '../../services/chat.service';
import { AuthService } from '../../../../core/user/services/auth.service';
import { PaginatedResults } from '../../../../shared/search/models/Search';
import { ChatSearchDto } from '../../models/Chat';
import { CommonModule } from '@angular/common';
import { SearchInputComponent } from "../../../../shared/common/components/search-input/search-input.component";
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { UiUtilService } from '../../../../shared/common/services/ui-util.service';
import { Router } from '@angular/router';
import { UIItem } from '../../../../shared/common/types/UIItem';

@Component({
  selector: 'app-chats',
  imports: [CommonModule, FontAwesomeModule, SearchInputComponent],
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

    constructor (
        private readonly chatService: ChatService,
        private readonly authService: AuthService,
        private readonly uiUtilService: UiUtilService,
        private readonly router: Router
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
                this.chats = data;
            },
            (error) => {
                console.error("Failed to search for chats: ", error);
            }
        );
    }

    formatDate(dateString?: string): string {
        return this.uiUtilService.formatDate(dateString);
    }

    navigateToChat(chatId?: number) {
        if (!chatId) {
            console.error("Chat ID unavailable: ", chatId);
            return;
        }

        this.router.navigate([`/chat/${chatId}`]);
    }

    handleAddChat(): void {

    }

    selectTab(tabValue: string) {
        this.selectedTab = tabValue;
    }

    faArrowUpWideShort = faArrowUpWideShort;
    faArrowDownShortWide = faArrowDownShortWide;
    faMessage = faMessage;
    faCheck = faCheck;
}
