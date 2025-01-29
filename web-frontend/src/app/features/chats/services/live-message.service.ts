import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { CreateMessageDto, MessageSearchDto } from "../models/ChatMessage";

@Injectable({
    providedIn: "root"
})
export class LiveMessageService {
    private socket$: WebSocketSubject<MessageSearchDto | CreateMessageDto> | null = null;

    connect(chatId: number): Observable<MessageSearchDto> {
        const url = `http://localhost:8080/ws`;
        this.socket$ = webSocket(url);
        return this.socket$.asObservable() as Observable<MessageSearchDto>;
    }

    sendMessage(message: CreateMessageDto): void {
        this.socket$?.next(message);
    }

    close(): void {
        this.socket$?.complete();
    }
}