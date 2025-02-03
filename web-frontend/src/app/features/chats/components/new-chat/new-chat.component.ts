import { Component, OnInit } from '@angular/core';
import { UIItem } from '../../../../shared/common/types/common';
import { UserSearchDto, UserSmallDto } from '../../../../core/user/models/User';
import { LanguageLevels, PaginatedResults, UserSearchParams } from '../../../../shared/search/models/Search';
import { NavigationMenuComponent } from "../../../../shared/common/components/navigation-menu/navigation-menu.component";
import { CommonModule } from '@angular/common';
import { NewChatUsersListComponent } from "./new-chat-users-list/new-chat-users-list.component";
import { NewChatBotSelectionComponent } from "./new-chat-bot-selection/new-chat-bot-selection.component";
import { NewChatUsersSearchComponent } from "./new-chat-users-search/new-chat-users-search.component";
import { UserService } from '../../../../core/user/services/user.service';
import { Language } from '../../../../shared/language/models/Language';
import { LanguageLevelsSelectorComponent } from "../../../../shared/language/components/language-levels-selector/language-levels-selector.component";

@Component({
  selector: 'app-new-chat',
  imports: [CommonModule, NavigationMenuComponent, NewChatUsersListComponent, NewChatBotSelectionComponent, NewChatUsersSearchComponent, LanguageLevelsSelectorComponent],
  templateUrl: './new-chat.component.html',
  styleUrl: './new-chat.component.css'
})
export class NewChatComponent implements OnInit {
    userId?: number;
    navItems: UIItem[] = [
        { label: "User Chat", value: "UserChat" },
        { label: "Bot Chat", value: "BotChat" },
    ];
    selectedItemValue: string = "UserChat";

    searchParams: UserSearchParams = { languageLevels: {}, searchQuery: "", sortBy: "createdAt", isAscending: false, page: 1, itemsPerPage: 20 };

    users?: PaginatedResults<UserSearchDto>;
    areUsersLoading: boolean = false;

    constructor(
        private readonly userService: UserService,
    ) {}

    ngOnInit(): void {
        this.userService.searchUsers(this.searchParams).subscribe(
            (data) => {
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

    onSelectedLanguageLevelsChange(languageLevels: LanguageLevels) {
        console.log("lang: ", languageLevels);
        this.searchParams.languageLevels = languageLevels;
    }
}
