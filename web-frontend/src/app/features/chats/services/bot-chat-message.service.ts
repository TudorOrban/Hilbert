import { HttpClient, HttpEventType, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { SearchUrlBuilderService } from "../../../shared/search/services/SearchUrlBuilderService";
import {
    BotChatMessageSearchParams,
    PaginatedResults,
} from "../../../shared/search/models/Search";
import { map, Observable } from "rxjs";
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
        private urlBuilderService: SearchUrlBuilderService
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

    createMessageAndRespond(
        messageDto: CreateBotChatMessageDto
    ): Observable<string> {
        return new Observable<string>((observer) => {
            this.http
                .post<BotChatStartStreamResponse>(this.apiUrl + "/start-responding", messageDto)
                .subscribe({
                    next: (response) => {
                        console.log("Request ID: ", response.requestId);
                        const eventSource = new EventSource(
                            this.apiUrl + "/response-stream/" + response.requestId
                        ); 
                        
                        eventSource.onmessage = (event) => {
                            console.log("Event: ", event);
                            observer.next(event.data);
                        };

                        eventSource.onerror = (error) => {
                            observer.error(error);
                            eventSource.close();
                        };

                        eventSource.onopen = () => {
                            console.log("SSE connection opened");
                        };

                        return () => {
                            eventSource.close();
                            console.log("SSE connection closed");
                        };
                    },
                    error: (error) => {
                        observer.error(
                            "Error starting process: " + error.message
                        );
                    },
                });
        });
    }
}
