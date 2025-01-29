import { Component } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { AuthService } from "../../../../core/user/services/auth.service";
import { ChatService } from "../../services/chat.service";
import { ChatFullDto } from "../../models/Chat";
import { MessageSearchDto } from "../../models/ChatMessage";
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

@Component({
    selector: "app-chat",
    imports: [CommonModule, FontAwesomeModule],
    templateUrl: "./chat.component.html",
    styleUrl: "./chat.component.css",
})
export class ChatComponent {
    chatId?: number;
    userId?: number;
    chat?: ChatFullDto;
    messages?: MessageSearchDto[];
    totalCount?: number;
    private liveMessageSubscription?: Subscription;

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

            // this.loadChat();
        });
        // this.authService.getCurrentUser().subscribe((user) => {
        //     this.userId = user?.id;
        // });

        if (!this.chatId) {
            return;
        }
        // this.liveMessageSubscription = this.liveMessageService
        //     .connect(this.chatId)
        //     .subscribe((message) => {
        //         this.handleNewMessage(message);
        //     });
        this.rxStompService
            .watch("/topic/chat/" + this.chatId)
            .subscribe((message: Message) => {
                this.receivedMessages.push(message.body);
                console.log("Received message: ", message);
            });
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
    }

    formatDate(dateString?: string): string {
        return this.uiUtilService.formatDate(dateString);
    }

    sendMessage(): void {
        console.log("Message sending: ");

        const message = "Message";
        this.rxStompService.publish({
            destination: "/app/chat.sendMessage",
            body: JSON.stringify(message),
        });
    }

    faCheck = faCheck;
    faSearch = faSearch;
    faEllipsisVertical = faEllipsisVertical;
    faPaperPlane = faPaperPlane;
}
