import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { SearchUrlBuilderService } from "../../../shared/search/services/SearchUrlBuilderService";
import { BotChatSearchParams, PaginatedResults } from "../../../shared/search/models/Search";
import { Observable } from "rxjs";
import { BotChatFullDto, BotChatSearchDto, CreateBotChatDto } from "../models/BotChat";
import { Language } from "../../../shared/language/models/Language";

@Injectable({
    providedIn: "root"
})
export class BotChatService {
    private apiUrl = "http://localhost:8080/api/v1/bot-chats";
    
    constructor(
        private http: HttpClient,
        private urlBuilderService: SearchUrlBuilderService
    ) {}

    
    searchChats(searchParams: BotChatSearchParams, userId: number, includeUsers?: boolean): Observable<PaginatedResults<BotChatSearchDto>> {
        const url = this.urlBuilderService.buildSearchUrl(this.apiUrl + "/search", searchParams);
            console.log("URLL", url);
        return this.http.get<PaginatedResults<BotChatSearchDto>>(`${url}&userId=${userId}&language=${searchParams.language ?? Language.NONE}&includeUsers=${includeUsers ?? false}`);
    }

    getChat(chatId: number, includeUsers?: boolean, includeMessages?: boolean): Observable<BotChatFullDto> {
        return this.http.get<BotChatFullDto>(`${this.apiUrl}/${chatId}?includeUsers=${includeUsers ?? false}&includeMessages=${includeMessages ?? false}`);
    }

    createChat(chatDto: CreateBotChatDto): Observable<BotChatFullDto> {
        return this.http.post<BotChatFullDto>(this.apiUrl, chatDto);
    }

    deleteChat(chatId: number) {
        return this.http.delete(`${this.apiUrl}/${chatId}`);
    }
}