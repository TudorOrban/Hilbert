import { AfterViewChecked, Component, ElementRef, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { AuthService } from "../../../../core/user/services/auth.service";
import { ChatService } from "../../services/chat.service";
import { ChatFullDto } from "../../models/Chat";
import { CreateChatMessageDto, ChatMessageSearchDto } from "../../models/ChatMessage";
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
import { Message } from "stompjs";
import { FormsModule } from "@angular/forms";
import { ChatMessageService } from "../../services/chat-message.service";
import { ChatMessageSearchParams } from "../../../../shared/search/models/Search";

@Component({
    selector: "app-chat",
    imports: [CommonModule, FontAwesomeModule, FormsModule],
    templateUrl: "./chat.component.html",
    styleUrl: "./chat.component.css",
})
export class ChatComponent implements OnInit {
    @ViewChild("messagesContainer") messagesContainer!: ElementRef;

    chatId?: number;
    userId?: number;
    chat?: ChatFullDto;
    messages: ChatMessageSearchDto[] = [];
    totalCount?: number;
    currentPage: number = 1;
    messageToSendContent: string = "";

    constructor(
        private readonly chatService: ChatService,
        private readonly chatMessageService: ChatMessageService,
        private readonly authService: AuthService,
        private readonly uiUtilService: UiUtilService,
        private readonly route: ActivatedRoute,
        private readonly rxStompService: RxStompService,
    ) {}

    ngOnInit(): void {
        this.route.paramMap.subscribe((params) => {
            this.chatId = Number(params.get("chatId"));

            this.loadChat();
        });
        this.authService.getCurrentUser().subscribe((user) => {
            this.userId = user?.id;
        });

        this.connectToWebSocket();
    }

    // Initial data loading
    loadChat(): void {
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

    private handleChatResponse(chatResponse: ChatFullDto): void {
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

    // Live messages
    connectToWebSocket(): void {
      if (!this.chatId) {
          return;
      }

      this.rxStompService
          .watch("/topic/chat/" + this.chatId)
          .subscribe((message: Message) => {
              this.handleNewMessage(JSON.parse(message.body));
          });
    }

    handleNewMessage(message: ChatMessageSearchDto): void {
        this.messages?.push(message);
    }

    sendMessage(): void {
        if (!this.userId) {
            console.error("You are not logged in");
            return;
        }
        const messageDto: CreateChatMessageDto = {
            userId: this.userId ?? 0,
            chatId: this.chatId ?? 0,
            content: this.messageToSendContent
        }

        this.rxStompService.publish({
            destination: "/app/chat.sendMessage",
            body: JSON.stringify(messageDto),
        });
        this.messageToSendContent = "";
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
        const searchParams: ChatMessageSearchParams = { page: this.currentPage, itemsPerPage: 10, isAscending: false };
        
        this.chatMessageService.searchChatMessages(searchParams).subscribe(
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
