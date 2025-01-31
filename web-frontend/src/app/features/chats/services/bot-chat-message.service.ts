import { HttpClient, HttpEventType, HttpHeaders } from "@angular/common/http";
import { Injectable, NgZone } from "@angular/core";
import { SearchUrlBuilderService } from "../../../shared/search/services/SearchUrlBuilderService";
import {
    BotChatMessageSearchParams,
    PaginatedResults,
} from "../../../shared/search/models/Search";
import { map, Observable, Observer } from "rxjs";
import {
    BotChatMessageSearchDto,
    BotChatStartStreamResponse,
    CreateBotChatMessageDto,
} from "../models/BotChatMessage";

@Injectable({
    providedIn: "root",
})
export class BotChatMessageService {
    private apiUrl = "http://localhost:8080/api/v1/bot-chat-messages";

    constructor(
        private http: HttpClient,
        private urlBuilderService: SearchUrlBuilderService,
        private ngZone: NgZone
    ) {}

    searchBotChatMessages(
        searchParams: BotChatMessageSearchParams,
        includeUsers?: boolean
    ): Observable<PaginatedResults<BotChatMessageSearchDto>> {
        const url = this.urlBuilderService.buildSearchUrl(
            this.apiUrl + "/search",
            searchParams
        );

        return this.http.get<PaginatedResults<BotChatMessageSearchDto>>(
            `${url}&includeUsers=${includeUsers ?? false}`
        );
    }

    createMessageAndRespond(messageDto: CreateBotChatMessageDto): Observable<string> {
        return new Observable<string>((observer) => {
            this.startResponseStream(messageDto).subscribe({
                next: (response) => this.handleResponseStream(response, observer),
                error: (error) => observer.error("Error starting process: " + error.message),
            });
        });
    }
    
    private startResponseStream(messageDto: CreateBotChatMessageDto): Observable<BotChatStartStreamResponse> {
        return this.http.post<BotChatStartStreamResponse>(this.apiUrl + "/start-responding", messageDto);
    }
    
    private handleResponseStream(response: BotChatStartStreamResponse, observer: Observer<string>): () => void {
        const eventSource = this.createEventSource(response.requestId);
    
        eventSource.onmessage = (event) => this.handleMessage(event, observer);
        eventSource.onerror = (error) => this.handleError(error, observer, eventSource);
        eventSource.onopen = () => console.log("SSE connection opened");
    
        return () => {
            eventSource.close();
            console.log("SSE connection closed");
        };
    }
    
    private createEventSource(requestId: string): EventSource {
        return new EventSource(this.apiUrl + "/response-stream/" + requestId);
    }
    
    private handleMessage(event: MessageEvent, observer: Observer<string>): void {
        const encodedData = event.data.substring("data: ".length).trim();
        const decodedBase64 = atob(encodedData);
    
        try {
            const decodedUTF8 = decodeURIComponent(escape(decodedBase64));
            this.ngZone.run(() => {
                observer.next(decodedUTF8);
            });
        } catch (e) {
            console.error("UTF-8 decode failed, fallback to direct Base64 string:", e);
            this.ngZone.run(() => {
                observer.next(decodedBase64);
            });
        }
    }
    
    private handleError(error: Event, observer: Observer<string>, eventSource: EventSource): void {
        console.error("EventSource error:", error);

        if (error.target instanceof EventSource && error.target.readyState === EventSource.CLOSED) {
            console.log("SSE connection closed normally.");
            this.ngZone.run(() => {
                observer.complete();
            });
        } else {
            console.error("EventSource error:", error);
            this.ngZone.run(() => {
                observer.error(error);
            });
            eventSource.close();
        }
    }
    
}
