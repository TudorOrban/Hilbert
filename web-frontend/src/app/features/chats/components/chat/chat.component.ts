import { Component, OnDestroy, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { AuthService } from "../../../../core/user/services/auth.service";
import { ChatService } from "../../services/chat.service";
import { ChatFullDto } from "../../models/Chat";
import { CreateMessageDto, MessageSearchDto } from "../../models/ChatMessage";
import { CommonModule } from "@angular/common";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import {
    faCheck,
    faEllipsisVertical,
    faPaperPlane,
    faSearch,
} from "@fortawesome/free-solid-svg-icons";
import { UiUtilService } from "../../../../shared/common/services/ui-util.service";
import { LiveMessageService } from "../../services/live-message.service";
import { Subscription } from "rxjs";
import { RxStompService } from "../../services/rx-stomp.service";
import { Message } from "stompjs";
import { FormsModule } from "@angular/forms";

@Component({
    selector: "app-chat",
    imports: [CommonModule, FontAwesomeModule, FormsModule],
    templateUrl: "./chat.component.html",
    styleUrl: "./chat.component.css",
})
export class ChatComponent implements OnInit, OnDestroy {
    chatId?: number;
    userId?: number;
    chat?: ChatFullDto;
    messages?: MessageSearchDto[];
    totalCount?: number;
    private liveMessageSubscription?: Subscription;
    messageToSendContent: string = "";

    receivedMessages: string[] = [];

    constructor(
        private readonly chatService: ChatService,
        private readonly liveMessageService: LiveMessageService,
        private readonly authService: AuthService,
        private readonly uiUtilService: UiUtilService,
        private readonly route: ActivatedRoute,
        private readonly rxStompService: RxStompService
    ) {}

    ngOnInit(): void {
        this.route.paramMap.subscribe((params) => {
            this.chatId = Number(params.get("chatId"));

            this.loadChat();
        });
        this.authService.getCurrentUser().subscribe((user) => {
            this.userId = user?.id;
        });

        if (!this.chatId) {
            return;
        }
        this.rxStompService
            .watch("/topic/chat/" + this.chatId)
            .subscribe((message: Message) => {
                this.handleNewMessage(JSON.parse(message.body));
            });
    }

    ngOnDestroy() {
        this.liveMessageSubscription?.unsubscribe();
    }

    loadChat(): void {
        if (!this.chatId) {
            console.error("Chat ID not available");
            return;
        }

        this.chatService.getChat(this.chatId, true, true).subscribe(
            (data) => {
                console.log("Data: ", data);
                this.chat = data;
                this.messages = data.messages?.results.sort(
                    (a, b) =>
                        new Date(a.createdAt).getTime() -
                        new Date(b.createdAt).getTime()
                );
                this.totalCount = data.messages?.totalCount;
            },
            (error) => {
                console.error("Failed to load chat: ", error);
            }
        );
    }

    handleNewMessage(message: MessageSearchDto): void {
        console.log("Message received: ", message);
        this.messages?.push(message);
    }

    formatDate(dateString?: string): string {
        return this.uiUtilService.formatDate(dateString);
    }

    sendMessage(): void {
        if (!this.userId) {
            console.error("You are not logged in");
            return;
        }
        const messageDto: CreateMessageDto = {
            userId: this.userId ?? 0,
            chatId: this.chatId ?? 0,
            content: this.messageToSendContent
        }

        this.rxStompService.publish({
            destination: "/app/chat.sendMessage",
            body: JSON.stringify(messageDto),
        });
    }

    faCheck = faCheck;
    faSearch = faSearch;
    faEllipsisVertical = faEllipsisVertical;
    faPaperPlane = faPaperPlane;
}
