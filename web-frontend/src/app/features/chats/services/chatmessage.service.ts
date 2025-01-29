import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { SearchUrlBuilderService } from "../../../shared/search/services/SearchUrlBuilderService";
import { ChatSearchParams, PaginatedResults } from "../../../shared/search/models/Search";
import { Observable } from "rxjs";
import { ChatFullDto, ChatSearchDto, CreateChatDto } from "../models/Chat";
import { MessageSearchDto } from "../models/ChatMessage";

@Injectable({
    providedIn: "root"
})
export class ChatMessageService {
    private apiUrl = "http://localhost:8080/api/v1/chat-messages";
    
    constructor(
        private http: HttpClient,
        private urlBuilderService: SearchUrlBuilderService
    ) {}

    
    searchChatMessages(searchParams: ChatSearchParams, includeUsers?: boolean): Observable<PaginatedResults<MessageSearchDto>> {
        const url = this.urlBuilderService.buildSearchUrl(this.apiUrl + "/search", searchParams);

        return this.http.get<PaginatedResults<MessageSearchDto>>(`${url}&includeUsers=${includeUsers ?? false}`);
    }
}