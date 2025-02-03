import { Component, OnInit } from '@angular/core';
import { UIItem } from '../../../../shared/common/types/UIItem';
import { UserSearchDto, UserSmallDto } from '../../../../core/user/models/User';
import { PaginatedResults } from '../../../../shared/search/models/Search';
import { NavigationMenuComponent } from "../../../../shared/common/components/navigation-menu/navigation-menu.component";
import { CommonModule } from '@angular/common';
import { NewChatUsersListComponent } from "./new-chat-users-list/new-chat-users-list.component";
import { NewChatBotSelectionComponent } from "./new-chat-bot-selection/new-chat-bot-selection.component";
import { NewChatUsersSearchComponent } from "./new-chat-users-search/new-chat-users-search.component";
import { UserService } from '../../../../core/user/services/user.service';

@Component({
  selector: 'app-new-chat',
  imports: [CommonModule, NavigationMenuComponent, NewChatUsersListComponent, NewChatBotSelectionComponent, NewChatUsersSearchComponent],
  templateUrl: './new-chat.component.html',
  styleUrl: './new-chat.component.css'
})
export class NewChatComponent implements OnInit {
    userId?: number;
    navItems: UIItem[] = [
        { label: "User Chats", value: "UserChats" },
        { label: "Bot Chats", value: "BotChats" },
    ];
    selectedItemValue: string = "UserChats";

    users?: PaginatedResults<UserSearchDto>;
    areUsersLoading: boolean = false;

    constructor(
        private readonly userService: UserService,
    ) {}

    ngOnInit(): void {
        this.userService.searchUsers({}).subscribe(
            (data) => {
                console.log("Data: ", data);
                this.users = data;
            },
            (error) => {
                console.error("Error searching users: ", error);
            }
        )
    }


    onSelectedItemChange(newValue: string) {
        this.selectedItemValue = newValue;
    }
}
