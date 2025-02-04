import { Component, OnInit } from '@angular/core';
import { LanguageSelectorComponent } from "../../../../../shared/language/components/language-selector/language-selector.component";
import { EnumSelectorComponent } from "../../../../../shared/common/components/enum-selector/enum-selector.component";
import { DifficultyLevel } from '../../../../articles/models/Article';
import { Language } from '../../../../../shared/language/models/Language';
import { Router } from '@angular/router';
import { faPaperPlane } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormsModule } from '@angular/forms';
import { BotChatService } from '../../../services/bot-chat.service';
import { AuthService } from '../../../../../core/user/services/auth.service';
import { CreateBotChatDto } from '../../../models/BotChat';
import { NavigationUtilService } from '../../../../../shared/common/services/navigation-util.service';

@Component({
  selector: 'app-new-chat-bot-selection',
  imports: [FontAwesomeModule, FormsModule, LanguageSelectorComponent, EnumSelectorComponent],
  templateUrl: './new-chat-bot-selection.component.html',
  styleUrl: './new-chat-bot-selection.component.css'
})
export class NewChatBotSelectionComponent implements OnInit {
    selectedLanguage = Language.ENGLISH;
    selectedLevel = DifficultyLevel.B1;
    messageContent: string = "";

    userId?: number;

    constructor(
        private readonly botChatService: BotChatService,
        private readonly authService: AuthService,
        private readonly navigationService: NavigationUtilService,
        private readonly router: Router
    ) {}

    ngOnInit(): void {
        this.authService.getCurrentUser().subscribe((data) => this.userId = data?.id);
    }

    onSelectedLanguageChange(language: Language) {
        this.selectedLanguage = language;
    }

    onSelectedLevelChange(level: DifficultyLevel) {
        this.selectedLevel = level;
    }

    startChat(): void {
        if (!this.userId) {
            console.log("You are not logged in");
            return;
        }

        const createChatDto: CreateBotChatDto = {
            userId: this.userId,
            language: this.selectedLanguage,
            messageContent: this.messageContent
        };

        this.botChatService.createChat(createChatDto).subscribe(
            (data) => {
                console.log("Data:", data);
                this.navigationService.setData("newMessage", this.messageContent);
                this.router.navigate([`/bot-chat/${data?.id}`]);
            },
            (error) => {
                console.error("Error creating bot chat:", error);
            }
        );
    }


    DifficultyLevel = DifficultyLevel;
    faPaperPlane = faPaperPlane;
}
