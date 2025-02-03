import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CreateUserDto, UpdateUserDto, UserDataDto, UserSearchDto } from "../models/User";
import { Observable } from "rxjs";
import { PaginatedResults, UserSearchParams } from "../../../shared/search/models/Search";
import { SearchUrlBuilderService } from "../../../shared/search/services/SearchUrlBuilderService";
import { Language } from "../../../shared/language/models/Language";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private apiUrl: string = "http://localhost:8080/api/v1/users";

    constructor(
        private readonly urlBuilderService: SearchUrlBuilderService,
        private readonly http: HttpClient
    ) {}

    getUserByUsername(username: string): Observable<UserDataDto> {
        return this.http.get<UserDataDto>(`${this.apiUrl}/${username}`);
    }

    searchUsers(searchParams: UserSearchParams): Observable<PaginatedResults<UserSearchDto>> {
        const url = this.urlBuilderService.buildUserSearchUrl(this.apiUrl + "/search", searchParams);

        return this.http.get<PaginatedResults<UserSearchDto>>(url);
    }

    createUser(userDto: CreateUserDto): Observable<UserDataDto> {
        return this.http.post<UserDataDto>(this.apiUrl, userDto);
    }

    updateUser(userDto: UpdateUserDto): Observable<UserDataDto> {
        return this.http.put<UserDataDto>(this.apiUrl, userDto);
    }

    deleteUser(id: number) {
        this.http.delete(`${this.apiUrl}/${id}`);
    }

}
