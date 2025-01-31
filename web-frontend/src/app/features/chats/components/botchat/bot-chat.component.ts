import { Component, ElementRef, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { AuthService } from "../../../../core/user/services/auth.service";
import { BotChatService } from "../../services/bot-chat.service";
import { BotChatFullDto } from "../../models/BotChat";
import { CommonModule } from "@angular/common";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import {
    faCheck,
    faEllipsisVertical,
    faPaperPlane,
    faSearch,
} from "@fortawesome/free-solid-svg-icons";
import { UiUtilService } from "../../../../shared/common/services/ui-util.service";
import { RxStompService } from "../../services/rx-stomp.service";
import { FormsModule } from "@angular/forms";
import { BotChatMessageService } from "../../services/bot-chat-message.service";
import { BotChatMessageSearchParams } from "../../../../shared/search/models/Search";
import { BotChatMessageSearchDto, CreateBotChatMessageDto } from "../../models/BotChatMessage";
import { Language } from "../../../../shared/language/models/Language";

@Component({
    selector: "app-bot-chat",
    imports: [CommonModule, FontAwesomeModule, FormsModule],
    templateUrl: "./bot-chat.component.html",
    styleUrl: "./bot-chat.component.css",
})
export class BotChatComponent implements OnInit {
    @ViewChild("messagesContainer") messagesContainer!: ElementRef;

    chatId?: number;
    userId?: number;
    chat?: BotChatFullDto;
    messages: BotChatMessageSearchDto[] = [];
    totalCount?: number;

    currentPage: number = 1;
    messageToSendContent: string = "";
    isBotResponding: boolean = false;
    currentResponse: string = "";

    constructor(
        private readonly chatService: BotChatService,
        private readonly chatMessageService: BotChatMessageService,
        private readonly authService: AuthService,
        private readonly uiUtilService: UiUtilService,
        private readonly route: ActivatedRoute,
        private readonly rxStompService: RxStompService,
    ) {}

    ngOnInit(): void {
        this.route.paramMap.subscribe((params) => {
            this.chatId = Number(params.get("botChatId"));

            this.loadBotChat();
        });
        this.authService.getCurrentUser().subscribe((user) => {
            this.userId = user?.id;
        });
    }

    // Initial data loading
    loadBotChat(): void {
        if (!this.chatId) {
            console.error("Chat ID not available");
            return;
        }

        this.chatService.getChat(this.chatId, true, true).subscribe(
            (data) => {
                this.handleChatResponse(data);
            },
            (error) => {
                console.error("Failed to load chat: ", error);
            }
        );
    }

    private handleChatResponse(chatResponse: BotChatFullDto): void {
        this.chat = chatResponse;
        this.messages = chatResponse.messages?.results.sort(
            (a, b) =>
                new Date(a.createdAt).getTime() -
                new Date(b.createdAt).getTime()
        ) ?? [];
        this.totalCount = chatResponse.messages?.totalCount;
        setTimeout(() => {
            this.scrollToBottom();
        }, 100);
    }

    private scrollToBottom(): void {
        if (this.messagesContainer && this.messagesContainer.nativeElement) {
            const element = this.messagesContainer.nativeElement;
            element.scrollTop = element.scrollHeight;
        }
    }

    sendMessage(): void {
        if (!this.userId) {
            console.error("You are not logged in");
            return;
        }
        const messageDto: CreateBotChatMessageDto = {
            userId: this.userId ?? 0,
            isUser: true,
            botChatId: this.chatId ?? 0,
            content: this.messageToSendContent,
            language: this.chat?.language ?? Language.NONE,
        }

        this.messageToSendContent = "";

        this.isBotResponding = true;
        this.chatMessageService.createMessageAndRespond(messageDto).subscribe(
            (data) => {
                this.currentResponse += data;
            },
        );
    }

    onKeydown(event: KeyboardEvent): void {
        if (event.key === 'Enter') {
            this.sendMessage();
        }
    }

    // Infinite query
    loadMoreMessages(): void {
        if ((this.totalCount ?? 0) < this.messages.length) {
            return;
        }

        this.currentPage++;
        const searchParams: BotChatMessageSearchParams = { page: this.currentPage, itemsPerPage: 10, isAscending: false };
        
        this.chatMessageService.searchBotChatMessages(searchParams).subscribe(
            (data) => {
                this.messages = this.messages.concat(data.results).sort(
                    (a, b) =>
                        new Date(a.createdAt).getTime() -
                        new Date(b.createdAt).getTime()
                );
            },
            (error) => {
                console.error("Failed to load next messages: ", error);
            }
        )
    }

    // Utils
    shouldShowData(index: number): boolean {
        let currentDate = new Date(this.messages?.[index]?.createdAt).getDate();
        let previousDate = new Date(this.messages?.[index + 1]?.createdAt).getDate();
        return currentDate != previousDate;
    }

    getDate(dateString?: string): string | undefined {
        if (!dateString) {
            return undefined;
        }
        return new Date(dateString).toDateString();
    }

    formatDate(dateString?: string): string {
        return this.uiUtilService.formatDate(dateString);
    }

    faCheck = faCheck;
    faSearch = faSearch;
    faEllipsisVertical = faEllipsisVertical;
    faPaperPlane = faPaperPlane;
}
