import { HttpClient, HttpEventType } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { SearchUrlBuilderService } from "../../../shared/search/services/SearchUrlBuilderService";
import { BotChatMessageSearchParams, PaginatedResults } from "../../../shared/search/models/Search";
import { map, Observable } from "rxjs";
import { BotChatMessageSearchDto, CreateBotChatMessageDto } from "../models/BotChatMessage";

@Injectable({
    providedIn: "root"
})
export class BotChatMessageService {
    private apiUrl = "http://localhost:8080/api/v1/bot-chat-messages";
    
    constructor(
        private http: HttpClient,
        private urlBuilderService: SearchUrlBuilderService
    ) {}

    
    searchBotChatMessages(searchParams: BotChatMessageSearchParams, includeUsers?: boolean): Observable<PaginatedResults<BotChatMessageSearchDto>> {
        const url = this.urlBuilderService.buildSearchUrl(this.apiUrl + "/search", searchParams);

        return this.http.get<PaginatedResults<BotChatMessageSearchDto>>(`${url}&includeUsers=${includeUsers ?? false}`);
    }

    createMessageAndRespond(messageDto: CreateBotChatMessageDto): Observable<String | null | undefined> {
        return this.http.post<String>(`${this.apiUrl}/respond`, messageDto, {
            observe: 'events',
            reportProgress: true
        }).pipe(
            map(event => {
                if (event.type === HttpEventType.DownloadProgress) {
                    return "";
                } else if (event.type === HttpEventType.Response) {
                    return event.body;
                }
                return "";
            })
        );
    }
}