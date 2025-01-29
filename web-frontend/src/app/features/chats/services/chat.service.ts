import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { SearchUrlBuilderService } from "../../../shared/search/services/SearchUrlBuilderService";
import { ChatSearchParams, PaginatedResults } from "../../../shared/search/models/Search";
import { Observable } from "rxjs";
import { ChatFullDto, ChatSearchDto, CreateChatDto } from "../models/Chat";

@Injectable({
    providedIn: "root"
})
export class ChatService {
    private apiUrl = "http://localhost:8080/api/v1/chats";
    
    constructor(
        private http: HttpClient,
        private urlBuilderService: SearchUrlBuilderService
    ) {}

    
    searchChats(searchParams: ChatSearchParams, includeUsers?: boolean): Observable<PaginatedResults<ChatSearchDto>> {
        const url = this.urlBuilderService.buildSearchUrl(this.apiUrl + "/search", searchParams);

        return this.http.get<PaginatedResults<ChatSearchDto>>(`${url}&includeUsers=${includeUsers ?? false}`);
    }

    getChat(chatId: number, includeUsers?: boolean, includeMessages?: boolean): Observable<ChatFullDto> {
        return this.http.get<ChatFullDto>(`${this.apiUrl}/${chatId}?includeUsers=${includeUsers ?? false}&includeMessages=${includeMessages ?? false}`);
    }

    createChat(chatDto: CreateChatDto): Observable<ChatFullDto> {
        return this.http.post<ChatFullDto>(this.apiUrl, chatDto);
    }

    deleteChat(chatId: number) {
        return this.http.delete(`${this.apiUrl}/${chatId}`);
    }
}