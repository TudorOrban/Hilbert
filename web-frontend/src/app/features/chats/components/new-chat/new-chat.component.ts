import { Component } from '@angular/core';
import { UIItem } from '../../../../shared/common/types/UIItem';
import { UserSmallDto } from '../../../../core/user/models/User';
import { PaginatedResults } from '../../../../shared/search/models/Search';
import { NewChatHeaderComponent } from "./new-chat-header/new-chat-header.component";
import { NavigationMenuComponent } from "../../../../shared/common/components/navigation-menu/navigation-menu.component";
import { CommonModule } from '@angular/common';
import { NewChatUsersListComponent } from "./new-chat-users-list/new-chat-users-list.component";
import { NewChatBotSelectionComponent } from "./new-chat-bot-selection/new-chat-bot-selection.component";

@Component({
  selector: 'app-new-chat',
  imports: [CommonModule, NewChatHeaderComponent, NavigationMenuComponent, NewChatUsersListComponent, NewChatBotSelectionComponent],
  templateUrl: './new-chat.component.html',
  styleUrl: './new-chat.component.css'
})
export class NewChatComponent {
    userId?: number;
    navItems: UIItem[] = [
        { label: "User Chats", value: "UserChats" },
        { label: "Bot Chats", value: "BotChats" },
    ];
    selectedItemValue: string = "UserChats";

    users?: PaginatedResults<UserSmallDto>;
    areUsersLoading: boolean = false;



    onSelectedItemChange(newValue: string) {
        this.selectedItemValue = newValue;
    }
}
